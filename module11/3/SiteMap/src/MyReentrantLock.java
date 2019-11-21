import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MyReentrantLock implements Lock {
    private GetLinksCallable links;
    private boolean isLock = false;
    private int numOfCalls = 0;

    public void setLinks(GetLinksCallable links) {
        this.links = links;
    }

    @Override
    public void lock() {
        synchronized (links) {
            isLock = true;
            numOfCalls++;
            if (numOfCalls > 1) {
                while (isLock) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public synchronized void unlock() {
        synchronized (links) {
            isLock = false;
            numOfCalls = 0;
            notify();
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long l, TimeUnit timeUnit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
