import java.util.*;
import java.util.concurrent.*;

public class Loader {
    private static final String URL = "https://skillbox.ru/";

    public static void main(String[] args) throws Exception {
        Set<String> allLinks = new HashSet<>();
        ExecutorService service = Executors.newFixedThreadPool(4);

        Future res = service.submit(new GetLinksCallable(URL));
        boolean isAdded = true;
        while (isAdded != false) {
            Set<String> links = (Set<String>) res.get();
            isAdded = allLinks.addAll(links);
            for (String l : links) {
                res = service.submit(new GetLinksCallable(l));
            }
        }
        service.shutdown();

        for (String a : allLinks) {
            System.out.println(a);
        }
    }
}
