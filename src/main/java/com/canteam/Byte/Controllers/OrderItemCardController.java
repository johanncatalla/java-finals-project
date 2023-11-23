package com.canteam.Byte.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class OrderItemCardController {

    @FXML
    private AnchorPane itemImage;

    @FXML
    private Label itemName;

    @FXML
    private Label itemPrice;

    @FXML
    private Label itemQty;

    @FXML
    private Button removeBtn;

    public void setData(String itemStore, String itemName, String itemPrice, String itemQty){
        System.out.println("Setting data for " + itemName + ":");
        System.out.println("itemStore: " + itemStore + "\nitemName: " + itemName + "\nitemPrice: " + itemPrice + "\nitemQty: " + itemQty + "\n");
        String imageSrc = "/com/canteam/Byte/images/" + itemStore + "/" + itemName + ".jpg";
        this.itemName.setText(itemName);
        this.itemPrice.setText(itemPrice);
        this.itemQty.setText("X"+itemQty);
        this.itemImage.setStyle("" +
                "-fx-background-image: url("+imageSrc+");" +
                " -fx-background-size: cover;" +
                " -fx-background-position: center;" +
                " -fx-background-repeat: no-repeat;" +
                " -fx-border-width: 2;" +
                " -fx-border-color: #EAEAEA;");
    }

}
