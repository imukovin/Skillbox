import java.util.concurrent.ThreadPoolExecutor;

public class Loader
{
    public static final int NUM_OF_THREADS = 100;

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();

        for (int i = 1; i <= NUM_OF_THREADS; i++) {
            String fileName = "res/numbers" + i + ".txt";
            int regionCode = i;
            Thread t = new Thread(new Runrun(fileName, regionCode));
            t.start();
            t.join();
        }

        System.out.println((System.currentTimeMillis() - start) + " ms");
    }
}