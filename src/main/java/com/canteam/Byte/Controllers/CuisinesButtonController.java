package com.canteam.Byte.Controllers;

import com.canteam.Byte.Models.CuisineModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

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
        Rectangle clip = new Rectangle(buttonImage.getFitWidth(), buttonImage.getFitHeight());
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        buttonImage.setClip(clip);
    }

}
