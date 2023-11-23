package com.canteam.Byte.Models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import com.canteam.Byte.MongoDB.Connection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.BsonField;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;


import com.canteam.Byte.Models.CartModel;
import org.bson.conversions.Bson;

public class OrderModel {
    private OrderModel() {}

    private static HashMap<Integer, HashMap<String, HashMap<String, String>>> userOrderItems = new HashMap<>();
    private static HashMap<Integer, HashMap<String, String>> userOrderDetails = new HashMap<>();
    private static MongoClient client = Connection.getInstance();
    private static MongoDatabase database = client.getDatabase("Byte");
    private static MongoCollection<Document> cartCollection = database.getCollection("Carts");
    private static MongoCollection<Document> orderCollection = database.getCollection("Orders");

    public static void placeOrder(String username) {
        // transfer cart document to order document
        Bson filter = Filters.eq("UserName", username);
        Document cart = cartCollection.find(filter).first();
        if (cart != null) {
            System.out.println("Successfully placed order");
            orderCollection.insertOne(cart);

            // Add new Order Status and Order Number fields to order
            orderCollection.updateOne(filter, Updates.set("Order Status", "Ordered"));
            orderCollection.updateOne(filter, Updates.set("Order Number", getOrderNumberCount("Number")));
            incrementOrderNumberCount("Number");
        }
        CartModel.emptyCart(username);
        System.out.println("Successfully placed order");
    }

    private static int getOrderNumberCount(String code) {
        Document userDocument = orderCollection.find(Filters.eq("Code", code)).first();

        if (userDocument!= null) {
            int orderNumberCount = userDocument.getInteger("Order Number Count");
            System.out.println(orderNumberCount);
            return orderNumberCount;
        }
        return 0;
    }

    public static void incrementOrderNumberCount(String code) {
        Document userDocument = orderCollection.find(Filters.eq("Code", code)).first();

        if (userDocument!= null) {
            int orderNumberCount = userDocument.getInteger("Order Number Count");
            orderCollection.updateOne(Filters.eq("Code", code), Updates.set("Order Number Count", orderNumberCount + 1));
        }
    }

    public static void main(String[] args) {
        placeOrder("admin");
    }
}
