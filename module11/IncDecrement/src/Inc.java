public class Inc implements Runnable {
    private SharedRecource sr;
    private MyLock myLock;
    private int countInc;

    public Inc(SharedRecource sr, int countInc, MyLock myLock) {
        this.sr = sr;
        this.myLock = myLock;
        this.countInc = countInc;

        this.myLock.setSr(sr);
    }

    @Override
    public void run() {
            //myLock.lock();
        //synchronized (sr) {
            for (int i = 0; i < countInc; i++) {
                sr.inc();
            }
        //}
            //myLock.unlock();
    }
}