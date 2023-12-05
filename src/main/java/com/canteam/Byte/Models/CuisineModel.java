package com.canteam.Byte.Models;

import com.canteam.Byte.Models.Abstract.CuisineModelAbstract;

public class CuisineModel extends CuisineModelAbstract {
    @Override
    public String getCuisineName() {
        return cuisineName;
    }
    @Override
    public void setCuisineName(String cuisineName) {
        this.cuisineName = cuisineName;
    }

    // polymorphism
    public void setCuisineName() {
        System.out.println("No parameter setCuisineName method called");
    }

    @Override
    public String getCuisineImageSrc() {
        return cuisineImageSrc;
    }
    @Override
    public void setCuisineImageSrc(String cuisineImageSrc) {
        this.cuisineImageSrc = cuisineImageSrc;
    }

    // polymorphism
    public void setCuisineImageSrc() {
        System.out.println("No parameter setCuisineImageSrc method called");
    }
}
