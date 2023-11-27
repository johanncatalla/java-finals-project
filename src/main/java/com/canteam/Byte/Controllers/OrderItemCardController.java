package com.canteam.Byte.Controllers;

import com.canteam.Byte.Models.CartModel;
import com.canteam.Byte.Models.UserModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class OrderItemCardController {

    @FXML
    private ImageView itemImage;

    @FXML
    private Label itemName;

    @FXML
    private Label itemPrice;

    @FXML
    private Label itemQty;

    @FXML
    public Button removeBtn;

    public void setData(String itemStore, String itemName, String itemPrice, String itemQty){
        // set data to labels
        this.itemName.setText(itemName);
        this.itemPrice.setText("PHP "+itemPrice+".00");
        this.itemQty.setText("X"+itemQty);

        // get image resource as stream
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/canteam/Byte/assets/images/Store/"+itemStore+"/"+itemName+".jpg")));
        // set image to image view
        itemImage.setImage(image);
        // clip the image to rounded rectangle
        Rectangle clip = new Rectangle(
                itemImage.getFitWidth(), itemImage.getFitHeight()
        );
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        itemImage.setClip(clip);
    }

}
