package filter;

public class I extends ClosedLoopBlock {


    private double rT;

    /**
     * Internal amplification variable
     */
    private double rK;

    public I(double amplification, double delay){
        rK = amplification;
        rT = delay;
        reset();
    }
    @Override
    public void reset(){
        rY = 0.0;
    }

    @Override
    public void updateOutput() {
        rY += rK/max(rT,0.001)*(rX*cycTime);
    }

}
