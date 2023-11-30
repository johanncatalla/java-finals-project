package com.canteam.Byte.Controllers;
import com.canteam.Byte.Models.*;
import com.canteam.Byte.Models.UserModel;
import com.canteam.Byte.MongoDB.Connection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class RestaurantMenuController {

    @FXML
    private Button addOnsButton;

    @FXML
    private Button addQtyBtn;

    @FXML
    private Button subtractQtyBtn;
    @FXML
    private Label qtyLabel;

    @FXML
    private Button backButton;

    @FXML
    private Button beveragesButton;

    @FXML
    private ImageView cartIcon;

    @FXML
    private AnchorPane gridPaneContainer, itemViewPane, qtyAddSubPane, noTagsPositionPane ,
            scrollAnchorPane, specialInstructionsPane, extrasLabel, addSuccessAlert;

    @FXML
    private GridPane itemsGridPane, extrasGridPane;

    @FXML
    private Button koreanStreetFoodBtn;

    @FXML
    private Button popularBtn;

    @FXML
    private Label restaurantName, sectionTitle, sectionDescription;

    @FXML
    private Button riceBowlBtn;

    @FXML
    private Button riceToppingsBtn;

    @FXML
    private TextField searchField;

    @FXML
    private Button sizzlingBtn;

    @FXML
    private ImageView statusBar;

    @FXML
    private ImageView mealImg;

    @FXML
    private Label mealName;

    @FXML
    private Label mealPrice;

    @FXML
    private TextField specialInstructionsTxt;

    @FXML
    private HBox tagsHBox;
    private boolean extraChecked;
    List<ItemModel> shopItems = new ArrayList<>();

    private Draggable draggable = new Draggable();

    private PageNavigator pageNavigator = new PageNavigator();
    private HashMap<String, Integer> extrasMap = new HashMap<>();
    @FXML
    protected void onBackButtonClicked(){
        pageNavigator.backToPage(backButton);
    }

    @FXML
    void onAddQty(ActionEvent event) {
        if (Integer.parseInt(qtyLabel.getText()) < 10) {
            qtyLabel.setText(Integer.toString(Integer.parseInt(qtyLabel.getText()) + 1));
        }
    }

    @FXML
    void onSubtractQty(ActionEvent event) {
        if (Integer.parseInt(qtyLabel.getText()) > 1) {
            qtyLabel.setText(Integer.toString(Integer.parseInt(qtyLabel.getText()) - 1));
        }
    }

    private void conditionShopTags(String shopTag){
        // clear the grid pane
        itemsGridPane.getChildren().clear();

        // Add the items to the grid pane
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < shopItems.size(); i++) {
                ItemModel itemModel = shopItems.get(i);

                // Get the fxml file for the cuisine button
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/canteam/Byte/fxml/Item.fxml"));
                AnchorPane item = fxmlLoader.load();

                // add event listener to the item button
                item.setOnMouseClicked(mouseEvent -> {
                    ItemModel.setSelectedItemInfo(itemModel.getItemInfo());
                    showItem();
                });

                // Set the data for the cuisine button
                ItemController itemController = fxmlLoader.getController();
                // Check if itemTags contains "Rice Meal"


                if (itemModel.getItemShopTags().contains(shopTag)) {
                    itemController.setData(itemModel, "/com/canteam/Byte/assets/images/Store/"+itemModel.getItemStore()+"/"+itemModel.getItemName()+".jpg");
                    // Place the cuisine button in the gridPane
                    if (column == 2) {
                        row++;
                        column = 0;
                    }
                    clipItem(item);
                    itemsGridPane.add(item, column++, row);
                }

                // Set the margin for the cuisine button
                GridPane.setMargin(item, new Insets(0, 20, 10, 20));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onEnterPressed(){
        // clear the grid pane
        itemsGridPane.getChildren().clear();

        // Add the items to the grid pane
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < shopItems.size(); i++) {
                ItemModel itemModel = shopItems.get(i);

                // Get the fxml file for the cuisine button
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/canteam/Byte/fxml/Item.fxml"));
                AnchorPane item = fxmlLoader.load();

                // add event listener to the item button
                item.setOnMouseClicked(mouseEvent -> {
                    ItemModel.setSelectedItemInfo(itemModel.getItemInfo());
                    showItem();
                });

                // Set the data for the cuisine button
                ItemController itemController = fxmlLoader.getController();
                // Check if itemTags contains "Rice Meal"


                if (itemModel.getItemName().toLowerCase().contains(searchField.getText())) {
                    itemController.setData(itemModel, "/com/canteam/Byte/assets/images/Store/"+itemModel.getItemStore()+"/"+itemModel.getItemName()+".jpg");
                    // Place the cuisine button in the gridPane
                    if (column == 2) {
                        row++;
                        column = 0;
                    }
                    clipItem(item);
                    itemsGridPane.add(item, column++, row);
                }

                // Set the margin for the cuisine button
                GridPane.setMargin(item, new Insets(0, 20, 10, 20));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showItem(){
        HashMap<String, String> selectedItem = ItemModel.getSelectedItemInfo();
        // Set the meal name and price
        mealName.setText(selectedItem.get("Item_Name"));
        mealPrice.setText("PHP " + selectedItem.get("Item_Price") + ".00");

        // item image
        mealImg.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/canteam/Byte/assets/images/Store/"+selectedItem.get("Item_Store")+"/"+selectedItem.get("Item_Name")+".jpg"))));

        // Set qtyAddSubPane to visible
        qtyAddSubPane.setVisible(true);


        // add checkbox for extra
        int row = 0;
        if (!selectedItem.get("Item_Sizes").isEmpty()){
            noTagsPositionPane.setVisible(false);
            extrasLabel.setVisible(true);
            HashMap<String, Integer> sizesMap = ItemModel.convertDocumentStrToHashMap(selectedItem.get("Item_Sizes"));

            // clear the children of the grid pane
            extrasGridPane.getChildren().clear();

            // Create a checkbox
            for (String key : sizesMap.keySet()){
                CheckBox checkBox = new CheckBox(key);
                Label label = new Label("+ PHP " + sizesMap.get(key) + ".00");

                // Add event listener to the checkbox
                checkBox.setOnAction(actionEvent -> {
                    if (checkBox.isSelected()){
                        extrasMap.put(key, sizesMap.get(key));
                        mealPrice.setText("PHP "+String.valueOf(Integer.parseInt(selectedItem.get("Item_Price"))+sizesMap.get(key))+".00");
                        extraChecked = true;
                        System.out.println(extrasMap);
                    } else {
                        extrasMap.remove(key);
                        mealPrice.setText("PHP "+String.valueOf(Integer.parseInt(selectedItem.get("Item_Price")))+".00");
                        extraChecked = false;
                        System.out.println(extrasMap);
                    }
                });

                // Add the checkbox to the grid pane
                extrasGridPane.add(checkBox, 0, row);
                extrasGridPane.add(label , 1, row++);

                // Move the special instructions pane to the last row of the grid pane
                extrasGridPane.add(specialInstructionsPane, 0, row);

                //span the special instructions pane to the whole row
                GridPane.setColumnSpan(specialInstructionsPane, 2);


                // Style the checkbox and label with 'details' class
                checkBox.getStyleClass().add("details");
                label.getStyleClass().add("details");

                // checkbox left margin = 30
                GridPane.setMargin(checkBox, new Insets(0, 0, 10, 20));
            }
        } else {
            noTagsPositionPane.setVisible(true);
            extrasLabel.setVisible(false);
            // remove the children of the grid pane
            extrasGridPane.getChildren().clear();
            // move the special instructions pane back to noTagsPositionPane
            noTagsPositionPane.getChildren().clear();
            noTagsPositionPane.getChildren().add(specialInstructionsPane);
        }
        // Animation for the item view pane after clicking an item
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(javafx.util.Duration.seconds(0.5));
        transition.setNode(itemViewPane);
        transition.setToY(-750);
        transition.play();
    }

    @FXML
    public void hideItem(){
        // Animation for the item view pane after clicking an item
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(javafx.util.Duration.seconds(0.5));
        transition.setNode(itemViewPane);
        transition.setToY(0);
        transition.play();

        // Set qtyAddSubPane to visible
        qtyAddSubPane.setVisible(false);
        qtyLabel.setText("1");

        // clear the special instructions text field
        specialInstructionsTxt.clear();

        // put the scrollAnchorPane back to the top
        scrollAnchorPane.setLayoutY(0);
    }

    @FXML
    private void onCartIconClicked(){
        pageNavigator.forwardToPage(cartIcon, "RestaurantMenu", "Cart");
    }


    // add exception handling for the alert
    public void alertSuccess() {
        Thread thread = new Thread(() -> {
            try {
                addSuccessAlert.setVisible(true);
                Thread.sleep(500);
                // fade out the alert
                addSuccessAlert.setOpacity(1);

                addSuccessAlert.setVisible(false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    @FXML
    private void onAddtoCartBtnClicked(){

        HashMap<String, String> selectedItem = ItemModel.getSelectedItemInfo();
        String txtPrice = mealPrice.getText();
        int price = Integer.parseInt(txtPrice.substring(4, txtPrice.length()-3));

        System.out.println(selectedItem);
        if (ItemModel.convertDocumentStrToHashMap(selectedItem.get("Item_Sizes")).isEmpty()) {

            CartModel.addToCart(UserModel.getUserName(), selectedItem.get("Item_Name"),
                    price, Integer.parseInt(qtyLabel.getText()),
                    selectedItem.get("Item_Store"), specialInstructionsTxt.getText(), null, selectedItem.get("Item_Image"));
            // show the alert
            alertSuccess();
        } else {
            if (extraChecked) {
                CartModel.addToCart(UserModel.getUserName(), selectedItem.get("Item_Name")+" [22oz]",
                        price, Integer.parseInt(qtyLabel.getText()),
                        selectedItem.get("Item_Store"), specialInstructionsTxt.getText(), extrasMap, selectedItem.get("Item_Image"));
                // show the alert
                alertSuccess();
            } else {
                CartModel.addToCart(UserModel.getUserName(), selectedItem.get("Item_Name")+" [Regular]",
                        price, Integer.parseInt(qtyLabel.getText()),
                        selectedItem.get("Item_Store"), specialInstructionsTxt.getText(), extrasMap, selectedItem.get("Item_Image"));
                // show the alert
                alertSuccess();
            }

        }
        // reset the qty label to 1
        qtyLabel.setText("1");
        specialInstructionsTxt.setText("");


    }

    //Clip item to round corners
    private void clipItem(AnchorPane item) {
        Rectangle clip = new Rectangle(
                item.getPrefWidth(), item.getPrefHeight()
        );
        clip.setArcWidth(20);
        clip.setArcHeight(20);

        item.setClip(clip);
    }


    private List<ItemModel> getData() {
        MongoClient client = Connection.getInstance();
        MongoDatabase database = client.getDatabase("Stores");
        MongoCollection<Document> collection = database.getCollection(ShopModel.getSelectedShopName());

        FindIterable<Document> documents = collection.find();

        ArrayList<HashMap<String, String>> finalArray = new ArrayList<>();

        // Iterate through the documents
        for (Document document : documents) {
            HashMap<String, String> dbMap = new HashMap<>();
            for (String key : document.keySet()) {
                Object value = document.get(key);
                // value could be null
                if (value == null) {
                    value = "";
                }
                dbMap.put(key, value.toString());
            }
            finalArray.add(dbMap);
        }

        // Create a list of ItemModel objects
        List<ItemModel> itemList = new ArrayList<>();
        ItemModel item;

        // Iterate through the finalArray
        for (int i = 0; i < finalArray.size(); i++){
            item = new ItemModel();
            item.setData(finalArray.get(i));
            System.out.println(item.getItemName());
            itemList.add(item);
        }
        return itemList;
    }


    @FXML
    void initialize() {
        // if CartController.addItemClicked is true, disable the add to cart button
        if (CartController.addItemClicked){
            cartIcon.setVisible(false);
        } else {
            cartIcon.setVisible(true);
        }

        // clear the grid pane
        itemsGridPane.getChildren().clear();

        // Get the data from the database
        shopItems.addAll(getData());

        // Set shop tags
        ArrayList<String> shopTags = TagsModel.getShopTagArray(ShopModel.getSelectedShopName());
        System.out.println(ShopModel.getSelectedShopName());
        for (String tag : shopTags){
            Hyperlink tagLink = new Hyperlink(tag);
            tagLink.setOnAction(actionEvent -> {
                sectionTitle.setText(tag);
                sectionDescription.setText("These are the " + tag + " items in " + ShopModel.getSelectedShopName());

                conditionShopTags(tag);
            });
            // Set the style of the tag remove the underline
            tagLink.setStyle(
                    "-fx-text-fill: black;" +
                            "-fx-font-size: 14px;" +
                            "-fx-underline: false;" +
                            "-fx-font-family: Poppins"
            );
            tagsHBox.getChildren().add(tagLink);
        }

        // Add the items to the grid pane
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < shopItems.size(); i++) {
                ItemModel itemModel = shopItems.get(i);

                // Get the fxml file for the cuisine button
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/canteam/Byte/fxml/Item.fxml"));
                AnchorPane item = fxmlLoader.load();

                // add event listener to the item button
                item.setOnMouseClicked(mouseEvent -> {
                    ItemModel.setSelectedItemInfo(itemModel.getItemInfo());
                    showItem();
                });

                // Set the data for the cuisine button
                ItemController itemController = fxmlLoader.getController();

                itemController.setData(shopItems.get(i), "/com/canteam/Byte/assets/images/Store/"+itemModel.getItemStore()+"/"+itemModel.getItemName()+".jpg");
                if (column == 2) {
                    row++;
                    column = 0;
                }

                // Clip the item to round corners
                clipItem(item);

                itemsGridPane.add(item, column++, row);

                // Set the margin for the cuisine button
                GridPane.setMargin(item, new Insets(0, 20, 10, 20));
            }
            draggable.makeScrollableY(gridPaneContainer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        restaurantName.setText(ShopModel.getSelectedShopName());
        draggable.makeWindowDraggable(statusBar);
        draggable.makeScrollableY(scrollAnchorPane);
        draggable.makeScrollableX(tagsHBox);


        // Make the search field on enter pressed
        searchField.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()){
                case ENTER:
                    onEnterPressed();
                    break;
            }
        });

        //Clip the scrollAnchorPane to ItemViewPane
        Rectangle clip = new Rectangle(
                itemViewPane.getPrefWidth(), itemViewPane.getPrefHeight()
        );
        clip.setArcWidth(20);
        clip.setArcHeight(20);

        itemViewPane.setClip(clip);
    }

}
