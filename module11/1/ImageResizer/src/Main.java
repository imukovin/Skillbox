import java.io.File;
import java.util.Arrays;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String srcFolder = "res/";
        File srcDir = new File(srcFolder);
        File[] files = srcDir.listFiles();

        long start = System.currentTimeMillis();
        int cores = Runtime.getRuntime().availableProcessors();

        if (files != null) {
            if (files.length < cores) {
                new ImageResizer(files);
            } else {
                int countForOne = files.length / cores;
                ExecutorService service = Executors.newFixedThreadPool(cores);
                for (int i = 0; i < cores; i++) {
                    File[] f = Arrays.copyOfRange(files, i * countForOne, (i + 1) * countForOne);
                    //service.submit(new ImageResizer(f));
                    Thread t = new ImageResizer(f);
                    //t.start();
                    //t.join();

                    service.submit(t);
                }
                service.shutdown();
            }
        } else {
            System.out.println("Problem with folder!");
        }

        System.out.println("Общее время: " + (System.currentTimeMillis() - start));
    }
}