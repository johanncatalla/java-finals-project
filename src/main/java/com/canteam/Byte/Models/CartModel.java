package com.canteam.Byte.Models;

import java.nio.file.DirectoryStream;
import java.util.HashMap;
import java.util.Map;

import com.canteam.Byte.MongoDB.Connection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;


public class CartModel {
    private CartModel() {}
    private static HashMap<String, HashMap<String, String>> cart;
    private static int subtotal;
    private static int totalPriceOfOrder;
    private static String modeOfPayment;

    // Connection
    private static final MongoClient client = Connection.getInstance();
    private static final MongoDatabase db = client.getDatabase("Byte");
    private static final MongoCollection<Document> collection = db.getCollection("Carts");

    public static void createUserCart(String username) {
        Document newUser = new Document("UserName", username)
                .append("Cart", new HashMap<String, HashMap<String, String>>())
                .append("Subtotal", 0.0)
                .append("Total Price of Order", 0.0)
                .append("Mode of Payment", "Cash on Delivery");
        collection.insertOne(newUser);
    }

    public static void addToCart(String username, String name, double price, int quantity, String store, String instructions) {
        CartModel.defineCart(username);
        if (cart.containsKey(name)) {

            // Update item quantity if item is already in cart
            collection.updateOne(Filters.eq("UserName", username), Updates.set("Cart."+name+".Quantity", CartModel.getItemQuantityFromCart(username, name)+quantity));
            // Update item total price accordingly
            collection.updateOne(Filters.eq("UserName", username), Updates.set("Cart."+name+".Total Price", CartModel.getItemTotalPriceFromCart(username, name)));

            System.out.println("Successfully updated");
        } else {
            int itemTotalPrice = (int) (price * quantity);

            // Create item document containing item details
            Document itemDB = new Document("Name", name)
                    .append("Price", price)
                    .append("Quantity", quantity)
                    .append("Store", store)
                    .append("Instructions", instructions)
                    .append("Total Price", itemTotalPrice);

            // Append item document to cart
            collection.updateOne(Filters.eq("UserName", username), Updates.set("Cart."+name, itemDB));
            // Update item total price in cart
            collection.updateOne(Filters.eq("UserName", username), Updates.set("Cart."+name+".Total Price", CartModel.getItemTotalPriceFromCart(username, name)));
            System.out.println("Successfully added");
        }
        // update order subtotal and total price
        updateSubtotalAndTotalPrice(username);
        CartModel.defineCart(username);
    }

    private static int getItemQuantityFromCart(String username, String itemName) {
        Document userDocument = collection.find(Filters.eq("UserName", username)).first();

        if (userDocument != null) {
            Document cartDocument = (Document) userDocument.get("Cart");
            Document itemDocument = (Document) cartDocument.get(itemName);
            int quantity = itemDocument.getInteger("Quantity");

            return quantity;
        }
        return 0;
    }

    private static int getItemTotalPriceFromCart(String username, String itemName) {
        Document userDocument = collection.find(Filters.eq("UserName", username)).first();

        if (userDocument!= null) {
            Document cartDocument = (Document) userDocument.get("Cart");
            Document itemDocument = (Document) cartDocument.get(itemName);
            int quantity = itemDocument.getInteger("Quantity");
            double price = itemDocument.getDouble("Price");

            return (int) (quantity * price);
        }
        return 0;
    }

    private static void updateSubtotalAndTotalPrice(String username) {
        // Retrieve the user document
        Document userDocument = collection.find(Filters.eq("UserName", username)).first();

        if (userDocument != null) {
            // Get the cart from the user document
            Document cartDocument = (Document) userDocument.get("Cart");

            // Calculate the subtotal
            int subtotal = 0;
            for (String key : cartDocument.keySet()) {
                Document itemDocument = (Document) cartDocument.get(key);
                subtotal += itemDocument.getInteger("Total Price");
            }

            // Calculate the total price of order
            int totalPriceOfOrder = subtotal + 20;

            // Update the Subtotal and Total Price of Order fields in the user document
            collection.updateOne(Filters.eq("UserName", username), Updates.set("Subtotal", subtotal));
            collection.updateOne(Filters.eq("UserName", username), Updates.set("Total Price of Order", totalPriceOfOrder));
        }
    }


    public static void emptyCart(String username) {
        collection.updateOne(Filters.eq("UserName", username), Updates.set("Cart", new Document()));
        collection.updateOne(Filters.eq("UserName", username), Updates.set("Subtotal", 0));
        collection.updateOne(Filters.eq("UserName", username), Updates.set("Total Price of Order", 0));
        // update local cart
        defineCart(username);
    }

    // setters
    public static void changeModeOfPayment(String username, String mode) {
        // Note: default mode of payment is cash on delivery
        collection.updateOne(Filters.eq("UserName", username), Updates.set("Mode of Payment", mode));
        modeOfPayment = mode;
    }
    public static void defineCart(String username) {
        cart = new HashMap<String, HashMap<String, String>>();
        // Retrieve the user document
        final Document userDocument = collection.find(Filters.eq("UserName", username)).first();
        // If the user document exists
        if (userDocument != null) {
            // Get the cart from the user document
            Document cartDocument = (Document) userDocument.get("Cart");
            // Iterate over each item in the cart
            for (String key : cartDocument.keySet()) {
                Document itemDocument = (Document) cartDocument.get(key);

                // Create a new HashMap for the item
                HashMap<String, String> item = new HashMap<String, String>();

                // Add each field from the item document to the item HashMap
                for (Map.Entry<String, Object> entry : itemDocument.entrySet()) {
                    item.put(entry.getKey(), entry.getValue().toString());
                }
                // Add the item to the cart
                cart.put(key, item);
            }

            // update local variables for subtotal and total
            subtotal = userDocument.getInteger("Subtotal");
            totalPriceOfOrder = userDocument.getInteger("Total Price of Order");

        }

    }
    //getters
    public static HashMap<String, HashMap<String, String>> getCart() { return cart; }
    public static int getSubtotal() { return subtotal; }
    public static int getTotalPriceOfOrder() { return totalPriceOfOrder; }
    public static String getModeOfPayment() { return modeOfPayment; }

    public static void main(String[] args) {
        // Sample usage
        // define cart to update local variable cart
        CartModel.defineCart("johann");
        System.out.println(cart);

        // add to cart
        // adding to cart already syncs cart in database to local cart
        CartModel.addToCart("johann", "Chicken", 100, 2, "Mangyupsa;", "Instructions");
        System.out.println(cart);
        CartModel.addToCart("johann", "Chicken", 100, 2, "Mangyupsa;", "Instructions");
        System.out.println(cart);
        CartModel.addToCart("johann", "Chicken", 100, 2, "Mangyupsa;", "Instructions");
        System.out.println(cart);
        System.out.println("Subtotal: "+CartModel.getSubtotal());
        System.out.println("Total Price of Order: "+CartModel.getTotalPriceOfOrder());

        // CartModel.emptyCart("johann");
        System.out.println(cart);
    }
}
