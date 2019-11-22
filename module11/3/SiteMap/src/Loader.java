import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

public class Loader {
    private static final String URL = "https://skillbox.ru/";
    private static final int NUM_OF_SLASH = 3;

    public static void main(String[] args) throws Exception {
        Set<String> allLinks = new HashSet<>();
        ExecutorService service = Executors.newFixedThreadPool(10);
        CompletionService<Set<String>> completionService = new ExecutorCompletionService<>(service);

        completionService.submit(new GetLinksCallable(URL, URL));
        boolean errors = false;
        int numOfTasks = 1;
        while (!errors && numOfTasks > 0) {
            Future<Set<String>> resultFuture = completionService.take();
            numOfTasks--;
            try {
                Set<String> result = resultFuture.get();
                for (String l : result) {
                    if (!allLinks.contains(l)) {
                        allLinks.add(l);
                        completionService.submit(new GetLinksCallable(l, URL));
                        numOfTasks++;
                    }
                }
            }
            catch(Exception e) {
                errors = true;
            }
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.HOURS);

        List<String> sortedList = new ArrayList<>(allLinks);
        Collections.sort(sortedList);

        try(FileWriter writer = new FileWriter("outElem/siteMap.txt", false))
        {
            String text = "";
            writer.write(URL + "\n");
            for (String a : sortedList) {
                for (int i = 0; i < a.split("/").length - NUM_OF_SLASH; i++) {
                    text = text + "\t";
                }
                text = text + a + "\n";
                writer.write(text);
                text = "";
            }
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}