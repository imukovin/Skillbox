public class MyLock {
    private boolean isLock = false;
    private long currThread = -1;

    public void lock() {
        if (Thread.currentThread().getId() != 1) {
            if (!isLock && currThread != Thread.currentThread().getId()) {
                currThread = Thread.currentThread().getId();
                isLock = true;
                synchronized (this) {
                    while (isLock) {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public void unlock() {
        isLock = false;
        currThread = -1;
        synchronized (this) {
            this.notify();
        }
    }
}
