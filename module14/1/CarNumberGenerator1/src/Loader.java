import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Loader
{
    private static final int NUM_OF_THREADS = 100;

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        ExecutorService taskExecutor = Executors.newFixedThreadPool(NUM_OF_THREADS);

        for (int i = 1; i <= NUM_OF_THREADS; i++) {
            String fileName = "res/numbers" + i + ".txt";
            int regionCode = i;
            taskExecutor.execute(new Thread(new Runrun(fileName, regionCode)));
        }
        taskExecutor.shutdown();
        taskExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        System.out.println((System.currentTimeMillis() - start) + " ms");
    }
}