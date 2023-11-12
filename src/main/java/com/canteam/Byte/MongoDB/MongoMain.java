package com.canteam.Byte.MongoDB;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MongoMain {
    protected String uri;
    protected MongoClient mongoClient;
    protected static MongoDatabase database;
    protected static MongoCollection<Document> collection;
    protected Document document;

    public MongoMain() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(".properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        uri = properties.getProperty("mongodb.uri");
        mongoClient = MongoClients.create(uri);
    }
    public void connect() {
        database = mongoClient.getDatabase("Byte");
    }

    public void close() {
        mongoClient.close();
    }

    public static void getCollection() {
        collection = database.getCollection("Stores");
    }

    public void addDocument(String key, String value) {
        getCollection();
        document = new Document(key, value);
        collection.insertOne(document);
    }

    public static void main(String[] args) {
        MongoMain db = new MongoMain();
        db.connect();
        db.addDocument("Hello", "World");
    }
}
