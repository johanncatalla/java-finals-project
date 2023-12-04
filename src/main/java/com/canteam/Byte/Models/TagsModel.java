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
    /**
     * Retrieves the list of shop tags associated with a specific shop name.
     *
     * @param shopName The name of the shop to retrieve tags for.
     * @return An ArrayList containing shop tags associated with the provided shop name.
     */
    public static ArrayList<String> getShopTagArray(String shopName) {
        ArrayList<String> shopTags = new ArrayList<String>();

        // Find the document containing store tags information
        Document shops = tagsCollection.find(Filters.eq("Category", "Store Tags")).first();

        // Retrieve shop tags based on the provided shopName
        if (shops != null) {
            shopTags = (ArrayList<String>) shops.get(shopName);
        }

        return shopTags;
    }

    /**
     * Retrieves the list of cuisine tags from the database.
     *
     * @return An ArrayList containing cuisine tags.
     */
    public static ArrayList<String> getCuisineTagsArray() {
        // Find the document containing cuisine tags information
        Document tags = tagsCollection.find(Filters.eq("Category", "Cuisine Tags")).first();

        // Retrieve and return the list of cuisine tags
        return (ArrayList<String>) tags.get("Tags");
    }

}



