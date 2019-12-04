public class MyLock {
    private boolean isLock = false;
    private long currThread = -1;
    private int callCount = 0;

    public void lock() {
        if (currThread != Thread.currentThread().getId()) {
            synchronized (this) {
                    while (isLock) {
                        try {
                            //System.out.printf("Thread %2d: this.wait%n", Thread.currentThread().getId());
                            if (isLock) {
                                this.wait();
                            }
                            //System.out.printf("Thread %2d: this.waked up%n", Thread.currentThread().getId());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //System.out.printf("Thread %2d: exited while(isLock)%n", Thread.currentThread().getId());
                    callCount++;
                    isLock = true;
                    currThread = Thread.currentThread().getId();
                    //System.out.printf("Thread %2d: isLock=true (was=%s) %n", Thread.currentThread().getId(), Boolean.toString(isLock));
            }
        }
    }

    public void unlock() {
        //System.out.printf("Thread %2d: isLock=false. notify%n", Thread.currentThread().getId());
        synchronized (this) {
            if (currThread == Thread.currentThread().getId()) {
                callCount--;
                if (callCount == 0) {
                    isLock = false;
                    currThread = -1;
                    this.notify();
                }
            }
        }
    }
}
