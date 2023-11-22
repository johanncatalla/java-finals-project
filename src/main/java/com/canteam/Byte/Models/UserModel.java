package com.canteam.Byte.Models;
import com.canteam.Byte.MongoDB.Connection;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.HashMap;
import org.bson.Document;

public class UserModel {

    // private constructor to prevent instantiation
    private UserModel() {}
    private static MongoClient client = Connection.getInstance();
    private static MongoDatabase db = client.getDatabase("Byte");
    private static MongoCollection<Document> collection = db.getCollection("Users");
    private static String fullName;
    private static String username;
    private static String password;
    private static String address;
    private static String contact;
    private static String userType;

    public static void signOut() {
        UserModel.setFullName(null);
        UserModel.setUserName(null);
        UserModel.setUserPassword(null);
        UserModel.setUserAddress(null);
        UserModel.setUserContact(null);
        UserModel.setUserType(null);
    }

    public static void loginUser(Document user, String username, String password) {
        UserModel.setUserName(username.trim());
        UserModel.setUserPassword(password);
        UserModel.setUserAddress(user.getString("Address"));
        UserModel.setUserContact(user.getString("Contact"));
        UserModel.setUserType(user.getString("UserType"));
        UserModel.setFullName(user.getString("FullName"));
        UserModel.setUserType(user.getString("UserType"));
    }

    public static boolean userExists(String username) {
        Document user = (Document) collection.find(new Document("Username", username)).first();
        if (user != null) {
            return true;
        } else {
            return false;
        }
    }

    public static void createUser(String fullName, String username, String password, String address, String contact, String userType) {
        Document newUser = new Document("FullName", fullName).append("Username", username.trim()).append("Password", password).append("Address", address).append("Contact", contact).append("UserType", userType);
        collection.insertOne(newUser);
    }

    public static void setFullName(String inputFullName) { fullName = inputFullName; }
    public static void setUserName(String inputUserName) { username = inputUserName; }
    public static void setUserPassword(String inputPassword) { password = inputPassword; }
    public static void setUserAddress(String inputAddress) { address = inputAddress; }
    public static void setUserContact(String inputContact) { contact = inputContact; }
    public static void setUserType(String inputUserType) { userType = inputUserType; }
    public static String getFullName() { return fullName; }
    public static String getUserName() { return username; }
    public static String getUserPassword() { return password; }
    public static String getUserAddress() { return address; }
    public static String getUserContact() { return contact; }

    public static void main(String[] args) {
        UserModel.createUser("Hehefull","Johann", "hehe", "addressTest", "contact", "Customer");
    }
}