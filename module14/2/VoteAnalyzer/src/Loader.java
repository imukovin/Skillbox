import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Loader
{
    // DOM 6281 ms
    // SAX 1998 ms
    // dop
    public static void main(String[] args) throws Exception
    {
        String fileName = "res/data-18M.xml";
        long start = System.currentTimeMillis();

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        SAXHandler handler = new SAXHandler();
        parser.parse(new File(fileName), handler);
        handler.printResult();

        System.out.println(System.currentTimeMillis() - start + " ms");
    }
}