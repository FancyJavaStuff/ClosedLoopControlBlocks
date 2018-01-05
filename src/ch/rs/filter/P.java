package ch.rs.filter;

import ch.rs.filter.master.ClosedLoopBlock;

public class P extends ClosedLoopBlock {


    /**
     * Internal amplification variable
     */
    private double amplification;

    public P(double amplification) {
        this.amplification = amplification;
        reset();
    }

    @Override
    public void reset() {
        output = 0.0;
    }

    @Override
    protected void calculateOutput() {
        output = amplification * input;
    }

}
