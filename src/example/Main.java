package example;

import ch.rs.filter.*;
import ch.rs.filter.master.BlockCalculationRunnable;

import java.io.PrintWriter;

public class Main {

    public static void main(String[] args) throws Exception {

        double div = 1000000000.0;

        double start = System.nanoTime() / div;

        double valueToReach = 230.0;
        double currentValueOne = 0.0;
        double currentValueTwo = 0.0;
        double currentValueThree = 0.0;
        double currentValueFour = 0.0;
        double currentValueFive = 0.0;
        double currentValueSix = 0.0;
        double currentValueSeven = 0.0;

        PrintWriter out = new PrintWriter("pt1.csv");

        PT1 ptOne = new PT1(0.2, 0.3);
        PT2 ptTwo = new PT2(0.2, 0.3, 0.3);
        P p = new P(0.2);
        I i = new I(0.2, 0.3);
        D d = new D(0.2, 0.2);
        PID pid = new PID(0.2, 0.2, 0.2, 0.2);
        pid.setUseP(true);
        pid.setUseI(true);
        pid.setUseD(true);
        ptOne.setInput(valueToReach - currentValueOne);
        ptTwo.setInput(valueToReach - currentValueTwo);
        p.setInput(valueToReach - currentValueThree);
        i.setInput(valueToReach - currentValueFour);
        d.setInput(valueToReach - currentValueFive);
        pid.setInput(valueToReach - currentValueSix);
        out.println("ValueToReach;PT1;PT2;P;I;D;PID;");
        out.println(valueToReach + ";0.0;0.0;0.0;0.0;0.0;0.0;");
        BlockCalculationRunnable b = new BlockCalculationRunnable();
        Thread thread = new Thread(b);
        b.addAllClosedLoopBlocks(ptOne, ptTwo, p, i, d, pid);
        int timePasse = 1;
        thread.start();
        while (timePasse < 1000) {
            out.println(valueToReach + ";" + currentValueOne + ";" + currentValueTwo + ";" +
                    currentValueThree + ";" + currentValueFour + ";" + currentValueFive + ";" +
                    currentValueSix + ";");
            currentValueOne = ptOne.getOutput();
            currentValueTwo = ptTwo.getOutput();
            currentValueThree = p.getOutput();
            currentValueFour = i.getOutput();
            currentValueFive = d.getOutput();
            currentValueSix = pid.getOutput();
            ptOne.setInput(valueToReach - currentValueOne);
            ptTwo.setInput(valueToReach - currentValueTwo);
            p.setInput(valueToReach);// - currentValueThree);
            i.setInput(valueToReach);// - currentValueFour);
            d.setInput(valueToReach);// - currentValueFive);
            pid.setInput(valueToReach - currentValueSix);
            /**ptOne.updateOutput();
             ptTwo.updateOutput();
             p.updateOutput();
             i.updateOutput();
             d.updateOutput();
             pid.updateOutput();**/
            if (timePasse == 300) {
                valueToReach = -20;
            }
            if (timePasse == 600) {
                valueToReach = 600;
            }
            timePasse++;
        }
        out.close();
        System.out.println((System.nanoTime() / div - start));
        b.stop();
        System.exit(0);
    }
}
