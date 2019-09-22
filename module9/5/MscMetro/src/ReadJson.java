import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ReadJson {
    private final static String dataFile = "outFile/MapMsc.json";

    public String readJ() throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonData = (JSONObject) parser.parse(getJsonFile());

        JSONObject stationsObject = (JSONObject) jsonData.get("stations");
        String out = parseStations(stationsObject);
        return out;
    }

    private static String parseStations(JSONObject stationsObject)
    {
        String out = "";

        for (Object line : stationsObject.keySet()) {

            JSONArray stationsArray = (JSONArray) stationsObject.get(line);
            out = out + "Line №" + line + ": " + stationsArray.size() + "\n";
        }
        return out;
    }

    private static String getJsonFile()
    {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(dataFile));
            lines.forEach(line -> builder.append(line));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return builder.toString();
    }
}
