package filter;

public class P extends ClosedLoopBlock {


    /**
     * Internal amplification variable
     */
    private double rK;

    public P(double amplification){
        rK = amplification;
        reset();
    }
    @Override
    public void reset(){
        rY = 0.0;
    }

    @Override
    public void updateOutput() {
        rY = rK * rX;
    }

}
