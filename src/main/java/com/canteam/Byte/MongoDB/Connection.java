package com.canteam.Byte.MongoDB;

import com.mongodb.client.*;
import org.bson.Document;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.ArrayList;

public class Connection {
    private static MongoClient mongoClient;
    private static List<String> collectionNames = new ArrayList<String>();

    private Connection() {}
    public static MongoClient getInstance() {
        if (mongoClient == null) {
            String uri = "mongodb+srv://user:BRObR82T1CpcbZVB@cluster0.d6b9qyq.mongodb.net/";
            mongoClient = MongoClients.create(uri);
        }
        return mongoClient;
    }

    public static void main(String[] args) {
        // Sample
        // import Connection class then create MongoClient then get instance of connection.
        MongoClient client = Connection.getInstance();

        /// Use connection as needed
        MongoDatabase db = client.getDatabase("Stores");
        MongoCollection<Document> collection = db.getCollection("Mangyupsal");
        FindIterable<Document> documents = collection.find();

        HashMap<String, HashMap<String, Object>> finalMap = new HashMap<>();

        for (Document document : documents) {
            HashMap<String, Object> dbMap = new HashMap<>();
            for (String key : document.keySet()) {
                Object value = document.get(key);
                dbMap.put(key, value);
            }
            finalMap.put((String) dbMap.get("Item_Name"), dbMap);
        }



        Integer arrTags = (Integer) finalMap.get("Spicy Chicken Rice Bowl").get("Item_Price");
        System.out.println(arrTags);
    }
}
