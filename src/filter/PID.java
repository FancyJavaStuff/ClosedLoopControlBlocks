package filter;

public class PID extends ClosedLoopBlock {

    /**
     *
     * G(jw) = Kr*[1+1/(Tn*jw)+(Tv*jw/(1+Tvz*jw)]
     *
     */

    /**
     * Variable for the different P,I and D parts
     */
    private boolean useP = false, useI = false, useD = false;

    /**
     * Variable for the Max and Min AntiWindUp
     */
    private boolean antiWindUpMax = false, antiWindUpMin = false;

    /**
     * amplification
     */
    private double rKr;

    /**
     * enddelay
     */
    private double rTn;

    /**
     * startdelay
     */
    private double rTv;

    /**
     * used as delay for DT1
     */
    private double rTvz;


    private double  rDy, rDx, rProportional, rIntegral, rDifferential,
            rIntegralCalc, rXeAlt, rYcalc;


    private final double rAvoidZero= 0.001;


    public PID(double amplification, double preDelay, double endDelay, double dtDelay){
        rKr = amplification;
        rTv = preDelay;
        rTn = endDelay;
        rTvz = dtDelay;
        reset();
    }

    @Override
    public void reset() {
        rProportional = 0.0;
        rIntegral = 0.0;
        rDifferential = 0.0;
        rY = 0.0;
    }

    @Override
    public void updateOutput() {
        calculateP();
        calculateI();
        calculateD();
        rYcalc = rProportional + rIntegral + rDifferential;
        rY = rYcalc;
    }

    private void calculateP(){
        if (useP) {
            rProportional = rKr*rX;
            return;
        }
        rProportional = 0;
    }

    private void calculateI(){
        if(useI){
            rIntegralCalc = rKr/max(rTn,0.001)*(rX*cycTime);
            return;
        }
        rIntegral = 0;
        calculateAntiWindUp();
    }

    private void calculateAntiWindUp(){
        if(antiWindUpMax && (Double.compare(rX, 0) > 0) ||
                antiWindUpMin && (Double.compare(rX, 0) < 0)){
            return;
        }
        rIntegral += rIntegralCalc;
    }

    private void calculateD(){
        if(useD && (Double.compare(rTvz, (2.0*cycTime)) >= 0)){
            rDx = rKr*rTv*(rX - rXeAlt);
            rXeAlt = rX;
            rDy = rDx/max(rTvz, rAvoidZero);
            rDifferential = rDifferential - limit(-1000.0, rDifferential/max(rTvz, rAvoidZero), 1000.0)*cycTime + rDy;
            return;
        }
        rDifferential = 0.0;
    }

    public void setAntiWindUpMax(boolean value){
        antiWindUpMax = value;
    }

    public boolean isAntiWindUpMaxOn(){
        return antiWindUpMax;
    }

    public void setAntiWindUpMin(boolean value){
        antiWindUpMin = value;
    }

    public boolean isAntiWindUpMinOn(){
        return antiWindUpMin;
    }

    public void setUseP(boolean value){
        useP = value;
    }

    public boolean getUseP(){
        return useP;
    }

    public void setUseI(boolean value){
        useI = value;
    }

    public boolean getUseI(){
        return useI;
    }

    public void setUseD(boolean value){
        useD = value;
    }

    public boolean getUseD(){
        return useD;
    }
}
