package com.canteam.Byte.Models;

public class ShopModel {
    private String shopName, shopImageSrc;
    private static String selectedShopName;

    public static String getSelectedShopName() {
        return selectedShopName;
    }

    public static void setSelectedShopName(String selectedShop) {
        ShopModel.selectedShopName = selectedShop;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopImageSrc() {
        return shopImageSrc;
    }

    public void setShopImageSrc(String shopImageSrc) {
        this.shopImageSrc = shopImageSrc;
    }
}
