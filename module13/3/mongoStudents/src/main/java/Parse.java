import au.com.bytecode.opencsv.CSVReader;
import com.mongodb.BasicDBObject;
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

        String[] nextLine;
        Document firstDocument;
        try (CSVReader reader = new CSVReader(new FileReader(file), ',' , '"' , 0)) {
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
    }

    private void sendToMongoServer(Document firstDocument) {
        collection.insertOne(firstDocument);
    }

    public long getStudentsCount() {
        BsonDocument query = BsonDocument.parse("{}");
        return collection.countDocuments(query);
    }

    public long getStudentsAgeMoreFourteen() {
        BasicDBObject query = new BasicDBObject("age", new BasicDBObject("$gt", 40));
        return collection.countDocuments(query);
    }

    public String getNamesYoungOrCoursesOfOldStudents(SortType sortType) {
        BasicDBObject query;
        if (sortType == SortType.ASC) {
            query = new BasicDBObject("age", 1);
        } else {
            query = new BasicDBObject("age", -1);
        }
        Document document = collection.find().sort(query).limit(1).first();
        int age = (int) document.get("age");
        query = new BasicDBObject("age", new BasicDBObject("$eq", age));
        StringBuilder students = new StringBuilder();
        for (Document doc : collection.find(query)) {
            if (sortType == SortType.ASC) {
                students
                        .append(doc.get("name"))
                        .append(", ");
            } else {
                students
                        .append(doc.get("name"))
                        .append(" ")
                        .append(doc.get("courses"))
                        .append(", ");
            }
        }
        return students.toString();
    }
}
