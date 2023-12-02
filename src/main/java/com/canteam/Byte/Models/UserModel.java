package com.canteam.Byte.Models.AbstractFolder;
import com.canteam.Byte.MongoDB.Connection;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.Objects;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import com.canteam.Byte.Models.CartModel;

public abstract class UserModel {

    // private constructor to prevent instantiation
    private UserModel() {}
    private static MongoClient client = Connection.getInstance();
    private static MongoDatabase db = client.getDatabase("Byte");
    private static MongoCollection<Document> collection = db.getCollection("Users");
    private static String username;

    public static void signOut() { username = null; }

    public static void loginUser(String inputUsername) {
        username = inputUsername;

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
    }
    public static void setUserName(String inputUserName) {
        collection.updateOne(Filters.eq("Username", username), Updates.set("Username", inputUserName));
    }
    public static void setUserPassword(String inputPassword) {
        collection.updateOne(Filters.eq("Username", username), Updates.set("Password", inputPassword));
    }
    public static void setUserAddress(ArrayList<String> inputAddress) {
        collection.updateOne(Filters.eq("Username", username), Updates.set("Address", inputAddress));
    }
    public static void setEmail(String inputEmail) {
        collection.updateOne(Filters.eq("Username", username), Updates.set("Email", inputEmail));
    }
    public static void setUserContact(String inputContact) {
        collection.updateOne(Filters.eq("Username", username), Updates.set("Contact", inputContact));
    }
    public static void setUserType(String inputUserType) {
        collection.updateOne(Filters.eq("Username", username), Updates.set("UserType", inputUserType));
    }

    public static String getFullName() {
        Document user = collection.find(Filters.eq("Username", username)).first();
        return user.getString("FullName");
    }
    public static String getUserName() { return username; }
    public static String getUserPassword() {
        Document user = collection.find(Filters.eq("Username", username)).first();
        return user.getString("Password");
    }
    public static ArrayList<String> getUserAddress() {
        Document user = collection.find(Filters.eq("Username", username)).first();
        return user.get("Address", ArrayList.class);
    }
    public static String getLandmark() {
        return getUserAddress().get(0);
    }
    public static String getAddressDetails() {
        return getUserAddress().get(1);
    }
    public static String getUserContact() {
        Document user = collection.find(Filters.eq("Username", username)).first();
        return user.getString("Contact");
    }
    public static String getUserType() {
        Document user = collection.find(Filters.eq("Username", username)).first();
        return user.getString("UserType");
    }
    public static String getEmail() {
        Document user = collection.find(Filters.eq("Username", username)).first();
        return user.getString("Email");
    }

    public static void main(String[] args) {
        UserModel.createUser("Hehefull","Johann", "hehe", null, "contact", "Customer", "email");
    }
}
