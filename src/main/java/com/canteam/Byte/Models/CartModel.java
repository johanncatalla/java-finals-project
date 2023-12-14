package com.canteam.Byte.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.canteam.Byte.MongoDB.Connection;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;


public class CartModel {

    /**
     * Private constructor to prevent instantiation of CartModel objects.
     */
    private CartModel() {}
    private static HashMap<String, HashMap<String, String>> cart;
    private static int subtotal;
    //
    private static int totalPriceOfOrder;
    private static String modeOfPayment;
    private static String store;

    // Connection
    private static final MongoClient client = Connection.getInstance();
    private static final MongoDatabase db = client.getDatabase("Byte");
    private static final MongoCollection<Document> collection = db.getCollection("Carts");

    /**
     * Creates a new user cart in the database with initial values.
     *
     * @param username The username of the user for whom the cart is created.
     */
    public static void createUserCart(String username) {
        // Create a new document for the user with default cart details
        Document newUser = new Document("UserName", username) // Username of the user
                .append("Cart", new HashMap<String, HashMap<String, String>>()) // Empty cart
                .append("Subtotal", 0.0) // Initial subtotal set to 0.0
                .append("Total Price of Order", 0.0) // Initial total price set to 0.0
                .append("Mode of Payment", "Cash on Delivery") // Default payment mode
                .append("Store", null); // Store information (null for now)

        // Insert the new user document into the collection
        collection.insertOne(newUser);
    }

    /**
     * Adds an item to the user's cart with provided details.
     * If the item already exists in the cart, updates the quantity and total price.
     *
     * @param username     The username of the user whose cart is updated.
     * @param name         The name of the item to be added to the cart.
     * @param price        The price of the item.
     * @param quantity     The quantity of the item to be added.
     * @param store        The store where the item is purchased from.
     * @param instructions Any specific instructions related to the item.
     * @param size         The size of the item (if applicable).
     * @param imageName    The image name or reference for the item.
     */
    public static void addToCart(String username, String name, int price, int quantity,
                                 String store, String instructions, HashMap<String, Integer> size,
                                 String imageName) {
        int itemTotalPrice = (price * quantity);

        // Create item document containing item details
        Document itemDB = new Document("Name", name)
                .append("Price", price)
                .append("Quantity", quantity)
                .append("Instructions", instructions)
                .append("Total Price", itemTotalPrice)
                .append("Size", size)
                .append("Image", imageName);

        // Check if item exists in cart and instructions differ
        if (cart.containsKey(name)) {
            // Update item quantity if item is already in cart
            itemDB.append("Quantity", CartModel.getItemQuantityFromCart(username, name) + quantity);
            // Update item total price accordingly
            itemDB.append("Total Price", CartModel.getItemTotalPriceFromCart(username, name) + (price * quantity));
        }

        // Prepare updates
        List<Bson> updates = new ArrayList<>();
        updates.add(Updates.set("Store", store));
        updates.add(Updates.set("Cart." + name, itemDB));

        // Apply updates
        collection.updateOne(Filters.eq("UserName", username), Updates.combine(updates));

        // Fetch cart from database to local cart
        CartModel.defineCart(username);
    }

    /**
     * Retrieves the quantity of a specific item from the user's cart.
     *
     * @param username  The username of the user whose cart is being accessed.
     * @param itemName  The name of the item for which the quantity is retrieved.
     * @return          The quantity of the specified item in the user's cart.
     */
    private static int getItemQuantityFromCart(String username, String itemName) {
        // Find the user's document in the collection
        Document userDocument = collection.find(Filters.eq("UserName", username)).first();

        if (userDocument != null) {
            // Get the cart document from the user's document
            Document cartDocument = (Document) userDocument.get("Cart");

            // Get the item's document from the cart using the itemName
            Document itemDocument = (Document) cartDocument.get(itemName);

            // Retrieve the quantity of the item from its document
            int quantity = itemDocument.getInteger("Quantity");

            return quantity;
        }
        // If user document or item document doesn't exist, return 0
        return 0;
    }

    /**
     * Deletes a specific item from the user's cart by its name.
     *
     * @param username  The username of the user whose cart is being modified.
     * @param itemName  The name of the item to be removed from the cart.
     */
    public static void deleteItemFromCart(String username, String itemName) {
        // Delete the specified item from the user's cart
        collection.updateOne(Filters.eq("UserName", username), Updates.unset("Cart." + itemName));

        // If the cart becomes empty after deletion, set the store to null
        if (CartModel.getCart().isEmpty()) {
            collection.updateOne(Filters.eq("UserName", username), Updates.set("Store", null));
        }

        // Fetch and define the updated cart after the deletion
        CartModel.defineCart(username);
    }


    /**
     * Retrieves the total price of a specific item in the user's cart.
     *
     * @param username  The username of the user whose cart is being accessed.
     * @param itemName  The name of the item for which the total price is retrieved.
     * @return          The total price of the specified item in the user's cart.
     */
    private static int getItemTotalPriceFromCart(String username, String itemName) {
        // Find the user's document in the collection
        Document userDocument = collection.find(Filters.eq("UserName", username)).first();

        if (userDocument != null) {
            // Get the cart document from the user's document
            Document cartDocument = (Document) userDocument.get("Cart");

            // Get the item's document from the cart using the itemName
            Document itemDocument = (Document) cartDocument.get(itemName);

            // Retrieve the quantity and price of the item from its document
            int quantity = itemDocument.getInteger("Quantity");
            int price = itemDocument.getInteger("Price");

            // Calculate and return the total price of the item (quantity * price)
            return (quantity * price);
        }
        // If user document or item document doesn't exist, return 0
        return 0;
    }

    /**
     * Updates the subtotal and total price of order for a user's cart.
     *
     * @param username The username of the user whose cart values are being updated.
     */
    private static void updateSubtotalAndTotalPrice(String username) {
        // Retrieve the user document
        Document userDocument = collection.find(Filters.eq("UserName", username)).first();

        if (userDocument != null) {
            // Get the cart from the user document
            Document cartDocument = (Document) userDocument.get("Cart");

            // Calculate the subtotal
            int subtotalIt = 0;
            for (String key : cartDocument.keySet()) {
                Document itemDocument = (Document) cartDocument.get(key);
                subtotalIt += itemDocument.getInteger("Total Price");
            }

            // Calculate the total price of order (considering a fixed additional cost of 20)
            int totalPriceOfOrderIt = subtotalIt + 20;

            // Update the Subtotal and Total Price of Order fields in the user document
            collection.updateOne(Filters.eq("UserName", username), Updates.set("Subtotal", subtotalIt));
            collection.updateOne(Filters.eq("UserName", username), Updates.set("Total Price of Order", totalPriceOfOrderIt));

            // Update local variables for subtotal and total
            subtotal = subtotalIt;
            totalPriceOfOrder = totalPriceOfOrderIt;
        }
    }

    /**
     * Empties the cart and resets associated values for a specified user.
     *
     * @param username The username of the user whose cart is being emptied.
     */
    public static void emptyCart(String username) {
        // Update database fields to empty the cart and reset related values
        collection.updateOne(Filters.eq("UserName", username), Updates.set("Cart", new Document()));
        collection.updateOne(Filters.eq("UserName", username), Updates.set("Subtotal", 0));
        collection.updateOne(Filters.eq("UserName", username), Updates.set("Total Price of Order", 0));
        collection.updateOne(Filters.eq("UserName", username), Updates.set("Store", null));
        collection.updateOne(Filters.eq("UserName", username), Updates.set("Mode of Payment", "Cash on Delivery"));

        // Update local cart
        defineCart(username);
    }

    // setters
    /**
     * Changes the mode of payment for a specified user.
     * Note: The default mode of payment is cash on delivery.
     *
     * @param username The username of the user whose mode of payment is being updated.
     * @param mode     The new mode of payment.
     */
    public static void changeModeOfPayment(String username, String mode) {
        // Update the mode of payment in the database for the specified user
        collection.updateOne(Filters.eq("UserName", username), Updates.set("Mode of Payment", mode));

        // Update the local variable for mode of payment
        modeOfPayment = mode;
    }

    /**
     * Defines the user's cart by retrieving its contents from the database.
     *
     * @param username The username of the user whose cart is being defined.
     */
    public static void defineCart(String username) {
        // Initialize a new HashMap to represent the user's cart
        cart = new HashMap<String, HashMap<String, String>>();

        // Retrieve the user document from the database
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
                    // Convert non-null values to strings, handle null values
                    if (entry.getValue() != null) {
                        item.put(entry.getKey(), entry.getValue().toString());
                    } else {
                        item.put(entry.getKey(), null);
                    }
                }
                // Add the item to the cart
                cart.put(key, item);
            }

            // Update subtotal, total price, store, and mode of payment based on user document
            updateSubtotalAndTotalPrice(username);
            store = (String) userDocument.get("Store");
            modeOfPayment = (String) userDocument.get("ModeOfPayment");
        }
    }

    //getters
    public static HashMap<String, HashMap<String, String>> getCart() { return cart; }
    public static int getSubtotal() { return subtotal; }
    public static int getTotalPriceOfOrder() { return totalPriceOfOrder; }
    public static String getModeOfPayment() { return modeOfPayment; }
    public static String getStore() { return store; }

}
