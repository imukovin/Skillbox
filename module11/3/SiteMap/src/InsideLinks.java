import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class InsideLinks extends RecursiveTask<ArrayList<String>> {
    private String link;

    public InsideLinks(String link) {
        this.link = link;
    }

    @Override
    protected ArrayList<String> compute() {
        ArrayList<String> outLinks = new ArrayList<>();
        Elements links = null;
        try {
            Document doc = Jsoup.connect(link).get();
            links = doc.select("a[href]");
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<InsideLinks> tasks = new ArrayList<>();
        for (Element l : links) {
            String linkIn = l.attr("abs:href");
            if (!linkIn.equals(link)) {
                if (linkIn.contains(link)) {
                    System.out.println(linkIn);
                    outLinks.add(linkIn);
                    InsideLinks task = new InsideLinks(linkIn);
                    task.fork();
                    tasks.add(task);
                }
            }
        }

        for (InsideLinks t : tasks) {
            outLinks.addAll(t.join());
        }

        return outLinks;
    }
}
