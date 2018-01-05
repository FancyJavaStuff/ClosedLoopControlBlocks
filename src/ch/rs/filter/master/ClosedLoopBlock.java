package ch.rs.filter.master;


import ch.rs.filter.util.Lock;

public abstract class ClosedLoopBlock {


    /**
     * Internal output variable
     */
    protected volatile double output;

    /**
     * Internal inout variable
     */
    protected volatile double input;

    protected Lock calculationLock = new Lock();
    protected Lock outputLock = new Lock();
    protected Lock inputLock = new Lock();

    protected final double cycleTime = 0.01;

    protected final double avoidZero = 0.001;

    public abstract void reset();

    protected abstract void calculateOutput();

    public final void updateOutput() {
        calculationLock.lock();
        calculateOutput();
        outputLock.unlock();
    }


    public void setInput(double input) {
        inputLock.lock();
        this.input = input;
        calculationLock.unlock();
    }

    public double getOutput() {
        outputLock.lock();
        inputLock.unlock();
        return output;

    }
}
