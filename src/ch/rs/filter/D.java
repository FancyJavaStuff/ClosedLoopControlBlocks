package ch.rs.filter;

import ch.rs.filter.master.ClosedLoopBlock;
import ch.rs.filter.util.MathFunctions;


public class D extends ClosedLoopBlock {


    /**
     * delay variable
     */
    private double delay;

    /**
     * amplification variable
     */
    private double amplification;

    /**
     * variables used during the calculation
     */
    private double temporaryInput, temporaryY, temporaryX;

    public D(double amplification, double delay) {
        this.amplification = amplification;
        this.delay = delay;
        reset();
    }

    @Override
    public void reset() {
        output = 0.0;
        temporaryInput = 0.0;
    }

    @Override
    protected void calculateOutput() {
        output = amplification / MathFunctions.biggerValue(delay, 0.001) * (input * cycleTime);
        temporaryX = amplification * delay * (input - temporaryInput);
        temporaryInput = input;
        temporaryY = temporaryX / MathFunctions.biggerValue(delay, 0.001);
        output = output - MathFunctions.clamp(-1000.0, output / MathFunctions.biggerValue(delay, 0.001),
                1000.0) * cycleTime + temporaryY;

    }

    public double getDelay() {
        return delay;
    }

    public void setDelay(double delay) {
        this.delay = delay;
    }

    public double getAmplification() {
        return amplification;
    }

    public void setAmplification(double amplification) {
        this.amplification = amplification;
    }
}
