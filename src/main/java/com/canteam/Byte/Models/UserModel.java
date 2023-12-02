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

    public static void signOut() {
        username = null;
        fullName = null;
        email = null;
        password = null;
        userType = null;
        contact = null;
        address = null;
    }

    public static void loginUser(String inputUsername) {
        username = inputUsername;
        Document user = collection.find(Filters.eq("Username", inputUsername)).first();
        fullName = user.getString("FullName");
        password = user.getString("Password");
        address = user.get("Address", ArrayList.class);
        contact = user.getString("Contact");
        userType = user.getString("UserType");
        email = user.getString("Email");

        // fetch user cart from database
        if (Objects.equals(getUserType(), "Customer")) {
            CartModel.defineCart(inputUsername);
        }
    }

    public static boolean userExists(String username) {
        Document user = (Document) collection.find(new Document("Username", username)).first();
        if (user != null) {
            return true;
        } else {
            return false;
        }
    }

    public static void createUser(String fullName, String username, String password, ArrayList<String> address, String contact, String userType, String email){
        // Creates cart and sets new user to true if user type is Customer
        if (Objects.equals(userType, "Customer")) {
            Document newUser = new Document("FullName", String.valueOf(fullName))
                    .append("Username", String.valueOf(username).trim())
                    .append("Password", String.valueOf(username))
                    .append("Address", address)
                    .append("Contact", contact)
                    .append("UserType", userType)
                    .append("Email", email)
                    .append("New User", true);

            collection.insertOne(newUser);
            CartModel.createUserCart(username);

        } else {
            Document newUser = new Document("FullName", String.valueOf(fullName))
                    .append("Username", String.valueOf(username).trim())
                    .append("Password", String.valueOf(username))
                    .append("Address", address)
                    .append("Contact", contact)
                    .append("UserType", userType)
                    .append("Email", email)
                    .append("New User", null);

            collection.insertOne(newUser);
        }
    }

    public static void setNewOldUser(boolean trueFalse) {
        collection.updateOne(Filters.eq("Username", username), Updates.set("New User", trueFalse));
    }

    public static boolean isNewUser() {
        Document user = collection.find(Filters.eq("Username", username)).first();
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
