package com.canteam.Byte.Controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

public class AdController {

    @FXML
    private ImageView AdOne;
    private PageNavigator pageNavigator = new PageNavigator();

    Image imageTwo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/canteam/Byte/assets/images/CuisineType/Ads_pg2.jpg")));
    Image imageThree = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/canteam/Byte/assets/images/CuisineType/Ads_pg3.jpg")));


    private int ad = 0;

    @FXML
    void onClickAdOne(MouseEvent event) {

        ad++;

        if (ad == 1) {
            AdOne.setImage(imageTwo);
        } else if (ad == 2) {
            AdOne.setImage(imageThree);
        }
        else if (ad == 3) {
            pageNavigator.navigateToPage(AdOne, "Home");
        }



    }

}
