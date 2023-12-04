package com.canteam.Byte.Models.Abstract;

public abstract class ShopModelAbstract {
    protected String shopName, shopImageSrc;

    public abstract String getShopName();
    public abstract void setShopName(String shopName);
    public abstract String getShopImageSrc();
    public abstract void setShopImageSrc(String shopImageSrc);

}
