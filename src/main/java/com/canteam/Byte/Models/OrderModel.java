package com.canteam.Byte.Models;

import java.util.ArrayList;

public class OrderModel {
    private static String restaurant;
    private static String itemName;
    private static double itemPrice;
    private static String itemQuantity;
    private static ArrayList<String> itemTags;
    private static String itemSize = null;
    private static String itemImageDir;

    // Setters
    public static void setRestaurant(String restaurant) { restaurant = restaurant; }
    public static void setItemName(String itemName) { itemName = itemName; }
    public static void setItemPrice(String itemPrice) { itemPrice = itemPrice; }
    public static void setItemQuantity(String itemQuantity) { itemQuantity = itemQuantity; }
    public static void setItemTags(ArrayList<String> itemTags) { itemTags = itemTags; }
    public static void setItemSize(String itemSize) { itemSize = itemSize; }
    public static void setItemImage(String itemImageDir) { itemImageDir = itemImageDir; }


    // Getters
    public static String getRestaurant() { return restaurant; }
    public static String getItemName() { return itemName; }
    public static Double getItemPrice() { return itemPrice; }
    public static String getItemQuantity() { return itemQuantity; }
    public static ArrayList<String> getItemTags() { return itemTags; }
    public static String getItemSize() { return itemSize; }
    public static String getItemImageDir() { return itemSize; }

}
