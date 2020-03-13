import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class Loader
{
    public static void main(String[] args) throws Exception
    {
        String fileName = "res/data-1572M.xml";
        long start = System.currentTimeMillis();
        parseFileWithoutSP(fileName);
        //DBConnection.printVoterCounts();
        DBConnection.closeConnection();
        System.out.println("Without SP: " + (System.currentTimeMillis() - start) + " ms");

        start = System.currentTimeMillis();
        parseFileWithSP(fileName);
        //DBConnection.printVoterCounts();
        DBConnection.closeConnection();
        System.out.println("With SP: " + (System.currentTimeMillis() - start) + " ms");
    }

    private static void parseFileWithSP(String fileName) throws Exception
    {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        SAXHandlerWithSP handler = new SAXHandlerWithSP();
        parser.parse(new File(fileName), handler);
    }

    private static void parseFileWithoutSP(String fileName) throws Exception
    {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        SAXHandlerWithoutSP handler = new SAXHandlerWithoutSP();
        parser.parse(new File(fileName), handler);
    }
}