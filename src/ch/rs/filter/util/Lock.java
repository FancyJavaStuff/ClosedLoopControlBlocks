package ch.rs.filter.util;

public class Lock {

    private volatile boolean isLocked = false;

    public synchronized void lock() {
        while (isLocked) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Error at wait in Lock");
            }
        }
        isLocked = true;
    }

    public synchronized void unlock() {
        isLocked = false;
        notify();
    }

}
