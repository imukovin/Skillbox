public class MyLock {
    private boolean isLock;
    private SharedRecource sr;

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
            isLock = false;
            sr.notify();
        }
    }

    public void setSr(SharedRecource sr) {
        this.sr = sr;
    }
}
