package ch.rs.filter;

import ch.rs.filter.master.ClosedLoopBlock;
import ch.rs.filter.util.MathFunctions;

public class PT2 extends ClosedLoopBlock {

    /**
     * temporary variables used during calculations
     */
    private double omega, zeta, temporaryY, temporaryX, middleX;

    /**
     * delay
     */
    private double delayOne, delayTwo;

    /**
     * amplification
     */
    private double amplification;


    public PT2(double amplification, double delayOne, double delayTwo) {
        this.amplification = amplification;
        this.delayOne = delayOne;
        this.delayTwo = delayTwo;
        reset();
    }

    @Override
    public void reset() {
        middleX = 0.0;
        output = 0.0;
    }

    @Override
    protected void calculateOutput() {
        omega = 1 / Math.sqrt(MathFunctions.biggerValue(delayOne * delayTwo, 0.001));
        zeta = omega * (delayOne + delayTwo) / 2.0;

        temporaryY = cycleTime * omega * middleX;
        temporaryX = cycleTime * omega * (amplification * input - output - 2 * zeta * middleX);

        middleX = middleX + temporaryX;
        output = output + temporaryY;
    }

    public double getDelayOne() {
        return delayOne;
    }

    public void setDelayOne(double delayOne) {
        this.delayOne = delayOne;
    }

    public double getDelayTwo() {
        return delayTwo;
    }

    public void setDelayTwo(double delayTwo) {
        this.delayTwo = delayTwo;
    }

    public double getAmplification() {
        return amplification;
    }

    public void setAmplification(double amplification) {
        this.amplification = amplification;
    }
}
