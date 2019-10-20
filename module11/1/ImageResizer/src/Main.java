import java.io.File;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        String srcFolder = "res/";
        File srcDir = new File(srcFolder);
        File[] files = srcDir.listFiles();
        int cores = Runtime.getRuntime().availableProcessors();

        long start = System.currentTimeMillis();

        if (files != null) {
            ExecutorService service = Executors.newFixedThreadPool(cores);
            for (int i = 0; i < files.length; i++) {
                service.submit(new ImageResizer(files[i]));
            }
            service.shutdown();
            try {
                if (!service.awaitTermination(60, TimeUnit.SECONDS)) {
                    service.shutdownNow();
                }
            } catch (InterruptedException ie) {
                service.shutdownNow();
            }
        } else {
            System.out.println("Problem with folder!");
        }

        System.out.println("Общее время: " + (System.currentTimeMillis() - start));
    }
}