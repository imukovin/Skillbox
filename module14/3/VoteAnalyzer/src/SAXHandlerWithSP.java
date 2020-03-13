import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class SAXHandlerWithSP extends DefaultHandler {
    private static final int NUM_FOR_BATCH = 1_000_000;
    private Voter voter;
    private ArrayList<Voter> voterCounts;

    public SAXHandlerWithSP() {
        voterCounts = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        try {
            if (qName.equals("voter")) {
                voter = new Voter(attributes.getValue("name"), attributes.getValue("birthDay"));
            } else if (qName.equals("visit") && voter != null) {
                voterCounts.add(voter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equals("voter")) {
            voter = null;
        } else if (voterCounts.size() == NUM_FOR_BATCH) {
            sendPartToDB();
        }
    }

    private void sendPartToDB() {
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement("INSERT INTO voter_count (name, birthDate) VALUES (?, ?)")) {
            for (Voter voterCount : voterCounts) {
                ps.setString(1, voterCount.getName());
                ps.setString(2, voterCount.getBirthDay());
                ps.addBatch();
            }
            ps.executeBatch();
            voterCounts.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
