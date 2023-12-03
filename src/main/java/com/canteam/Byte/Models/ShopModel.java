package com.canteam.Byte.Models;

import com.canteam.Byte.Models.Abstract.ShopModelAbstract;

public class ShopModel extends ShopModelAbstract {
    private static String selectedShopName;

    public static String getSelectedShopName() {
        return selectedShopName;
    }

    public static void setSelectedShopName(String selectedShop) {
        ShopModel.selectedShopName = selectedShop;
    }

    @Override
    public String getShopName() {
        return shopName;
    }
    @Override
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public void setShopName() { System.out.println("No parameter setShopName method called."); }

    @Override
    public String getShopImageSrc() {
        return shopImageSrc;
    }
    @Override
    public void setShopImageSrc(String shopImageSrc) {
        this.shopImageSrc = shopImageSrc;
    }
    public void setShopImageSrc() { System.out.println("No parameter setShopImageSrc method called."); }
}
