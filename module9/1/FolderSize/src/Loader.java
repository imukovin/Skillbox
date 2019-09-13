import java.io.File;
import java.util.Scanner;

public class Loader {
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        for (;;) {
            File folder = getPath();
            File[] listFiles = folder.listFiles();

            long dirSize = 0;
            for (File l : listFiles) {
                dirSize += getDirSize(new File(l.toString()));
            }

            convertSizePrint(dirSize);
        }
    }

    public static File getPath() {
        File f;

        for (;;) {
            System.out.println("Введите путь к папке: ");
            String path = in.next();

            f = new File(path);
            if (f.isDirectory()) {
                break;
            } else {
                System.out.println(path + " - не путь к папке!");
            }
        }

        return f;
    }

    public static long getDirSize(File dir) {
        long size = 0;
        if (dir.isFile()) {
            size = dir.length();
        } else {
            File[] subFiles = dir.listFiles();
            for (File file : subFiles) {
                if (file.isFile()) {
                    size += file.length();
                } else {
                    size += getDirSize(file);
                }
            }
        }
        return size;
    }

    public static void convertSizePrint(double size) {
        int numDevisions = 0;

        while (size  > 1024) {
            numDevisions++;
            size /= 1024;
        }

        switch (numDevisions) {
            case 0:
                System.out.println(size + "Б");
                break;
            case 1:
                System.out.printf("%.2f %s %n", size, "Кб");
                break;
            case 2:
                System.out.printf("%.2f %s %n", size, "Мб");
                break;
            case 3:
                System.out.printf("%.2f %s %n", size, "Гб");
                break;
            case 4:
                System.out.printf("%.2f %s %n", size, "Тб");
                break;
        }
    }
}