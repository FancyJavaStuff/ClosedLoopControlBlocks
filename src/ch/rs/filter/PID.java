package ch.rs.filter;

import ch.rs.filter.master.ClosedLoopBlock;
import ch.rs.filter.util.MathFunctions;

public class PID extends ClosedLoopBlock {

    /**
     * Boolean for the usage of the different P,I and D parts
     */
    private boolean useP = false, useI = false, useD = false;

    /**
     * Boolean for the use of the Max and Min AntiWindUp
     */
    private boolean antiWindUpMax = false, antiWindUpMin = false;

    /**
     * Amplification
     */
    private double amplification;

    /**
     * This is the delay applied at the end.
     */
    private double afterDelay;

    /**
     * This is the delay applied at the beginning.
     */
    private double preDelay;

    /**
     * used as delay for DT1.
     */
    private double dDelay;


    /**
     * These variables are used to store values that are needed in the calculations of the output.
     */
    private double temporaryY, temporaryX, pResult, iResult, dResult,
            temporaryI, altX, altY;


    public PID(double amplification, double preDelay, double endDelay, double dtDelay) {
        this.amplification = amplification;
        this.preDelay = preDelay;
        afterDelay = endDelay;
        dDelay = dtDelay;
        reset();
    }

    @Override
    public void reset() {
        pResult = 0.0;
        iResult = 0.0;
        dResult = 0.0;
        output = 0.0;
    }

    @Override
    protected void calculateOutput() {
        calculateP();
        calculateI();
        calculateD();
        altY = pResult + iResult + dResult;
        output = altY;
    }

    private void calculateP() {
        if (useP) {
            pResult = amplification * input;
            return;
        }
        pResult = 0;
    }

    private void calculateI() {
        if (useI) {
            temporaryI = amplification / MathFunctions.
                    biggerValue(afterDelay, 0.001) * (input * cycleTime);
            return;
        }
        iResult = 0;
        calculateAntiWindUp();
    }

    private void calculateAntiWindUp() {
        if (antiWindUpMax && (Double.compare(input, 0) > 0) ||
                antiWindUpMin && (Double.compare(input, 0) < 0)) {
            return;
        }
        iResult += temporaryI;
    }

    private void calculateD() {
        if (useD && (Double.compare(dDelay, (2.0 * cycleTime)) >= 0)) {
            temporaryX = amplification * preDelay * (input - altX);
            altX = input;
            temporaryY = temporaryX / MathFunctions.biggerValue(dDelay, avoidZero);
            dResult = dResult - MathFunctions.clamp(-1000.0, dResult /
                    MathFunctions.biggerValue(dDelay, avoidZero), 1000.0) * cycleTime + temporaryY;
            return;
        }
        dResult = 0.0;
    }

    public boolean isUseP() {
        return useP;
    }

    public void setUseP(boolean useP) {
        this.useP = useP;
    }

    public boolean isUseI() {
        return useI;
    }

    public void setUseI(boolean useI) {
        this.useI = useI;
    }

    public boolean isUseD() {
        return useD;
    }

    public void setUseD(boolean useD) {
        this.useD = useD;
    }

    public boolean isAntiWindUpMax() {
        return antiWindUpMax;
    }

    public void setAntiWindUpMax(boolean antiWindUpMax) {
        this.antiWindUpMax = antiWindUpMax;
    }

    public boolean isAntiWindUpMin() {
        return antiWindUpMin;
    }

    public void setAntiWindUpMin(boolean antiWindUpMin) {
        this.antiWindUpMin = antiWindUpMin;
    }

    public double getAmplification() {
        return amplification;
    }

    public void setAmplification(double amplification) {
        this.amplification = amplification;
    }

    public double getAfterDelay() {
        return afterDelay;
    }

    public void setAfterDelay(double afterDelay) {
        this.afterDelay = afterDelay;
    }

    public double getPreDelay() {
        return preDelay;
    }

    public void setPreDelay(double preDelay) {
        this.preDelay = preDelay;
    }

    public double getdDelay() {
        return dDelay;
    }

    public void setdDelay(double dDelay) {
        this.dDelay = dDelay;
    }
}
