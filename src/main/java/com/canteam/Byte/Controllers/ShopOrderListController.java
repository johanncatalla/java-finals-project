package com.canteam.Byte.Controllers;

import com.canteam.Byte.Models.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.canteam.Byte.Controllers.PageNavigator;
import com.canteam.Byte.Models.OrderModel;
import org.bson.Document;


public class ShopOrderListController implements Initializable {

    @FXML
    private Button exitBtn;

    @FXML
    private Button reloadBtn;

    @FXML
    private GridPane gridOrders;

    @FXML
    private Label shopName;

    @FXML
    private AnchorPane scrollAnchor;

    @FXML
    private AnchorPane statusBar;

    private Draggable draggable = new Draggable();

    private PageNavigator pageNavigator = new PageNavigator();

    @FXML
    void onExit(ActionEvent event) {
        UserModel.signOut();
        pageNavigator.navigateToPage(exitBtn, "login");
        PageNavigator.clearHistory();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        draggable.makeScrollableY(scrollAnchor);
        draggable.makeWindowDraggable(statusBar);
        shopName.setText("Byte Shop: "+UserModel.getUserName());
        ArrayList<Document> shopOrderList = OrderModel.getStoreOrders(UserModel.getUserName());

        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < shopOrderList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/canteam/Byte/fxml/ShopOrderQueue.fxml"));
                AnchorPane orderContainer = fxmlLoader.load();

                ShopOrderQueueController shopOrderQueueController = fxmlLoader.getController();

                Document orderData = shopOrderList.get(i);
                String name = orderData.getString("Full Name");
                String address = orderData.getString("Address");
                String contact = orderData.getString("Contact");
                String orderNum = orderData.getInteger("Order Number").toString();

                shopOrderQueueController.setData(name, address, contact, orderNum);

                gridOrders.add(orderContainer, column, row++);

                GridPane.setMargin(orderContainer, new Insets(10));
            }
        } catch (IOException e) {
            throw  new RuntimeException(e);
        }
    }

    @FXML
    void onReload(ActionEvent event) {
        pageNavigator.navigateToPage(reloadBtn, "ShopOrderList");

    }
}
