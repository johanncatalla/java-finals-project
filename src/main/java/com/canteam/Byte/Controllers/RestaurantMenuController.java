package com.canteam.Byte.Controllers;
import com.canteam.Byte.Models.ShopModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class RestaurantMenuController {

    @FXML
    private Button addOnsButton;

    @FXML
    private Button backButton;

    @FXML
    private Button beveragesButton;

    @FXML
    private ImageView cartIcon;

    @FXML
    private AnchorPane gridPaneContainer;

    @FXML
    private GridPane itemsGridPane;

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
    private GridPane sectionBar;



    private Draggable draggable = new Draggable();

    private PageNavigator pageNavigator = new PageNavigator();

    @FXML
    protected void onBackButtonClicked(){
        pageNavigator.backToPage(backButton);
    }

    // onPopularButtonClicked() is a method that is called when the popularBtn is clicked
    @FXML
    protected void onPopularButtonClicked(){
        sectionTitle.setText("Popular");
        sectionDescription.setText("These are the most popular items in " + ShopModel.getSelectedShopName());
    }

    // onKoreanStreetFoodButtonClicked() is a method that is called when the koreanStreetFoodBtn is clicked
    @FXML
    protected void onKoreanStreetFoodButtonClicked(){
        sectionTitle.setText("Korean Street Food");
        sectionDescription.setText("These are the Korean Street Food items in " + ShopModel.getSelectedShopName());
    }

    // onRiceBowlButtonClicked() is a method that is called when the riceBowlBtn is clicked
    @FXML
    protected void onRiceBowlButtonClicked(){
        sectionTitle.setText("Rice Bowl");
        sectionDescription.setText("These are the Rice Bowl items in " + ShopModel.getSelectedShopName());
    }

    // onRiceToppingsButtonClicked() is a method that is called when the riceToppingsBtn is clicked
    @FXML
    protected void onRiceToppingsButtonClicked(){
        sectionTitle.setText("Rice Toppings");
        sectionDescription.setText("These are the Rice Toppings items in " + ShopModel.getSelectedShopName());
    }

    // onSizzlingButtonClicked() is a method that is called when the sizzlingBtn is clicked
    @FXML
    protected void onSizzlingButtonClicked(){
        sectionTitle.setText("Sizzling");
        sectionDescription.setText("These are the Sizzling items in " + ShopModel.getSelectedShopName());
    }

    // onBeveragesButtonClicked() is a method that is called when the beveragesButton is clicked
    @FXML
    protected void onBeveragesButtonClicked(){
        sectionTitle.setText("Beverages");
        sectionDescription.setText("These are the Beverages items in " + ShopModel.getSelectedShopName());
    }

    // onAddOnsButtonClicked() is a method that is called when the addOnsButton is clicked
    @FXML
    protected void onAddOnsButtonClicked(){
        sectionTitle.setText("Add-Ons");
        sectionDescription.setText("These are the Add-Ons items in " + ShopModel.getSelectedShopName());
    }

    @FXML
    void initialize() {
        restaurantName.setText(ShopModel.getSelectedShopName());
        draggable.makeWindowDraggable(statusBar);

        // Make draggable all the buttons in the section bar
        draggable.makeParentDraggableX(popularBtn);
        draggable.makeParentDraggableX(koreanStreetFoodBtn);
        draggable.makeParentDraggableX(riceBowlBtn);
        draggable.makeParentDraggableX(riceToppingsBtn);
        draggable.makeParentDraggableX(sizzlingBtn);
        draggable.makeParentDraggableX(beveragesButton);
        draggable.makeParentDraggableX(addOnsButton);
    }

}
