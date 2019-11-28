import com.sun.tools.javac.Main;

import java.util.concurrent.locks.ReentrantLock;

public class Loader {
    private static final MyLock MY_LOCK = new MyLock();
    private SharedRecource sr;

    Loader(SharedRecource sr) {
        this.sr = sr;
    }

    public static void main(String[] args) throws InterruptedException {
        //sr = new SharedRecource();
        //System.out.println(getResult(100000, 10000));
    }

    public int getResult(int countInc, int countDec) throws InterruptedException {
        Inc inc = new Inc(sr, countInc, MY_LOCK);
        Dec dec = new Dec(sr, countDec, MY_LOCK);

        Thread one = new Thread(inc);
        Thread two = new Thread(dec);

        one.start();
        two.start();
        one.join();
        two.join();

        return sr.getValue();
    }
}
