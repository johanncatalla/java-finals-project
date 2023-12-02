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

public class OrderModel {
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

    /**
     * Retrieves a list of rider orders that have statuses "Confirmed" or "Picked-up".
     *
     * @return An ArrayList containing Document objects representing rider orders.
     */
    public static ArrayList<Document> getRiderOrders() {
        ArrayList<Document> userOrders = new ArrayList<>();

        // Define the query to retrieve orders with statuses "Confirmed" or "Picked-up"
        Document query = new Document("Order Status", new Document("$in", Arrays.asList("Confirmed", "Picked-up")));

        // Retrieve and sort orders from the collection
        for (Document doc : orderCollection.find(query).sort(new Document("Order Number", 1))) {
            if (doc != null) {
                // Add retrieved documents to the list
                userOrders.add(doc);
            }
        }

        return userOrders;
    }


    /**
     * Retrieves a list of orders for a specific store with statuses "Ordered" or "Confirmed".
     *
     * @param storeName The name of the store for which orders are to be retrieved.
     * @return An ArrayList containing Document objects representing orders for the specified store.
     */
    public static ArrayList<Document> getStoreOrders(String storeName) {
        ArrayList<Document> userOrders = new ArrayList<>();

        // Define the query to retrieve orders for the specified store with statuses "Ordered" or "Confirmed"
        Document query = new Document("Store", storeName)
                .append("Order Status", new Document("$in", Arrays.asList("Ordered", "Confirmed")));

        // Retrieve and sort orders for the specified store from the collection
        for (Document doc : orderCollection.find(query).sort(new Document("Order Number", 1))) {
            if (doc != null) {
                // Add retrieved documents to the list
                userOrders.add(doc);
            }
        }

        return userOrders;
    }

    /**
     * Retrieves a list of orders for a specific user with statuses "Ordered", "Confirmed", or "Picked-up".
     *
     * @param username The username for which orders are to be retrieved.
     * @return An ArrayList containing Document objects representing orders for the specified user.
     */
    public static ArrayList<Document> getUserOrders(String username) {
        ArrayList<Document> userOrders = new ArrayList<>();

        // Define the query to retrieve orders for the specified user with statuses "Ordered", "Confirmed", or "Picked-up"
        Document query = new Document("UserName", username)
                .append("Order Status", new Document("$in", Arrays.asList("Ordered", "Confirmed", "Picked-up")));

        // Retrieve and sort orders for the specified user from the collection
        for (Document doc : orderCollection.find(query).sort(new Document("Order Number", 1))) {
            if (doc != null) {
                // Add retrieved documents to the list
                userOrders.add(doc);
            }
        }

        return userOrders;
    }

    /**
     * Completes an order by moving it from the 'orderCollection' to the 'historyCollection' based on its order number.
     *
     * @param orderNumber The order number of the order to be completed.
     */
    public static void completeOrder(Integer orderNumber) {
        // Filter to find the order by its order number
        Bson filter = Filters.eq("Order Number", orderNumber);

        // Collection where completed orders will be moved
        MongoCollection<Document> historyCollection = database.getCollection("History");

        // Find the order in the orderCollection
        Document order = orderCollection.find(filter).first();

        // If the order is found, create a copy of the order without the id field and transfer it to history
        if (order != null) {
            Document orderCopy = new Document();
            for (String key : order.keySet()) {
                if (!key.equals("_id")) { // Exclude the "_id" field
                    orderCopy.append(key, order.get(key));
                }
            }

            if (orderCopy != null) {
                // Insert the completed order into history and remove it from the order collection
                historyCollection.insertOne(orderCopy);
                orderCollection.deleteOne(filter);
            }
        }
    }

    /**
     * Updates the status of an order identified by its order number.
     *
     * @param orderNumber The order number of the order to update.
     * @param status The new status to set for the order.
     */
    public static void updateOrderStatus(Integer orderNumber, String status) {
        // Update the order status in the orderCollection based on the order number
        orderCollection.updateOne(Filters.eq("Order Number", orderNumber), Updates.set("Order Status", status));
    }

    /**
     * Retrieves the status of an order identified by its order number.
     *
     * @param orderNumber The order number of the order to retrieve the status.
     * @return The status of the specified order as a String.
     * @throws NullPointerException if the order with the given order number is not found.
     */
    public static String getOrderStatus(Integer orderNumber) {
        // Find the order in the orderCollection by its order number and retrieve its status
        return Objects.requireNonNull(orderCollection.find(Filters.eq("Order Number", orderNumber)).first())
                .getString("Order Status");
    }

    /**
     * Places an order using the items in the user's cart by transferring the cart contents to an order document.
     *
     * @param username The username of the user placing the order.
     */
    public static void placeOrder(String username) {
        // Find the cart document associated with the username
        Bson filter = Filters.eq("UserName", username);
        Document cart = cartCollection.find(filter).first();

        // Create a copy of the cart contents without the id
        Document cartCopy = new Document();
        assert cart != null;
        for (String key : cart.keySet()) {
            if (!key.equals("_id")) {
                cartCopy.append(key, cart.get(key));
            }
        }

        if (cartCopy != null) {
            // Add necessary fields for the order and insert it into the orderCollection
            cartCopy.append("Order Status", "Ordered")
                    .append("Order Number", getOrderNumberCount())
                    .append("Full Name", UserModel.getFullName())
                    .append("Address", UserModel.getLandmark() + ", " + UserModel.getAddressDetails())
                    .append("Contact", UserModel.getUserContact());
            orderCollection.insertOne(cartCopy);

            // Increment the order number count
            incrementOrderNumberCount();
        }

        // Empty the user's cart after placing the order
        CartModel.emptyCart(username);
    }

    /**
     * Retrieves the count of a specific order number in the database.
     *
     * @return The count of the order number.
     */
    private static int getOrderNumberCount() {
        // Find the document with the provided code in the orderCollection
        Document userDocument = orderCollection.find(Filters.eq("Code", "Number")).first();

        if (userDocument != null) {
            // Retrieve and return the order number count if the document exists
            return userDocument.getInteger("Order Number Count");
        }
        return 0; // Return 0 if the document is not found
    }

    /**
     * Increments the count of a specific order number in the database.*
     */
    public static void incrementOrderNumberCount() {
        // Find the document with the provided code in the orderCollection
        Document userDocument = orderCollection.find(Filters.eq("Code", "Number")).first();

        if (userDocument != null) {
            // Get the current order number count, increment it by 1, and update the collection
            int orderNumberCount = userDocument.getInteger("Order Number Count");
            orderCollection.updateOne(Filters.eq("Code", "Number"), Updates.set("Order Number Count", orderNumberCount + 1));
        }
    }
}
