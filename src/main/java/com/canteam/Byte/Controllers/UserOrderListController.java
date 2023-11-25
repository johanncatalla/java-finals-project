package com.canteam.Byte.Controllers;

import com.canteam.Byte.Models.UserModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.bson.Document;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.canteam.Byte.Models.UserModel;
import com.canteam.Byte.Models.OrderModel;

public class UserOrderListController implements Initializable {

    @FXML
    private Button closeOrdersButton;

    @FXML
    private GridPane gridOrders;
    private PageNavigator pageNavigator = new PageNavigator();

    private ArrayList<Document> userOrderList = new ArrayList<>();

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<Document> userOrderList = OrderModel.getUserOrders(UserModel.getUserName());

        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < userOrderList.size(); i++) {
                // Get the fxml file for the cuisine button
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/canteam/Byte/fxml/UserOrderQueue.fxml"));
                AnchorPane orderContainer = fxmlLoader.load();

                // Set the data for the cuisine button
                UserOrderQueueController userOrderQueueController = fxmlLoader.getController();
                userOrderQueueController.setData("test", "test", "test", "test");


                gridOrders.add(orderContainer, column, row++);

                // Set the margin for the cuisine button
                GridPane.setMargin(orderContainer, new Insets(10));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void onCloseOrdersButtonClick() {
        pageNavigator.backToPage(closeOrdersButton);


    }
}