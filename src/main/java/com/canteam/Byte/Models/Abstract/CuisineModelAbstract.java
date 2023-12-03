package com.canteam.Byte.Models.Abstract;

public abstract class CuisineModelAbstract {
    protected String cuisineName, cuisineImageSrc;
    public abstract String getCuisineName();
    public abstract void setCuisineName(String cuisineName);
    public abstract  String getCuisineImageSrc();
    public abstract void  setCuisineImageSrc(String cuisineImageSrc);

}
