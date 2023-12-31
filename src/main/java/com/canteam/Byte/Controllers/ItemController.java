package com.canteam.Byte.Controllers;
import com.canteam.Byte.Models.ItemModel;
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

public class ItemController {

    @FXML
    private ImageView itemImage;

    @FXML
    private Label itemName;

    @FXML
    private Label itemPrice;
    private ItemModel itemModel;


    // Set the data for the item
    public void setData(ItemModel itemModel, String path){
        this.itemModel = itemModel;
        this.itemName.setText(itemModel.getItemName());
        this.itemPrice.setText("PHP " + itemModel.getItemPrice() + ".00");
        this.itemImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(path))));
    }
}

