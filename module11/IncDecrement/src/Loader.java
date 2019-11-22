public class Loader {
    private static final MyLock MY_LOCK = new MyLock();
    private static SharedRecource sr;

    public static void main(String[] args) throws InterruptedException {
        sr = new SharedRecource();

        System.out.println(getResult(100000, 10000));
    }

    private static int getResult(int countInc, int countDec) throws InterruptedException {
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
