package com.canteam.Byte.Models;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ItemModel {
    private String itemName, itemImageSrc, itemStore;
    private String itemPrice;
    private List<String> itemShopTags, itemCuisineTags;
    private boolean itemAvailable, itemPopular;

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

    public List<String> getItemShopTags() {
        return itemShopTags;
    }

    public void setItemShopTags(List<String> itemShopTags) {
        this.itemShopTags = itemShopTags;
    }

    public List<String> getItemCuisineTags() {
        return itemCuisineTags;
    }

    public void setItemCuisineTags(List<String> itemCuisineTags) {
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
    public void setData(HashMap<String, String> item) {
        this.setItemName(item.get("Item_Name"));
        this.setItemPrice(item.get("Item_Price"));
        this.setItemImageSrc(item.get("Item_Image"));
        this.setItemStore(item.get("Item_Store"));
        this.setItemShopTags(Collections.singletonList(item.get("Item_Shop_Tag")));
        this.setItemCuisineTags(Collections.singletonList(item.get("Item_Cuisine_Tag")));
        this.setItemPopular(Boolean.parseBoolean(item.get("Item_Popular")));
        this.setItemAvailable(Boolean.parseBoolean(item.get("Item_Available")));
    }
}
