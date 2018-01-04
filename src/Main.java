import filter.*;

import java.io.PrintWriter;

public class Main {

    public static void main(String[] args) throws Exception{
        double valueToReach= 230;
        double currentValueOne = 35;
        double currentValueTwo = 35;
        double currentValueThree = 35;
        double currentValueFour = 35;
        double currentValueFive = 35;
        double currentValueSix = 35;
        double currentValueSeven = 35;

        PrintWriter out = new PrintWriter("pt1.csv");

        PT1 ptOne = new PT1(0.2, 0.3);
        PT2 ptTwo = new PT2(0.2, 0.3, 0.3);
        P p = new P(0.2);
        I i = new I(0.2,0.3);
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
        out.println(valueToReach +";0.0;0.0;0.0;0.0;0.0;0.0;");
        int timePasse = 1;
        while(timePasse < 1000){
            currentValueOne = ptOne.getOutput();
            currentValueTwo = ptTwo.getOutput();
            currentValueThree = p.getOutput();
            currentValueFour = i.getOutput();
            currentValueFive = d.getOutput();
            currentValueSix = pid.getOutput();
            out.println(valueToReach+";"+currentValueOne+";"+currentValueTwo+";"+
                    currentValueThree+";"+currentValueFour+";"+currentValueFive+";"+
                    currentValueSix+";");
            ptOne.setInput(valueToReach - currentValueOne);
            ptTwo.setInput(valueToReach - currentValueTwo);
            p.setInput(valueToReach);// - currentValueThree);
            i.setInput(valueToReach);// - currentValueFour);
            d.setInput(valueToReach);// - currentValueFive);
            pid.setInput(valueToReach - currentValueSix);
            ptOne.updateOutput();
            ptTwo.updateOutput();
            p.updateOutput();
            i.updateOutput();
            d.updateOutput();
            pid.updateOutput();
            timePasse++;
            if(timePasse == 300){
                valueToReach = -20;
            }
            if(timePasse == 600){
                valueToReach = 600;
            }
        }
        out.close();
        System.out.println("done");


    }
}
