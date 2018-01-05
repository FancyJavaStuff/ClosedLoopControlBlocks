package ch.rs.filter;

import ch.rs.filter.master.ClosedLoopBlock;
import ch.rs.filter.util.MathFunctions;

public class I extends ClosedLoopBlock {


    /**
     * delay variable
     */
    private double delay;

    /**
     * amplification variable
     */
    private double amplification;

    public I(double amplification, double delay) {
        this.amplification = amplification;
        this.delay = delay;
        reset();
    }

    @Override
    public void reset() {
        output = 0.0;
    }

    @Override
    protected void calculateOutput() {
        output += amplification / MathFunctions.biggerValue(delay, 0.001) * (input * cycleTime);
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
