import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Loader {
    public final static String URL = "https://lenta.ru/";

    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect(URL).maxBodySize(0).get();

        Elements elements = doc.select("img[src~=(?i)\\.(png|jpe?g)]");

        System.out.println("I'm working!");
        System.out.print("Progress--> ");
        elements.forEach(element -> {
            String[] pic = element.attr("abs:src").split("/");
            URL url = null;
            try {
                url = new URL(element.attr("abs:src"));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            if (url != null) {
                try (InputStream in = url.openStream()) {
                    in.transferTo(new BufferedOutputStream(new FileOutputStream("outPicture/" + pic[pic.length - 1])));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.print("#");
        });
        System.out.println();
        System.out.println("That's all!");
    }
}
