import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.*;
import java.util.ArrayList;

public class GenerateDB {
    private static final int MAX_PRICE = 400;
    public static final String FILE_NAME = "staff.txt";

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    private String[] shopMas = {"Семерочка", "Восьмерочка", "Девяточка"};
    private ArrayList<String> staff;

    GenerateDB() {
        mongoClient = new MongoClient("127.0.0.1", 27017);
        database = mongoClient.getDatabase("SkillboxShops");
        staff = new ArrayList<>();
        try {
            addStaff();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        addShops();
    }

    public void addShops() {
        collection = database.getCollection("shops");
        collection.drop();
        for (int i = 0; i < shopMas.length; i++) {
            Document firstDocument = new Document()
                    .append("name", shopMas[i])
                    .append("staffList", getStaffForShop());
            collection.insertOne(firstDocument);
        }
    }

    public void addStaff() throws FileNotFoundException {
        collection = database.getCollection("staff");
        collection.drop();

        ClassLoader classLoader = Loader.class.getClassLoader();
        File file = new File(classLoader.getResource(FILE_NAME).getFile());
        FileReader fr = new FileReader(file);
        try (BufferedReader reader = new BufferedReader(fr);) {
            String line = reader.readLine();
            while (line != null) {
                int price = (int) (Math.random() * (MAX_PRICE + 1));
                Document firstDocument = new Document()
                        .append("name", line)
                        .append("price", price);
                collection.insertOne(firstDocument);
                staff.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> getStaffForShop() {
        int staffCount = (int) (Math.random() * staff.size());
        ArrayList<String> shopStaff = new ArrayList<>();
        for (int i = 0; i < staffCount; i++) {
            String staff1 = staff.get((int) (Math.random() * staff.size()));
            while (shopStaff.contains(staff1)) {
                staff1 = staff.get((int) (Math.random() * staff.size()));
            }
            shopStaff.add(staff1);
        }
        return shopStaff;
    }
}
