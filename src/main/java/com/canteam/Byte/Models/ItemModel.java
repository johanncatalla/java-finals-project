package com.canteam.Byte.Models;

import java.util.HashMap;

public class ItemModel {
    private String itemName, itemImageSrc, itemStore, itemSizes;
    private String itemPrice;
    private String itemShopTags, itemCuisineTags;
    private boolean itemAvailable, itemPopular;
    private HashMap<String, String> itemInfo = new HashMap<>();
    public static HashMap<String, String> selectedItemInfo;


    public void setItemInfo(HashMap<String, String> itemInfo) {
        this.itemInfo = itemInfo;
    }

    public HashMap<String, String> getItemInfo() {
        return itemInfo;
    }

    public static void setSelectedItemInfo(HashMap<String, String> itemInfo) {
        selectedItemInfo = itemInfo;
    }

    public static HashMap<String, String> getSelectedItemInfo() {
        return selectedItemInfo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return String.valueOf(itemPrice);
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemImageSrc() {
        return itemImageSrc;
    }

    public void setItemImageSrc(String itemImageSrc) {
        this.itemImageSrc = itemImageSrc;
    }

    public String getItemStore() {
        return itemStore;
    }

    public void setItemStore(String itemStore) {
        this.itemStore = itemStore;
    }

    public String getItemShopTags() {
        return itemShopTags;
    }

    public void setItemShopTags(String itemShopTags) {
        this.itemShopTags = itemShopTags;
    }

    public String getItemCuisineTags() {
        return itemCuisineTags;
    }

    public void setItemCuisineTags(String itemCuisineTags) {
        this.itemCuisineTags = itemCuisineTags;
    }

    public boolean isItemPopular() {
        return itemPopular;
    }

    public void setItemPopular(boolean itemPopular) {
        this.itemPopular = itemPopular;
    }

    public boolean isItemAvailable() {
        return itemAvailable;
    }

    public void setItemAvailable(boolean itemAvailable) {
        this.itemAvailable = itemAvailable;
    }

    // setData() method
    public void setData(HashMap<String, String> item){
        this.setItemInfo(item);
        this.setItemName(item.get("Item_Name"));
        this.setItemPrice(item.get("Item_Price"));
        this.setItemImageSrc(item.get("Item_Image"));
        this.setItemStore(item.get("Item_Store"));
        this.setItemShopTags(item.get("Item_Shop_Tag"));
        this.setItemCuisineTags(item.get("Item_Cuisine_Tag"));
        this.setItemPopular(Boolean.parseBoolean(item.get("Item_Popular")));
        this.setItemAvailable(Boolean.parseBoolean(item.get("Item_Available")));
        System.out.println("This is the item: "+item.get("Item_Sizes")+" Item type:"+item.get("Item_Sizes").getClass());
    }

    public static HashMap<String, Integer> convertDocumentStrToHashMap(String str) {
        str = str.substring(11, str.length()-2); // remove "Document{{" and "}}"
        String[] keyValuePairs = str.split("="); // split the string to create key-value pairs
        HashMap<String, Integer> map = new HashMap<>();

        map.put(keyValuePairs[0].trim(), Integer.parseInt(keyValuePairs[1].trim())); // add them to the hashmap and trim whitespaces

        return map;
    }
}
