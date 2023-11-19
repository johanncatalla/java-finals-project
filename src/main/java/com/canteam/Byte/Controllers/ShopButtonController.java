package com.canteam.Byte.Controllers;

import com.canteam.Byte.Models.ShopModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class ShopButtonController {
    @FXML
    private ImageView buttonImage;

    @FXML
    private Label shopName;

    private ShopModel shopModel;

    public void setData(ShopModel shopModel){
        this.shopModel = shopModel;
        shopName.setText(shopModel.getShopName());
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(shopModel.getShopImageSrc())));
        buttonImage.setImage(image);
    }

}

