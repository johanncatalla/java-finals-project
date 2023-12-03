package com.canteam.Byte.Models;

public class CuisineModel {
    private String cuisineName;
    private String cuisineImageSrc;

    public String getCuisineName() {
        return cuisineName;
    }
    public void setCuisineName(String cuisineName) {
        this.cuisineName = cuisineName;
    }

    // polymorphism
    public void setCuisineName() {
        System.out.println("No parameter setCuisineName method called");
    }

    public String getCuisineImageSrc() {
        return cuisineImageSrc;
    }
    public void setCuisineImageSrc(String cuisineImageSrc) {
        this.cuisineImageSrc = cuisineImageSrc;
    }

    // polymorphism
    public void setCuisineImageSrc() {
        System.out.println("No parameter setCuisineImageSrc method called");
    }
}
