package com.canteam.Byte.Models.Abstract;

import java.util.HashMap;

public abstract class ItemModelAbstract {
    protected String itemName, itemImageSrc, itemStore;
    protected String itemPrice;
    protected String itemShopTags, itemCuisineTags;
    protected boolean itemAvailable, itemPopular;
    protected HashMap<String, String> itemInfo = new HashMap<>();
    public ItemModelAbstract() {}

    public abstract void setItemInfo(HashMap<String, String> itemInfo);
    public abstract HashMap<String, String> getItemInfo();
    public abstract String getItemName();
    public abstract void setItemName(String itemName);
    public abstract String getItemPrice();
    public abstract void setItemPrice(String itemPrice);
    public abstract String getItemImageSrc();
    public abstract void setItemImageSrc(String itemImageSrc);
    public abstract String getItemStore();
    public abstract void setItemStore(String itemStore);
    public abstract String getItemShopTags();
    public abstract void setItemShopTags(String itemShopTags);
    public abstract String getItemCuisineTags();
    public abstract void setItemCuisineTags(String itemCuisineTags);
    public abstract boolean isItemPopular();
    public abstract void setItemPopular(boolean itemPopular);
    public abstract boolean isItemAvailable();
    public abstract void setItemAvailable(boolean itemAvailable);
    public void setData(HashMap<String, String> item) {}
}
