public class Inc implements Runnable {
    private SharedRecource sr;
    private MyLock myLock;
    private int countInc;

    public Inc(SharedRecource sr, int countInc, MyLock myLock) {
        this.sr = sr;
        this.myLock = myLock;
        this.countInc = countInc;
    }

    @Override
    public void run() {
        myLock.lock();
        //myLock.lock();
        //myLock.lock();
        //myLock.lock();
        for (int i = 0; i < countInc; i++) {
            sr.inc();
            //System.out.println(i);
        }
        myLock.unlock();
        //myLock.unlock();
        //myLock.unlock();
    }
}