import java.io.File;
import java.util.Arrays;

public class Main
{
    public static void main(String[] args)
    {
        String srcFolder = "res/";
        File srcDir = new File(srcFolder);
        File[] files = srcDir.listFiles();

        int cores = Runtime.getRuntime().availableProcessors();

        if (files.length < cores) {
            new ImageResizer(files).start();
        } else {
            int countForOne = files.length / cores;
            for (int i = 0; i < cores; i++) {
                File[] f = Arrays.copyOfRange(files, i * countForOne, (i + 1) * countForOne);
                new ImageResizer(f).start();
            }
        }
    }
}