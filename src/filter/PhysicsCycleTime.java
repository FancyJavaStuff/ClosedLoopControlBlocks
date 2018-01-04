package filter;

import java.util.LinkedList;

public class PhysicsCycleTime implements Runnable{

    private boolean run = false;
    private LinkedList<ClosedLoopBlock> blockList =
        new LinkedList();


    @Override
    public void run() {
        run = true;
        while(run){
            blockList.forEach(block -> block.updateOutput());
        }
    }

    public void stop(){
        run = false;
    }

    public <T extends ClosedLoopBlock> void addClosedLoopBlock(T block){
        if(block.getClass() != ClosedLoopBlock.class) {
            blockList.add(block);
        } else {
            System.out.println("Cant input " + block.getClass().toString());
        }
    }
}
