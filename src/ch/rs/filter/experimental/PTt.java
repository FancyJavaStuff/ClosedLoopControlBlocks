package ch.rs.filter.experimental;

import ch.rs.filter.master.ClosedLoopBlock;
import ch.rs.filter.util.MathFunctions;

public class PTt extends ClosedLoopBlock {

    /**
     * delay
     */
    private double delay;

    /**
     * amplification
     */
    private double amplification;

    /**
     * Variables used in combination with the array usage
     */
    private int i, x;

    /**
     * Array used for calculations
     */
    private double[] arX;

    public PTt(double k, double t) {
        arX = new double[5000];
        amplification = k;
        delay = t;
        output = 0.0;
    }

    @Override
    public void calculateOutput() {
        x = (int) MathFunctions.clamp(1, delay /
                MathFunctions.biggerValue(cycleTime, 0.001), 5000);
        for (i = x; i > 1; i--) {
            arX[i] = arX[i - 1];
        }
        arX[0] = input;
        output = amplification * arX[x];
    }


    @Override
    public void reset() {
        output = 0.0;
        delay = cycleTime;
    }

    public void setDelay(double delay) {
        this.delay = delay;
    }

    public double getDelay() {
        return delay;
    }

    public double getAmplification() {
        return amplification;
    }

    public void setAmplification(double amplification) {
        this.amplification = amplification;
    }
}
