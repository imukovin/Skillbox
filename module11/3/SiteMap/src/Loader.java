import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

public class Loader {
    private static final String URL = "https://skillbox.ru/";

    public static void main(String[] args) {
        ArrayList<String> res = new ForkJoinPool().invoke(new InsideLinks(URL));

        System.out.println(URL);
        for (String r : res) {
            int tubsNum = r.split("/").length - 3;
            for (int i = 0; i < tubsNum; i++) {
                System.out.print("\t");
            }
            System.out.println(r);
        }
    }
}
