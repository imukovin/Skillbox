public class MyLock {
    private long currentNumOfThread;
    private boolean isLock;
    Object obj;

    public void lock() {
            if (currentNumOfThread != Thread.currentThread().getId()) {
                currentNumOfThread = Thread.currentThread().getId();
                isLock = true;
                while (isLock) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
    }

    public void unlock() {
            currentNumOfThread = -1;
            isLock = false;
            notify();
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
