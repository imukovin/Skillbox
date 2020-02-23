import au.com.bytecode.opencsv.CSVReader;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.Document;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class Parse {
    private static final String CSV_FILE_NAME = "mongo.csv";

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    Parse() {
        mongoClient = new MongoClient("127.0.0.1", 27017);
        database = mongoClient.getDatabase("SkillboxUsers");
        collection = database.getCollection("students");
        collection.drop();
        try {
            parseCsvFileAndAddToMongo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseCsvFileAndAddToMongo() throws IOException {
        ClassLoader classLoader = Loader.class.getClassLoader();
        File file = new File(classLoader.getResource(CSV_FILE_NAME).getFile());
        CSVReader reader = new CSVReader(new FileReader(file), ',' , '"' , 0);

        String[] nextLine;
        Document firstDocument;
        while ((nextLine = reader.readNext()) != null) {
            ArrayList<String> courses = new ArrayList<>();
            Collections.addAll(courses, nextLine[2].split(","));
            firstDocument = new Document()
                    .append("name", nextLine[0])
                    .append("age", Integer.parseInt(nextLine[1]))
                    .append("courses", courses);
            sendToMongoServer(firstDocument);
        }
    }

    private void sendToMongoServer(Document firstDocument) {
        collection.insertOne(firstDocument);
    }

    public int getStudentsCount() {
        AtomicInteger count = new AtomicInteger();
        BsonDocument query = BsonDocument.parse("{}");
        collection.find(query).forEach((Consumer<Document>) document -> {
            count.getAndIncrement();
        });
        return count.get();
    }

    public int getStudentsAgeMoreFourteen() {
        AtomicInteger count = new AtomicInteger();
        BsonDocument query = BsonDocument.parse("{\"age\" : {$gt : 40}}");
        collection.find(query).forEach((Consumer<Document>) document -> {
            count.getAndIncrement();
        });
        return count.get();
    }

    public void getNameOfYoung() {
        collection.find(BsonDocument.parse("{}"))
                .sort(BsonDocument.parse("{\"age\":1}"))
                .forEach((Consumer<Document>) document -> {
                System.out.println(document.get("name") + " (" + document.get("age") + ")");
        });
    }
}
