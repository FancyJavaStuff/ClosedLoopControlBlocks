package ch.rs.filter;

import ch.rs.filter.master.ClosedLoopBlock;
import ch.rs.filter.util.MathFunctions;

public class PT1 extends ClosedLoopBlock {


    /**
     * amplification variable
     */
    private double amplification;

    /**
     * delay variable
     */
    private double delay;

    public PT1(double amplification, double delay) {
        this.delay = delay;
        this.amplification = amplification;
        reset();
    }

    @Override
    public void reset() {
        output = 0.0;
    }

    @Override
    protected void calculateOutput() {
        if (Double.compare(delay, 0.0) == 0) {
            output = input;
            return;
        }
        output += (-output + amplification * input) / MathFunctions.biggerValue(delay, avoidZero) * cycleTime;
    }

    public double getAmplification() {
        return amplification;
    }

    public void setAmplification(double amplification) {
        this.amplification = amplification;
    }

    public double getDelay() {
        return delay;
    }

    public void setDelay(double delay) {
        this.delay = delay;
    }
}
