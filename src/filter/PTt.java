package filter;

public class PTt extends ClosedLoopBlock{

    /**
     *
     * == CodeSys Code ==
     *
     * IF bReset THEN
     *  rTt := RET_COMMON.rDt;
     *  reset();
     * END_IF
     *
     * iN := REAL_TO_INT(rTt/MAX(RET_COMMON.rDt, 0.001));
     * iN := LIMIT(1, iN, 5000);
     * FOR ii := iN TO 1 BY -1 DO
     *  arX[ii] := arX[ii - 1];
     * END_FOR
     * arX[0] := rX;
     * rY := rK*arX[iN];
     *
     * == Variable/Function definition ==
     * reset() = sets rY to 0. && Rt =  cycTim
     * rY = output Y
     * rX = input U
     * rTt = t
     * rK = K
     * LIMIT() = limit(min, value, max) limits the value to a minimal and maximal value
     *
     * call reset() at the start!
     *
     *
     */

    private double rT;

    private double rK;

    private int i, x;

    private double[] arX;

    public PTt(double k, double t){
        arX = new double[5000];
        rK = k;
        rT = t;
        rY = 0.0;
    }
    public void setDelay(double delay){
        rT = delay;
    }

    @Override
    public void updateOutput(){
        x = (int) limit(1, rT/max(cycTime, 0.001), 5000);
        for(i = x; i > 1; i--){
            System.out.println(i);
            arX[i] = arX[i - 1];
        }
        arX[0] = rX;
        rY = rK*arX[x];
    }


    @Override
    void reset() {
        rY = 0.0;
        rT = cycTime;
    }

}
