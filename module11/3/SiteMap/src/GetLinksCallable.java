import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

public class GetLinksCallable implements Callable {
    private static final String MAIN_URL = "https://skillbox.ru/";

    private Set<String> linksFromPage = new HashSet<>();
    private String link;

    public GetLinksCallable(String link) {
        this.link = link;
    }

    @Override
    public Object call() throws Exception {
        Elements element = null;
        try {
            Document doc = Jsoup.connect(link).maxBodySize(0).get();
            element = doc.select("a[href]");
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Element l : element) {
            String linkIn = l.attr("abs:href");
            if (!linkIn.equals(MAIN_URL)) {
                if (linkIn.contains(MAIN_URL)) {
                    //System.out.println(linkIn);
                    linksFromPage.add(linkIn);
                }
            }
        }
        return linksFromPage;
    }
}
