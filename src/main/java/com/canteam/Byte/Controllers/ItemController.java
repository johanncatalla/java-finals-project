package com.canteam.Byte.Controllers;
import com.canteam.Byte.Models.ItemModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class ItemController {

    @FXML
    private ImageView itemImage;

    @FXML
    private Label itemName;

    @FXML
    private Label itemPrice;

    private ItemModel itemModel;

    public void setData(ItemModel itemModel){
        this.itemName.setText(itemModel.getItemName());
        this.itemPrice.setText(itemModel.getItemPrice());
        this.itemImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(itemModel.getItemImageSrc()))));
    }

}

