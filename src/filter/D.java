package filter;

public class D extends ClosedLoopBlock {


    private double rT;

    /**
     * Internal amplification variable
     */
    private double rK;

    private double rXeAlt, rDy, rDx;

    public D(double amplification, double delay){
        rK = amplification;
        rT = delay;
        reset();
    }
    @Override
    public void reset(){
        rY = 0.0;
        rXeAlt = 0.0;
    }

    @Override
    public void updateOutput() {
        rY = rK/max(rT,0.001)*(rX*cycTime);
        rDx = rK*rT*(rX - rXeAlt);
        rXeAlt = rX;
        rDy = rDx/max(rT, 0.001);
        rY = rY - limit(-1000.0, rY/max(rT, 0.001), 1000.0)*cycTime + rDy;

    }

}
