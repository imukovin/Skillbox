import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;

public class Loader {
    public final static String URL = "https://lenta.ru/";

    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect(URL).get();

        Elements elements = doc.select("img[src~=(?i)\\.(png|jpe?g)]");

        System.out.println("I'm working!");
        System.out.print("Progress--> ");
        elements.forEach(element -> {
            String[] pic = element.attr("abs:src").split("/");
            try {
                URL url = new URL(element.attr("abs:src"));
                InputStream in = url.openStream();
                OutputStream out = new BufferedOutputStream(new FileOutputStream("outPicture/" + pic[pic.length - 1]));

                for (int b; (b = in.read()) != -1;) {
                    out.write(b);
                }
                out.close();
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.print("#");
        });
        System.out.println();
        System.out.println("That's all!");
    }
}
