public class MyLock {
    private int numOfCalls;
    private boolean isLock;

    public void lock() {
        if (!Thread.currentThread().getName().equals("main") && numOfCalls == 0) {
            synchronized (this) {
                if (!isLock) {
                    isLock = true;
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        numOfCalls++;
    }

    public void unlock() {
        numOfCalls = 0;
        synchronized (this) {
            isLock = false;
            this.notify();
        }
    }
}
