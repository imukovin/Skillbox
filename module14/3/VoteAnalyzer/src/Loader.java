import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Loader
{
    public static void main(String[] args) throws Exception
    {
        String fileName = "res/data-18M.xml";
        long start = System.currentTimeMillis();
        parseFile(fileName);
        DBConnection.printVoterCounts();
        System.out.println(System.currentTimeMillis() - start + " ms");
    }

    private static void parseFile(String fileName) throws Exception
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File(fileName));

        findEqualVoters(doc);
    }

    private static void findEqualVoters(Document doc) throws Exception
    {
        NodeList voters = doc.getElementsByTagName("voter");
        int votersCount = voters.getLength();
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement("INSERT INTO voter_count (name, birthDate) VALUES (?, ?)")) {
            for(int i = 0; i < votersCount; i++) {
                Node node = voters.item(i);
                NamedNodeMap attributes = node.getAttributes();
                String name = attributes.getNamedItem("name").getNodeValue();
                String birthDay = attributes.getNamedItem("birthDay").getNodeValue();

                birthDay = birthDay.replace('.', '-');
                ps.setString(1, name);
                ps.setString(2, birthDay);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBConnection.addIndex();
    }
}