import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

public class GetLinksCallable implements Callable<Set<String>> {
    private Set<String> linksFromPage = new HashSet<>();
    private String link;
    private String mainUrl;

    public GetLinksCallable(String link, String mainUrl) {
        this.link = link;
        this.mainUrl = mainUrl;
    }

    @Override
    public Set<String> call() {
        Elements element;
        try {
            Document doc = Jsoup.connect(link).maxBodySize(0).get();
            element = doc.select("a[href]");
        } catch (IOException e) {
            //e.printStackTrace();
            return null;
        }

        for (Element l : element) {
            String linkIn = l.attr("abs:href");
            if (!linkIn.equals(mainUrl)) {
                if (linkIn.contains(mainUrl)) {
                    //System.out.println(linkIn);
                    linksFromPage.add(linkIn);
                }
            }
        }
        return linksFromPage;
    }
}