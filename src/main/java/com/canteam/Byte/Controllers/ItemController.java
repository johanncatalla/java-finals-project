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

public class ItemController implements Initializable {

    @FXML
    private ImageView itemImage;

    @FXML
    private Label itemName;

    @FXML
    private Label itemPrice;

    @FXML
    private AnchorPane itemButton;

    private ItemModel itemModel;

    private PageNavigator pageNavigator = new PageNavigator();

    public void setData(ItemModel itemModel, String path){
        this.itemModel = itemModel;
        this.itemName.setText(itemModel.getItemName());
        this.itemPrice.setText("PHP " + itemModel.getItemPrice() + ".00");
        this.itemImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(path))));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        itemButton.setOnMouseClicked(mouseEvent -> {
            ItemModel.setSelectedItemInfo(itemModel.getItemInfo());
            pageNavigator.makeForwardNavigator(itemButton, "RestaurantMenu", "Meals");
        });
    }
}

