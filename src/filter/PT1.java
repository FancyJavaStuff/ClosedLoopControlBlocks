package filter;

public class PT1 extends ClosedLoopBlock {

    /**
     * == CodeSys Code ==
     *
     * IF bReset THEN
     *  reset();
     * END_IF
     *
     * IF (rT > 0.0) THEN
     *  rY := rY + (-rY + rK*rX)/MAX(rT,0.001)*RET_COMMON.rDt;
     * ELSE
     *  rY := rX;
     * END_IF
     *
     *  == Variable/Function explanation ==
     *
     * rY internal output variable = Y
     * rX internal input variable = U
     * rK = K
     * rT = T
     * MAX(x, y) = returns the bigger value of the given values
     * RET_COMMON.rDt = cycle time main task
     * reset() = sets rY = 0.0
     *
     *
     *
     *
     */


    /**
     * Internal amplification variable
     */
    private double rK;

    /**
     * Internal delay variable
     */
    private double rT;

    public PT1(double amplification, double delay){
        rT = delay;
        rK = amplification;
        reset();
    }
    @Override
    public void reset(){
        rY = 0.0;
    }

    @Override
    public void updateOutput() {
        if(Double.compare(rT, 0.0) == 0){
            rY = rX;
            return;
        }
        rY = rY + (-rY + rK*rX)/super.max(rT,0.001)*cycTime;
    }

}
