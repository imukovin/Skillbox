import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.BsonObjectId;
import org.bson.Document;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Loader {
    private static final int PRICE_LATTER_THAN = 100;

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
                    System.out.println();
                    staffAmountToShops();
                    System.out.println();
                    avgValueForEachShop();
                    System.out.println();
                    minAndMaxPricePerStaff();
                    System.out.println();
                    countOfStaffLaterThan100();
                    break;
                default:
                    System.out.println("--------> Неверно указана команда!");
                    break;
            }
        }
    }

    private static void staffAmountToShops() {
        //> db.shops.aggregate([{$match:{name:"Восьмерочка"}}, {$project:{"staffList1":{$s
        //ize:"$staffList"}}}, {$group:{"_id":null, "count":{$sum:"$staffList1"}}}])
        //{ "_id" : null, "count" : 4 }
        System.out.println(">>>>> Общее кол-во товара в каждом магазине: ");
        collection = database.getCollection("shops");
        for (Document doc : collection.find()) {
            AggregateIterable<Document> output = collection.aggregate(Arrays.asList(
                    new Document("$match", new Document("name", doc.get("name"))),
                    new Document("$project", new Document("staffList1", new Document("$size", "$staffList"))),
                    new Document("$group", new Document("_id", doc.get("name"))
                            .append("count", new Document("$sum", "$staffList1")))));
            for (Document d : output)
            {
                System.out.println("\t\t" + d.get("_id") + ": " + d.get("count"));
            }
        }
    }

    private static void avgValueForEachShop() {
        //> db.shops.aggregate([{$match:{name:"Девяточка"}}, {$lookup: {from:"staff", loca
        //    lField: "staffList", foreignField:"name", as: "prices"}}, {$unwind: "$prices"},
        //{$project:{"price": "$prices.price"}}, {$group:{_id:0, avgP:{$avg:"$price"}}}]).
        //pretty()
        System.out.println(">>>>> Среднее значение цены в каждом магазине: ");
        collection = database.getCollection("shops");
        for (Document doc : collection.find()) {
            AggregateIterable<Document> output = collection.aggregate(Arrays.asList(
                    new Document("$match", new Document("name", doc.get("name"))),
                    new Document("$lookup", new Document("from", "staff")
                            .append("localField", "staffList")
                            .append("foreignField", "name")
                            .append("as", "prices")),
                    new Document("$unwind", "$prices"),
                    new Document("$project", new Document("price", "$prices.price")),
                    new Document("$group", new Document("_id", doc.get("name"))
                            .append("avgP", new Document("$avg", "$price")))));
            for (Document d : output)
            {
                System.out.println("\t\t" + d.get("_id") + ": " + d.get("avgP"));
            }
        }
    }

    private static void minAndMaxPricePerStaff() {
        //> db.shops.aggregate([{$match:{name:"Девяточка"}}, {$lookup: {from:"staff", loca
         //   lField: "staffList", foreignField:"name", as: "prices"}}, {$unwind: "$prices"},
        //{$project:{"price": "$prices.price"}}, {$group:{_id:0, min:{$min:"$price"}, max:
        //{$max:"$price"}}}]).pretty()
        System.out.println(">>>>> Мин/макс значение цены в каждом магазине: ");
        collection = database.getCollection("shops");
        for (Document doc : collection.find()) {
            AggregateIterable<Document> output = collection.aggregate(Arrays.asList(
                    new Document("$match", new Document("name", doc.get("name"))),
                    new Document("$lookup", new Document("from", "staff")
                            .append("localField", "staffList")
                            .append("foreignField", "name")
                            .append("as", "prices")),
                    new Document("$unwind", "$prices"),
                    new Document("$project", new Document("price", "$prices.price")),
                    new Document("$group", new Document("_id", doc.get("name"))
                            .append("min", new Document("$min","$price"))
                            .append("max", new Document("$max", "$price")))));
            for (Document d : output)
            {
                System.out.println("\t\t" + d.get("_id") + ": min " + d.get("min") + "  max " + d.get("max"));
            }
        }
    }

    private static void countOfStaffLaterThan100() {
        //> db.shops.aggregate([{$match:{name:"Девяточка"}}, {$lookup: {from:"staff", loca
        //    lField: "staffList", foreignField:"name", as: "prices"}}, {$unwind: "$prices"},
        //{$project:{"price": "$prices.price"}}, {$match:{price:{$lt:100}}}, {$count:"coun
        //    t"}]).pretty()
        System.out.println(">>>>> Кол-во товара меньше 100 руб: ");
        collection = database.getCollection("shops");
        for (Document doc : collection.find()) {
            AggregateIterable<Document> output = collection.aggregate(Arrays.asList(
                    new Document("$match", new Document("name", doc.get("name"))),
                    new Document("$lookup", new Document("from", "staff")
                            .append("localField", "staffList")
                            .append("foreignField", "name")
                            .append("as", "prices")),
                    new Document("$unwind", "$prices"),
                    new Document("$project", new Document("price", "$prices.price")),
                    new Document("$match", new Document("price", new Document("$lt", PRICE_LATTER_THAN))),
                    new Document("$count", "count")));
            for (Document d : output)
            {
                System.out.println("\t\t" + doc.get("name") + ": " + d.get("count"));
            }
        }
    }
}
