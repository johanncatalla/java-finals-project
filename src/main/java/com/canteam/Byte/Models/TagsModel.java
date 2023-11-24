package com.canteam.Byte.Models;

import com.canteam.Byte.MongoDB.Connection;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;

public class TagsModel {
    private TagsModel() {}

    private static MongoClient mongoClient = Connection.getInstance();
    private static MongoDatabase database = mongoClient.getDatabase("Byte");
    private static MongoCollection<Document> tagsCollection = database.getCollection("Tags");

    // Returns arraylist of a shop's tags
    public static ArrayList<String> getShopTagArray(String shopName) {
        ArrayList<String> shopTags = new ArrayList<String>();
        Document shops = tagsCollection.find(Filters.eq("Category", "Store Tags")).first();
        shopTags = (ArrayList<String>) shops.get("Mangyupsal");

        return shopTags;
    }

    // Returns arraylist of cuisine tags
    public static ArrayList<String> getCuisineTagsArray() {
        Document tags = tagsCollection.find(Filters.eq("Category", "Cuisine Tags")).first();
        return (ArrayList<String>) tags.get("Tags");
    }

    public static void main(String[] args) {
        ArrayList<String> hehe = TagsModel.getCuisineTagsArray();
        System.out.println(hehe);
    }
}



