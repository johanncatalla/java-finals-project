package com.canteam.Byte.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.net.URL;
import java.util.ResourceBundle;

public class UserOrderViewController implements Initializable {

    @FXML
    private Button closeBtn;

    @FXML
    private Label confirmedByLabel;

    @FXML
    private Label deliveryAddressLabel;

    @FXML
    private Label deliveryFeeLabel;

    @FXML
    private Label modePaymentLabel;

    @FXML
    private Label orderNumberLabel;

    @FXML
    private Label orderStoreLabel;

    @FXML
    private GridPane ordersGridPane;

    @FXML
    private AnchorPane scrollAnchorPane;

    @FXML
    private ImageView statusBar;

    @FXML
    private Label subtotalLabel;

    @FXML
    private Label totalLabel;

    private PageNavigator pageNavigator = new PageNavigator();
    private Draggable draggable = new Draggable();

    @FXML
    void onCloseBtnClicked(ActionEvent event) {
        pageNavigator.backToPage(closeBtn);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Make the window draggable
        draggable.makeScrollableY(scrollAnchorPane);

        // Load content to grid pane
        for (int i = 0; i < 10; i++) {
            // Column 0 = Quantity
            // Column 1 = Item Name
            // Column 2 = Price

            // Create labels
            Label quantityLabel = new Label("12");
            Label itemNameLabel = new Label("Item Name Longer Name Hehets Wrap Testing Hehets");
            Label priceLabel = new Label("PHP 1200.00");

            priceLabel.setAlignment(Pos.CENTER_RIGHT);
            itemNameLabel.wrapTextProperty().setValue(true);
            itemNameLabel.maxHeight(50);

            // Add labels to grid pane
            ordersGridPane.add(quantityLabel, 0, i);
            ordersGridPane.add(itemNameLabel, 1, i);
            ordersGridPane.add(priceLabel, 2, i);

            // Set margin for labels
            GridPane.setMargin(quantityLabel, new javafx.geometry.Insets(0, 0, 5, 0));

        }
    }
}
