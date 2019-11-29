public class MyLock {
    private boolean isLock = false;
    private long currThread = -1;

    public void lock() {
        if (Thread.currentThread().getId() != 1 && currThread != Thread.currentThread().getId()) {
            synchronized (this) {
                if (isLock) {
                    while (isLock) {
                        try {
                            System.out.printf("Thread %2d: this.wait%n", Thread.currentThread().getId());
                            if (isLock) {
                                this.wait();
                            }
                            System.out.printf("Thread %2d: this.waked up%n", Thread.currentThread().getId());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.printf("Thread %2d: exited while(isLock)%n", Thread.currentThread().getId());
                } else {
                    isLock = true;
                    currThread = Thread.currentThread().getId();
                    System.out.printf("Thread %2d: isLock=true (was=%s) %n", Thread.currentThread().getId(), Boolean.toString(isLock));
                }
            }
        }
    }

    public void unlock() {
        System.out.printf("Thread %2d: isLock=false. notify%n", Thread.currentThread().getId());
        isLock = false;
        currThread = -1;
        synchronized (this) {
            this.notify();
        }
    }
}
