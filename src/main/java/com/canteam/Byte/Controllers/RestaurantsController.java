package com.canteam.Byte.Controllers;

import com.canteam.Byte.Models.ShopModel;
import com.canteam.Byte.MongoDB.Connection;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RestaurantsController implements Initializable {
    @FXML
    private ImageView statusBar;

    @FXML
    private GridPane shopsGridPane;

    @FXML
    private AnchorPane gridPaneContainer;

    @FXML
    private Button backButton;

    @FXML
    private TextField searchField;

    @FXML
    private ImageView cartIcon;

    private Draggable draggable = new Draggable();

    private List<ShopModel> shopList = new ArrayList<>();

    PageNavigator pageNavigator = new PageNavigator();

    private static String initialSearch = null;

    private List<ShopModel> getData(){
        List<ShopModel> shopList = new ArrayList<>();
        ShopModel shop;

        MongoClient client = Connection.getInstance();
        MongoDatabase database = client.getDatabase("Stores");

        // Get Collection Names For Testing
        List<String> collectionNames = new ArrayList<>();
        for (String name : database.listCollectionNames()) {
            collectionNames.add(name);
        }

        // Just a demo/placeholder data for now
        for (int i = 0; i < collectionNames.size(); i++){
            shop = new ShopModel();
            shop.setShopName(collectionNames.get(i));
            shop.setShopImageSrc("/com/canteam/Byte/assets/images/ShopImgPlaceholder.png");
            shopList.add(shop);
        }
        return shopList;
    }

    @FXML
    protected void onBackButtonClicked(){
        initialSearch = null;
        pageNavigator.backToPage(backButton);
    }

    void onSearchEntered(String text){
        // Clear the gridpane
        shopsGridPane.getChildren().clear();

        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < shopList.size(); i++) {
                // Get the fxml file for the cuisine button
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/canteam/Byte/fxml/ShopButton.fxml"));
                AnchorPane cuisineButton = fxmlLoader.load();

                // Set the data for the cuisine button
                ShopButtonController shopButtonController = fxmlLoader.getController();

                if (shopList.get(i).getShopName().toLowerCase().contains(text.toLowerCase())){
                    shopButtonController.setData(shopList.get(i));
                    // Place the cuisine button in the gridPane
                    if (column == 2) {
                        row++;
                        column = 0;
                    }
                    shopsGridPane.add(cuisineButton, column++, row);

                    // Set the margin for the cuisine button
                    GridPane.setMargin(cuisineButton, new Insets(0, 20, 15, 20));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setInitialSearch(String search) {
        initialSearch = search;
    }

    @FXML
    private void onCartIconClicked(){
        pageNavigator.forwardToPage(cartIcon, "Restaurants", "Cart");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // if CartController.addItemClicked is true, disable the add to cart button
        if (CartController.addItemClicked){
            cartIcon.setVisible(false);
        } else {
            cartIcon.setVisible(true);
        }

        // Set up window dragging
        draggable.makeWindowDraggable(statusBar);

        // Set up enter on search field
        searchField.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode().toString().equals("ENTER")){
                onSearchEntered(searchField.getText());
            }
        });

        // Set up data for the shops
        // Will create an error at start but will be ok when you open the home page
        shopList.addAll(getData());

        if (initialSearch != null){
            onSearchEntered(initialSearch);
        } else {
            int column = 0;
            int row = 1;
            try {
                for (int i = 0; i < shopList.size(); i++) {
                    // Get the fxml file for the cuisine button
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/com/canteam/Byte/fxml/ShopButton.fxml"));
                    AnchorPane cuisineButton = fxmlLoader.load();

                    // Set the data for the cuisine button
                    ShopButtonController shopButtonController = fxmlLoader.getController();
                    shopButtonController.setData(shopList.get(i));

                    // Place the cuisine button in the gridPane
                    if (column == 2) {
                        row++;
                        column = 0;
                    }
                    shopsGridPane.add(cuisineButton, column++, row);

                    // Set the margin for the cuisine button
                    GridPane.setMargin(cuisineButton, new Insets(0, 20, 15, 20));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Make gridpane scrollable
        draggable.makeScrollableY(gridPaneContainer);
    }
}
