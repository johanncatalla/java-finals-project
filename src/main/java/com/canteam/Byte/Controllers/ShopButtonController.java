package com.canteam.Byte.Controllers;

import com.canteam.Byte.Models.ShopModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ShopButtonController {
    @FXML
    private ImageView buttonImage;

    @FXML
    private Label shopName;

    @FXML
    private AnchorPane shopButton;

    private ShopModel shopModel;

    PageNavigator pageNavigator = new PageNavigator();

    public void setData(ShopModel shopModel){
        this.shopModel = shopModel;
        shopName.setText(shopModel.getShopName());
        buttonImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(shopModel.getShopImageSrc()))));
    }

    public void onShopButtonClicked() {
        ShopModel.setSelectedShopName(shopModel.getShopName());
        pageNavigator.makeForwardNavigator(shopButton, "Restaurants", "RestaurantMenu");
    }
}

