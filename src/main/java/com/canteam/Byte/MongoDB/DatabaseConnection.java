package com.canteam.Byte.MongoDB;

import com.mongodb.client.*;
import org.bson.Document;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import java.util.List;

public class DatabaseConnection {
    protected String uri;
    protected MongoClient mongoClient;
    protected static MongoDatabase database;
    protected static MongoCollection<Document> collection;
    protected Document document;

    public DatabaseConnection() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(".properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        uri = properties.getProperty("mongodb.uri");
        mongoClient = MongoClients.create(uri);
    }
    public void connect(String databaseName) {
        database = mongoClient.getDatabase(databaseName);
    }
    public MongoDatabase getDatabase() {
        return database;
    }

    public void close() {
        mongoClient.close();
    }

    public MongoCollection getCollection(String restaurantName) {
        collection = database.getCollection(restaurantName);
        return collection;
    }

    public static void main(String[] args) {
        DatabaseConnection db = new DatabaseConnection();
        db.connect("Stores");
        db.getCollection("Mangyupsal");
        FindIterable<Document> documents = collection.find();

        HashMap<String, HashMap> finalMap = new HashMap<>();

        int i = 0;
        for (Document document : documents) {
            HashMap<String, Object> dbMap = new HashMap<>();
            for (String key : document.keySet()) {
                Object value = document.get(key);
                dbMap.put(key, value);
            }
            finalMap.put((String) dbMap.get("Item_Name"), dbMap);
        }

        List<String> arrTags = (List<String>) finalMap.get("Spicy Pork Rice Bowl").get("Item_Tags");
        System.out.println(finalMap);

        db.close();
    }
}
