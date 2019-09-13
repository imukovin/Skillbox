import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class Loader {
    private static final Scanner in = new Scanner(System.in);
    private static File folderFrom;
    private static File folderTo;

    public static void main(String[] args) throws IOException {
        folderFrom = getPath("Введите путь к копируемой папке: ");
        folderTo = getPath("Введите путь к папке в которую копируем: ");

        moveFiles(folderFrom);
    }

    public static File getPath(String message) {
        File f;

        for (;;) {
            System.out.println(message);
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

    public static void moveFiles(File from) throws IOException {
        File[] subFiles = from.listFiles();
        for (File file : subFiles) {
            String s = file.getPath()
                    .replaceAll("\\\\", "\\/")
                    .replaceAll(
                            folderFrom.getPath().replaceAll("\\\\", "\\/"),
                            folderTo.getPath().replaceAll("\\\\", "\\/"));

            if (file.isFile()) {
                File f = new File(s);
                Files.copy(file.toPath(), f.toPath());
            } else {
                new File(s).mkdir();
                moveFiles(file);
            }
        }
    }
}