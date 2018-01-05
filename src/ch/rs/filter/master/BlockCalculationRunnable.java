package ch.rs.filter.master;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BlockCalculationRunnable implements Runnable {

    private volatile boolean run = false;

    private volatile List<ClosedLoopBlock> blockList =
            new LinkedList<>();

    @Override
    public void run() {
        run = true;
        while (run && !Thread.currentThread().isInterrupted()) {
            blockList.forEach(ClosedLoopBlock::updateOutput);
        }
    }

    public void stop() {
        run = false;
    }

    public <T extends ClosedLoopBlock> void addClosedLoopBlock(T block) {
        blockList.add(block);
    }

    public <T extends ClosedLoopBlock> void addAllClosedLoopBlocks(T... blocks) {
        Collections.addAll(blockList, blocks);
    }

}
