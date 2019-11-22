public class MyLock {
    private long currentNumOfThread;
    private boolean isLock;
    private SharedRecource sr;

    MyLock() {
        currentNumOfThread = -1;
    }

    public void lock() {
        synchronized (sr) {
            if (!isLock) {
                isLock = true;
                    try {
                        sr.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    public void unlock() {
        synchronized (sr) {
            currentNumOfThread = -1;
            isLock = false;
            sr.notify();
        }
    }

    public void setSr(SharedRecource sr) {
        this.sr = sr;
    }
}
