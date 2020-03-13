import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.sql.SQLException;

public class SAXHandlerWithoutSP extends DefaultHandler {
    private static final int NUM_FOR_BATCH = 1_000_000;

    private Voter voter;
    private int countVoters;
    private StringBuilder query;

    public SAXHandlerWithoutSP() {
        query = new StringBuilder();
        countVoters = 0;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        try {
            if (qName.equals("voter")) {
                voter = new Voter(attributes.getValue("name"), attributes.getValue("birthDay"));
            } else if (qName.equals("visit") && voter != null) {
                query.append((query.length() == 0) ? "" : ", ")
                        .append("('")
                        .append(voter.getName())
                        .append("', '")
                        .append(voter.getBirthDay())
                        .append("')");
                countVoters++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equals("voter")) {
            voter = null;
        } else if (countVoters == NUM_FOR_BATCH) {
            sendPartToDB();
        }
    }

    private void sendPartToDB() {
        try {
            DBConnection.multiQuery(query);
            query = new StringBuilder();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        countVoters = 0;
    }
}
