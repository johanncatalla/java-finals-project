package com.canteam.Byte.Models;

import com.canteam.Byte.Models.Abstract.ItemModelAbstract;

import java.util.HashMap;

public class ItemModel extends ItemModelAbstract {
    public static HashMap<String, String> selectedItemInfo;

    @Override
    public void setItemInfo(HashMap<String, String> itemInfo) {
        this.itemInfo = itemInfo;
    }

    @Override
    public HashMap<String, String> getItemInfo() {
        return itemInfo;
    }

    public static void setSelectedItemInfo(HashMap<String, String> itemInfo) {
        selectedItemInfo = itemInfo;
    }

    public static HashMap<String, String> getSelectedItemInfo() {
        return selectedItemInfo;
    }

    @Override
    public String getItemName() {
        return itemName;
    }

    @Override
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public String getItemPrice() {
        return String.valueOf(itemPrice);
    }

    @Override
    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    @Override
    public String getItemImageSrc() {
        return itemImageSrc;
    }

    @Override
    public void setItemImageSrc(String itemImageSrc) {
        this.itemImageSrc = itemImageSrc;
    }

    @Override
    public String getItemStore() {
        return itemStore;
    }

    @Override
    public void setItemStore(String itemStore) {
        this.itemStore = itemStore;
    }

    @Override
    public String getItemShopTags() {
        return itemShopTags;
    }

    @Override
    public void setItemShopTags(String itemShopTags) {
        this.itemShopTags = itemShopTags;
    }

    @Override
    public String getItemCuisineTags() {
        return itemCuisineTags;
    }

    @Override
    public void setItemCuisineTags(String itemCuisineTags) {
        this.itemCuisineTags = itemCuisineTags;
    }

    @Override
    public boolean isItemPopular() {
        return itemPopular;
    }

    @Override
    public void setItemPopular(boolean itemPopular) {
        this.itemPopular = itemPopular;
    }

    @Override
    public boolean isItemAvailable() {
        return itemAvailable;
    }

    @Override
    public void setItemAvailable(boolean itemAvailable) {
        this.itemAvailable = itemAvailable;
    }

    /**
     * Sets data for the item using a HashMap.
     *
     * @param item A HashMap containing item information.
     */
    @Override
    public void setData(HashMap<String, String> item) {
        // Set the overall item information
        this.setItemInfo(item);

        // Set specific item attributes using the provided keys from the HashMap
        this.setItemName(item.get("Item_Name"));
        this.setItemPrice(item.get("Item_Price"));
        this.setItemImageSrc(item.get("Item_Image"));
        this.setItemStore(item.get("Item_Store"));
        this.setItemShopTags(item.get("Item_Shop_Tag"));
        this.setItemCuisineTags(item.get("Item_Cuisine_Tag"));

        // Parse and set boolean values for item popularity and availability
        this.setItemPopular(Boolean.parseBoolean(item.get("Item_Popular")));
        this.setItemAvailable(Boolean.parseBoolean(item.get("Item_Available")));
    }


    /**
     * Converts a string formatted as "Document{{key=value}}" to a HashMap with String keys and Integer values.
     *
     * @param str The string to be converted into a HashMap.
     * @return A HashMap with key-value pairs extracted from the provided string.
     */
    public static HashMap<String, Integer> convertDocumentStrToHashMap(String str) {
        HashMap<String, Integer> map = new HashMap<>();

        if (!str.isEmpty()) {
            // Remove "Document{{" and "}}" from the string
            str = str.substring(10, str.length() - 2);

            // Split the string to create key-value pairs
            String[] keyValuePairs = str.split("=");

            // Add key-value pairs to the hashmap after trimming whitespaces
            map.put(keyValuePairs[0].trim(), Integer.parseInt(keyValuePairs[1].trim()));
        }

        return map;
    }

}
