package com.canteam.Byte.Controllers;

import com.canteam.Byte.Models.CuisineModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

import com.canteam.Byte.MongoDB.DatabaseConnection;

public class CuisinesButtonController {

    @FXML
    private ImageView buttonImage;

    @FXML
    private Label cuisineName;

    private CuisineModel cuisineModel;

    public void setData(CuisineModel cuisineModel){
        this.cuisineModel = cuisineModel;
        cuisineName.setText(cuisineModel.getCuisineName());
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(cuisineModel.getCuisineImageSrc())));
        buttonImage.setImage(image);
    }

}
