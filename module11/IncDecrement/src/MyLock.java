public class MyLock {
    private volatile boolean isLock = false;
    //private volatile long currThread = -1;

    public void lock() {
        if (Thread.currentThread().getId() != 1) {
            if (!isLock) {
                System.out.printf("Thread %2d: isLock=true (was=%s) %n", Thread.currentThread().getId(), Boolean.toString(isLock));
                synchronized (this) {
                    //currThread = Thread.currentThread().getId();
                    isLock = true;
                    while (isLock) {
                        try {
                            System.out.printf("Thread %2d: this.wait%n", Thread.currentThread().getId());
                            this.wait();
                            System.out.printf("Thread %2d: this.waked up%n", Thread.currentThread().getId());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.printf("Thread %2d: exited while(isLock)%n", Thread.currentThread().getId());
                }
            }
        }
    }

    public void unlock() {
        System.out.printf("Thread %2d: isLock=false. notify%n", Thread.currentThread().getId());
        synchronized (this) {
            isLock = false;
            //currThread = -1;
            this.notify();
        }
    }
}
