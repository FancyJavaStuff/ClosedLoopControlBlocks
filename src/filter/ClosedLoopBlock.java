package filter;

public abstract class ClosedLoopBlock {


    /**
     * Internal output variable
     */
    protected double rY;

    /**
     * Internal inout variable
     */
    protected double rX;

    protected final double cycTime = 0.01;

    abstract void reset();

    abstract void updateOutput();

    public void setInput(double input){
        rX = input;
    }

    public double getOutput(){
        return rY;
    }

    protected final double max(double x1, double x2){
        if(Double.compare(x1, x2) > 0){
            return x1;
        }
        return x2;
    }

    protected final double limit(double min, double value, double max){
        if(Double.compare(value, min) < 0){
            return min;
        } else if (Double.compare(value, max) > 0){
            return max;
        }
        return value;
    }
}
