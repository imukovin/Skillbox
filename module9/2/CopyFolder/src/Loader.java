import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    private static File getPath(String message) {
        File f;

        for (; ; ) {
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

    private static void moveFiles(File from) throws IOException {
        File[] subFiles = from.listFiles();

        if (subFiles == null) {
            System.out.println("Папка копирования пуста!");
            return;
        }

        for (File file : subFiles) {
            Path oldPath = Paths.get(file.getPath()).normalize();
            Path newPath = Paths.get(folderTo.getPath()).resolve(Paths.get(folderFrom.getPath()).relativize(oldPath));

            if (file.isFile()) {
                File f = new File(newPath.toString());
                Files.copy(file.toPath(), f.toPath());
            } else {
                boolean isCreated = new File(newPath.toString()).mkdir();
                moveFiles(file);
            }
        }
    }
}