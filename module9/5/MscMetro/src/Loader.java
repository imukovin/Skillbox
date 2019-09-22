import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Loader {
    private final static String URL = "https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D1%81%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D0%B9_%D0%9C%D0%BE%D1%81%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%BE%D0%B3%D0%BE_%D0%BC%D0%B5%D1%82%D1%80%D0%BE%D0%BF%D0%BE%D0%BB%D0%B8%D1%82%D0%B5%D0%BD%D0%B0";
    private final static int NUM_ON_PAGE = 3;
    private final static int NUM_LINES_AND_STATIONS = 3;

    private static ArrayList<Station> stations = new ArrayList<>();
    private static Map<String, String> lines = new TreeMap<>();
    private static ArrayList<String> connections = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect(URL).maxBodySize(0).get();

        // Основные станции
        parseStationsFromWikipedia(doc, NUM_ON_PAGE);
        // Монорельс
        parseStationsFromWikipedia(doc, NUM_ON_PAGE + 1);
        // Центральное кольцо
        parseStationsFromWikipedia(doc, NUM_ON_PAGE + 2);

        createJsonFile();
        
        lines.forEach((k, v) -> System.out.println(k + " - " + v));

        System.out.println("\n\n---------Read Json");
        try {
            System.out.println(ReadJson.readJ("outFile/MapMsc.json"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void parseStationsFromWikipedia(Document doc, int numOfTableOnPage) {
        Element table = doc.select("table").get(numOfTableOnPage);
        Elements rows = table.select("tr");

        for (int i = 1; i < rows.size(); i++) {
            Element row = rows.get(i);
            Elements cols = row.select("td");

            if (cols.size() > 0) {
                String lineAndNumber = cols.get(0).select("span").text().replaceAll("\\s+", " ");
                lineAndNumber = delZeroInFirstPosition(lineAndNumber.split(" "));

                String nameOfLine = cols.get(0).select("span").attr("title");

                String nameOfStation = "";
                if (cols.get(1).select("span").size() > 0) {
                    nameOfStation = cols.get(1).select("span").text();
                } else {
                    nameOfStation = cols.get(1).text();
                }

                String connection = cols.get(3).select("span").text().replaceAll("\\s+", " ");
                connection = delZeroInFirstPosition(connection.split(" ")).trim();

                String[] fragmentLineAndNum = lineAndNumber.split(" ");
                if (fragmentLineAndNum.length < NUM_LINES_AND_STATIONS) {
                    stations.add(new Station(nameOfStation, fragmentLineAndNum[0], fragmentLineAndNum[1], connection));
                } else {
                    stations.add(new Station(nameOfStation, fragmentLineAndNum[0], fragmentLineAndNum[2], connection));
                    stations.add(new Station(nameOfStation, fragmentLineAndNum[1], fragmentLineAndNum[2], connection));
                }

                lines.put(fragmentLineAndNum[0], nameOfLine);

                String connectStation = "";
                int numOfSpan = cols.get(3).select("span").size();
                if (numOfSpan <= 2) {
                    connectStation = cols.get(3).select("span")
                            .attr("title")
                            .replace("Переход на станцию ", "");

                    if (connection.length() >= 1) {
                        connections.add(fragmentLineAndNum[0] + "+" + nameOfStation);
                        connections.add(connection + "+" + connectStation.split(" ")[0]);
                    }
                } else {
                    for (int j = 1; j < numOfSpan / 2 + 1; j ++) {
                        connectStation = cols.get(3).select("span").get(j * 2 - 1)
                                .attr("title")
                                .replace("Переход на станцию ", "");

                        if (connection.length() >= 1) {
                            connections.add(fragmentLineAndNum[0] + "+" + nameOfStation);
                            connections.add(connection.split(" ")[j - 1] + "+" + connectStation.split(" ")[0]);
                        }
                    }
                }
            }
        }
    }

    private static String delZeroInFirstPosition(String[] mas) {
        String returnString = "";
        for (int i = 0; i < mas.length; i++) {
            mas[i] = mas[i].startsWith("0") ? mas[i].substring(1) : mas[i];
            returnString = returnString + mas[i] + " ";
        }
        return returnString;
    }

    @SuppressWarnings("unchecked")
    private static void createJsonFile() {
        JSONArray nameStations = new JSONArray();
        JSONObject linesJson = new JSONObject();
        JSONObject stationsJson = new JSONObject();

        for (String key : lines.keySet()) {
            nameStations = new JSONArray();
            for (int i = 0; i < stations.size(); i++) {
                if (key.equals(stations.get(i).getLine())) {
                    nameStations.add(stations.get(i).getName());
                }
            }
            linesJson.put(key, nameStations);
        }

        stationsJson.put("stations", linesJson);

        JSONObject stationJson;
        JSONArray arr;
        JSONArray connectJson = new JSONArray();


        int i = 0;
        while (i < connections.size()) {
            stationJson = new JSONObject();
            arr = new JSONArray();
            stationJson.put("line", connections.get(i).split("\\+")[0]);
            stationJson.put("name", connections.get(i).split("\\+")[1]);
            arr.add(stationJson);
            stationJson = new JSONObject();
            stationJson.put("line", connections.get(i + 1).split("\\+")[0]);
            String stationName = "";
            for (Station s : stations) {
                if (s.getName().contains(connections.get(i + 1).split("\\+")[1])) {
                    stationName = s.getName();
                    break;
                }
            }
            stationJson.put("name", stationName);
            arr.add(stationJson);

            connectJson.add(arr);
            i += 2;
        }

        stationsJson.put("connections", connectJson);


        JSONObject lineObj;
        JSONArray lineArr = new JSONArray();

        for (String key : lines.keySet()) {
            lineObj = new JSONObject();
            lineObj.put("number", key);
            lineObj.put("name", lines.get(key));

            lineArr.add(lineObj);
        }

        stationsJson.put("lines", lineArr);

        try (FileWriter file = new FileWriter("outFile/MapMsc.json")) {
            file.write(stationsJson.toString());
            file.flush();
        } catch (IOException e) {
            System.out.println("Can't open out JSON file!");
        }

    }
}