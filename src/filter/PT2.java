package filter;

public class PT2 extends ClosedLoopBlock {

    /**
     *
     * == CodeSys Code ==
     *
     * rOmega := 1/SQRT(MAX(rT1*rT2, 0.001));
     * rZeta := rOmega*(rT1+rT2)/2.0;
     *
     * IF bReset THEN
     *  reset();
     * END_IF
     *
     * rDy := RET_COMMON.rDt*rOmega*rXX;
     * rDx := RET_COMMON.rDt*rOmega*(rK*rX - rY - 2*rZeta*rXX);
     * rXX := rXX + rDx;
     * rY := rY + rDy;
     *
     *
     * == Variable/Function definition ==
     * reset() = sets rXX and rY to 0.0
     * rT1 = T1
     * rT2 = T2
     * rY = Y output
     * rX = U input
     * RET_COMMON.rDt = cycle time main task
     * rDy = temporaryVariable
     * rDx = temporaryVariable
     * rXX = temporaryVariable
     * rZeta = temporaryVariable
     * rOmega = temporaryVariable
     * SQRT = Math.sqrt()
     * MAX = max(x,y) the bigger of the two given values
     *
     * call reset() at the start!
     *
     *
     */

    private double rOmega, rZeta, rDy, rDx, rXX;

    private double rTOne, rTTwo;

    private double rK;


    public PT2(double amplification, double delayOne, double delayTwo){
        rK = amplification;
        rTOne = delayOne;
        rTTwo = delayTwo;
        reset();
    }

    @Override
    public void reset() {
        rXX = 0.0;
        rY = 0.0;
    }

    @Override
    public void updateOutput() {
        rOmega = 1/Math.sqrt(max(rTOne*rTTwo, 0.001));
        rZeta = rOmega*(rTOne+rTTwo)/2.0;

        rDy = cycTime*rOmega*rXX;
        rDx = cycTime*rOmega*(rK*rX - rY - 2*rZeta*rXX);
        rXX = rXX + rDx;
        rY = rY + rDy;
    }
}
