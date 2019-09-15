import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Scanner;

import static java.lang.Integer.MAX_VALUE;

public class Loader {
    private static final Scanner in = new Scanner(System.in);

    private static long dirSize;

    public static void main(String[] args) throws IOException {
        for (;;) {
            Path path = getPath();
            dirSize = 0;

            Files.walkFileTree(path, new HashSet<>(), MAX_VALUE, new FileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                        throws IOException {
                    //System.out.println("visitFile: " + file);
                    dirSize += file.toFile().length();
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path path, IOException e) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path path, IOException e) throws IOException {
                    return FileVisitResult.CONTINUE;
                }
            });


            convertSizePrint(dirSize);
        }
    }

    public static Path getPath() {
        Path p;
        File f;
        for (;;) {
            System.out.println("Введите путь к папке: ");
            String path = in.next();

            f = new File(path);
            if (f.isDirectory()) {
                p = Paths.get(path);
                break;
            } else {
                System.out.println(path + " - не путь к папке!");
            }
        }
        return p;
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