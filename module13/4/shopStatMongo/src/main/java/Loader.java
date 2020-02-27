import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Loader {
    private static final int PRICE_LESS_THAN = 100;

    private static MongoCollection<Document> collection;
    private static MongoDatabase database;

    public static void main(String[] args) {
        new GenerateDB();

        MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);
        database = mongoClient.getDatabase("SkillboxShops");

        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("------------- Menu ---------------");
            System.out.println("add_shop название_магазина");
            System.out.println("add_staff название_товара цена");
            System.out.println("put_staff название_товара название_магазина");
            System.out.println("staff_stat");
            System.out.print("Введите команду: ");
            String command = in.nextLine();
            String[] commandParts = command.split(" ");
            switch (commandParts[0]) {
                case "add_shop":
                    collection = database.getCollection("shops");
                    try {
                        Document firstDocument = new Document()
                                .append("name", commandParts[1])
                                .append("staffList", new ArrayList<>());
                        collection.insertOne(firstDocument);
                    } catch (Exception e) {
                        System.out.println("--------> Неверно указана команда!");
                    }
                    break;
                case "add_staff":
                    collection = database.getCollection("staff");
                    try {
                        Document firstDocument = new Document()
                                .append("name", commandParts[1])
                                .append("price", commandParts[2]);
                        collection.insertOne(firstDocument);
                    } catch (Exception e) {
                        System.out.println("--------> Неверно указана команда!");
                    }
                    break;
                case "put_staff":
                    BasicDBObject query = new BasicDBObject("name", commandParts[2]);
                    BasicDBObject query1 = new BasicDBObject("$addToSet", new BasicDBObject("staffList", commandParts[1]));
                    try {
                        collection = database.getCollection("shops");
                        collection.updateOne(query, query1);
                    } catch (Exception e) {
                        System.out.println("--------> Неверно указана команда!");
                    }
                    break;
                case "staff_stat":
                    shopsStats();
                    break;
                default:
                    System.out.println("--------> Неверно указана команда!");
                    break;
            }
        }
    }

    private static void shopsStats() {
        collection = database.getCollection("shops");
        List<Document> aggregations = new ArrayList<>();
        aggregations.add(
                new Document("$lookup",
                        new Document()
                                .append("localField", "staffList")
                                .append("foreignField", "name")
                                .append("from", "staff")
                                .append("as", "goodsRef")
                )
        );
        aggregations.add(
                new Document("$unwind", new Document("path", "$goodsRef"))
        );
        aggregations.add(
                new Document("$addFields",
                        new Document("priceIsLessThan100",
                                new Document("$cond", new Document("if",
                                        new Document("$lt", Arrays.asList("$goodsRef.price", PRICE_LESS_THAN)))
                                                .append("then", 1)
                                                .append("else", 0))))
        );
        aggregations.add(
                new Document("$group",
                        new Document()
                                .append("_id", "$name")
                                .append("avg", new Document("$avg", "$goodsRef.price"))
                                .append("min", new Document("$min", "$goodsRef.price"))
                                .append("max", new Document("$max", "$goodsRef.price"))
                                .append("count", new Document("$sum", 1))
                                .append("countWherePriceIsLessThan100", new Document("$sum", "$priceIsLessThan100"))
                )
        );

        try {
            for(Document doc : collection.aggregate(aggregations)) {
                System.out.println("--------> " + doc.getString("_id")
                        + "\nСредняя цена: " + doc.getDouble("avg")
                        + "\nМин цена: " + doc.getInteger("min")
                        + "\nМакс цена: " + doc.getInteger("max")
                        + "\nОбщее число товара: " + doc.getInteger("count")
                        + "\nКоличество товара, стоимость < 100: " + doc.getInteger("countWherePriceIsLessThan100")
                );
            }
        }
        catch(MongoException exception) {
            System.out.println("Не удалось получить статистику");
            exception.printStackTrace();
        }
    }
}
