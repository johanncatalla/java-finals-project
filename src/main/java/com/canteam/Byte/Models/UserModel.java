package com.canteam.Byte.Models;
import com.canteam.Byte.MongoDB.Connection;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.Objects;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

public abstract class UserModel {

    // private constructor to prevent instantiation
    private UserModel() {}
    private static MongoClient client = Connection.getInstance();
    private static MongoDatabase db = client.getDatabase("Byte");
    private static MongoCollection<Document> collection = db.getCollection("Users");
    private static String username, fullName, email, password, userType, contact;
    private static ArrayList<String> address;

    /**
     * Clears the user's information from the local variables upon signing out.
     * Resets username, full name, email, password, user type, contact, and address to null.
     */
    public static void signOut() {
        username = null;
        fullName = null;
        email = null;
        password = null;
        userType = null;
        contact = null;
        address = null;
    }

    /**
     * Logs in a user with the provided username.
     * Retrieves user information from the database based on the given username
     * and sets the corresponding fields (fullName, password, address, contact, userType, email).
     * If the user is a Customer, it also fetches their cart from the database.
     *
     * @param inputUsername The username of the user to log in.
     */
    public static void loginUser(String inputUsername) {
        // Set the username to the provided inputUsername
        username = inputUsername;

        // Retrieve user document from the database based on the username
        Document user = collection.find(Filters.eq("Username", inputUsername)).first();

        // Set various user information based on the retrieved document fields
        fullName = user.getString("FullName");
        password = user.getString("Password");
        address = user.get("Address", ArrayList.class);
        contact = user.getString("Contact");
        userType = user.getString("UserType");
        email = user.getString("Email");

        // Fetch user cart from the database if the user is of type Customer
        if (Objects.equals(getUserType(), "Customer")) {
            CartModel.defineCart(inputUsername);
        }
    }

    /**
     * Checks if a user with the given username exists in the database.
     *
     * @param username The username to check for existence.
     * @return true if the user exists, false otherwise.
     */
    public static boolean userExists(String username) {
        // Find a user document in the collection based on the provided username
        Document user = (Document) collection.find(new Document("Username", username)).first();

        // Check if the user document exists
        if (user != null) {
            return true; // User exists
        } else {
            return false; // User does not exist
        }
    }

    /**
     * Creates a new user in the database with the provided information.
     * If the user type is "Customer", it creates a cart for the user.
     *
     * @param fullName  The full name of the user.
     * @param username  The username of the user.
     * @param password  The password of the user.
     * @param address   The address of the user (as an ArrayList of Strings).
     * @param contact   The contact information of the user.
     * @param userType  The type of user ("Customer", "Admin", etc.).
     * @param email     The email address of the user.
     */
    public static void createUser(String fullName, String username, String password,
                                  ArrayList<String> address, String contact,
                                  String userType, String email) {
        // Check if the user type is "Customer"
        if (Objects.equals(userType, "Customer")) {
            // Create a new user document with additional "New User" flag set to true
            Document newUser = new Document("FullName", String.valueOf(fullName))
                    .append("Username", String.valueOf(username).trim())
                    .append("Password", String.valueOf(password))
                    .append("Address", address)
                    .append("Contact", contact)
                    .append("UserType", userType)
                    .append("Email", email)
                    .append("New User", true);

            // Insert the new user document into the collection
            collection.insertOne(newUser);

            // Create a cart for the new user
            CartModel.createUserCart(username);
        } else {
            // Create a new user document without setting the "New User" flag
            Document newUser = new Document("FullName", String.valueOf(fullName))
                    .append("Username", String.valueOf(username).trim())
                    .append("Password", String.valueOf(password))
                    .append("Address", address)
                    .append("Contact", contact)
                    .append("UserType", userType)
                    .append("Email", email);

            // Insert the new user document into the collection
            collection.insertOne(newUser);
        }
    }

    /**
     * Sets the "New User" flag in the database for the current user.
     *
     * @param trueFalse Boolean indicating if the user is new (true) or old (false).
     */
    public static void setNewOldUser(boolean trueFalse) {
        // Update the "New User" flag in the collection based on the current username
        collection.updateOne(Filters.eq("Username", username), Updates.set("New User", trueFalse));
    }

    /**
     * Checks if the current user is a new user or an existing one based on the "New User" flag.
     *
     * @return true if the user is marked as new, false otherwise.
     */
    public static boolean isNewUser() {
        // Find the user document in the collection based on the current username
        Document user = collection.find(Filters.eq("Username", username)).first();

        // Get the value of the "New User" flag from the user document and return it
        return user.getBoolean("New User");
    }

    public static void setFullName(String inputFullName) {
        collection.updateOne(Filters.eq("Username", username), Updates.set("FullName", inputFullName));
        fullName = inputFullName;
    }
    public static void setUserName(String inputUserName) {
        collection.updateOne(Filters.eq("Username", username), Updates.set("Username", inputUserName));
        username = inputUserName;
    }
    public static void setUserPassword(String inputPassword) {
        collection.updateOne(Filters.eq("Username", username), Updates.set("Password", inputPassword));
        password = inputPassword;
    }
    public static void setUserAddress(ArrayList<String> inputAddress) {
        collection.updateOne(Filters.eq("Username", username), Updates.set("Address", inputAddress));
        address = inputAddress;
    }
    public static void setEmail(String inputEmail) {
        collection.updateOne(Filters.eq("Username", username), Updates.set("Email", inputEmail));
        email = inputEmail;
    }
    public static void setUserContact(String inputContact) {
        collection.updateOne(Filters.eq("Username", username), Updates.set("Contact", inputContact));
        contact = inputContact;
    }
    public static void setUserType(String inputUserType) {
        collection.updateOne(Filters.eq("Username", username), Updates.set("UserType", inputUserType));
        userType = inputUserType;
    }

    public static String getFullName() {
        return fullName;
    }
    public static String getUserName() { return username; }
    public static String getUserPassword() {
        return password;
    }
    public static ArrayList<String> getUserAddress() {
        return address;
    }
    public static String getLandmark() {
        return getUserAddress().get(0);
    }
    public static String getAddressDetails() {
        return getUserAddress().get(1);
    }
    public static String getUserContact() {
        return contact;
    }
    public static String getUserType() {
        return userType;
    }
    public static String getEmail() {
        return email;
    }
}
