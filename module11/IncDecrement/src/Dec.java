public class Dec implements Runnable {
    private SharedRecource sr;
    private MyLock myLock;
    private int countDec;

    public Dec(SharedRecource sr, int countDec, MyLock myLock) {
        this.sr = sr;
        this.myLock = myLock;
        this.countDec = countDec;

        this.myLock.setSr(sr);
    }

    @Override
    public void run() {
            myLock.lock();
        //synchronized (sr) {
            for (int i = 0; i < countDec; i++) {
                sr.dec();
            }
       // }
            myLock.unlock();
    }
}
