package com.canteam.Byte.Models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import com.canteam.Byte.MongoDB.Connection;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import org.bson.conversions.Bson;

public abstract class OrderModel {
    private OrderModel() {}
    private static MongoClient client = Connection.getInstance();
    private static MongoDatabase database = client.getDatabase("Byte");
    private static MongoCollection<Document> cartCollection = database.getCollection("Carts");
    private static MongoCollection<Document> orderCollection = database.getCollection("Orders");

    // Get array list of orders based on user type
    // Customer: Ordered, Confirmed, Picked-up
    // Store: Ordered, Confirmed
    // Rider: Confirmed, Picked-up
    // Customer History: Delivered
    public static ArrayList<Document> getRiderOrders() {
        ArrayList<Document> userOrders = new ArrayList<>();
        Document query = new Document("Order Status", new Document("$in", Arrays.asList("Confirmed", "Picked-up")));
        for (Document doc : orderCollection.find(query).sort(new Document("Order Number", 1))) {
            if (doc != null) {
                userOrders.add(doc);
            }
        }
        return userOrders;
    }

    public static ArrayList<Document> getStoreOrders(String storeName) {
        ArrayList<Document> userOrders = new ArrayList<>();
        Document query = new Document("Store", storeName)
                .append("Order Status", new Document("$in", Arrays.asList("Ordered", "Confirmed")));
        for (Document doc : orderCollection.find(query).sort(new Document("Order Number", 1))) {
            if (doc != null) {
                userOrders.add(doc);
            }
        }
        return userOrders;
    }

    public static ArrayList<Document> getUserOrders(String username) {
        ArrayList<Document> userOrders = new ArrayList<>();
        Document query = new Document("UserName", username)
                .append("Order Status", new Document("$in", Arrays.asList("Ordered", "Confirmed", "Picked-up")));
        for (Document doc : orderCollection.find(query).sort(new Document("Order Number", 1))) {
            if (doc != null) {
                userOrders.add(doc);
            }
        }
        return userOrders;
    }

    public static void completeOrder(Integer orderNumber) {
        Bson filter = Filters.eq("Order Number", orderNumber);
        MongoCollection<Document> historyCollection = database.getCollection("History");
        Document order = orderCollection.find(filter).first();

        // Copy the document to another document except the id
        Document orderCopy = new Document();
        assert order != null;
        for (String key : order.keySet()) {
            if (!key.equals("_id")) {
                orderCopy.append(key, order.get(key));
            }
        }

        // transfers the completed order to history
        if (orderCopy != null) {
            historyCollection.insertOne(orderCopy);
            orderCollection.deleteOne(filter);
            System.out.println("Order Ended");
        }

    }


    public static void updateOrderStatus(Integer orderNumber, String status) {
        orderCollection.updateOne(Filters.eq("Order Number", orderNumber), Updates.set("Order Status", status));
    }

    public static String getOrderStatus(Integer orderNumber) {
        return Objects.requireNonNull(orderCollection.find(Filters.eq("Order Number", orderNumber)).first()).getString("Order Status");
    }

    public static void placeOrder(String username) {
        // transfer cart document to order document
        Bson filter = Filters.eq("UserName", username);
        Document cart = cartCollection.find(filter).first();

        // Copy the contents of the cart except the id
        Document cartCopy = new Document();
        assert cart != null;
        for (String key : cart.keySet()) {
            if (!key.equals("_id")) {
                cartCopy.append(key, cart.get(key));
            }
        }

        if (cartCopy != null) {
            // Add order status and order number fields to the order
            cartCopy.append("Order Status", "Ordered")
                    .append("Order Number", getOrderNumberCount("Number"))
                    .append("Full Name", UserModel.getFullName())
                    .append("Address", UserModel.getLandmark()+", "+UserModel.getAddressDetails())
                    .append("Contact", UserModel.getUserContact());
            orderCollection.insertOne(cartCopy);

            incrementOrderNumberCount("Number");
            System.out.println("Successfully placed order");
        }

        CartModel.emptyCart(username);
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
