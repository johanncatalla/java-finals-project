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

    // Private constructor to prevent instantiation of Connection class
    private Connection() {}

    /**
     * Gets the instance of the MongoDB client.
     * If the client is null, it creates a new instance and returns it.
     *
     * @return The instance of the MongoDB client.
     */
    public static MongoClient getInstance() {
        if (mongoClient == null) {
            // MongoDB connection URI
            String uri = "mongodb+srv://byteUser:g2t5j456ROVCBhqu@cluster0.d6b9qyq.mongodb.net/?retryWrites=true&w=majority";

            // Create a new MongoDB client instance
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
