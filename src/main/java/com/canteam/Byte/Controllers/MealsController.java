package com.canteam.Byte.Controllers;
import com.canteam.Byte.Models.CartModel;
import com.canteam.Byte.Models.ItemModel;
import com.canteam.Byte.Models.UserModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;

public class MealsController implements Initializable{

    @FXML
    private Button addQtyBtn;

    @FXML
    private Button addToCartBtn;

    @FXML
    private Button btnClose;

    @FXML
    private CheckBox chkExtraRice;

    @FXML
    private GridPane extrasGridPane;

    @FXML
    private ImageView mealImg;

    @FXML
    private Label mealName;

    @FXML
    private Label mealPrice;

    @FXML
    private Label qtyLabel;

    @FXML
    private Label extraLabel1, extraLabel2;

    @FXML
    private TextField specialInstructionsTxt;

    @FXML
    private Button subtractQtyBtn;

    @FXML
    private ImageView statusBar;

    @FXML
    private AnchorPane scrollAnchorPane, specialInstructionsPane;

    private Draggable draggable = new Draggable();
    private PageNavigator pageNavigator = new PageNavigator();

    private HashMap<String, String> itemInfo = new HashMap<>();

    @FXML
    private void onCloseBtnClicked(){
        pageNavigator.backToPage(btnClose);
    }

    @FXML
    private void onAddtoCartBtnClicked(){
        // TODO: Change behavior when extra is checked
        System.out.println(itemInfo);
        if (itemInfo.get("Item_Sizes").isEmpty()) {
            CartModel.addToCart(UserModel.getUserName(), itemInfo.get("Item_Name"),
                    Double.parseDouble(itemInfo.get("Item_Price")), Integer.parseInt(qtyLabel.getText()),
                    itemInfo.get("Item_Store"), specialInstructionsTxt.getText(), null, itemInfo.get("Item_Image"));
        } else {
            HashMap<String, Integer> sizesMap = ItemModel.convertDocumentStrToHashMap(itemInfo.get("Item_Sizes"));
            CartModel.addToCart(UserModel.getUserName(), itemInfo.get("Item_Name"),
                    Double.parseDouble(itemInfo.get("Item_Price")), Integer.parseInt(qtyLabel.getText()),
                    itemInfo.get("Item_Store"), specialInstructionsTxt.getText(), null, itemInfo.get("Item_Image"));
        }

    }

    public void setData(HashMap<String, String> itemInfo) {
        this.itemInfo = itemInfo;
        mealName.setText((String) itemInfo.get("Item_Name"));
        mealPrice.setText("PHP " + itemInfo.get("Item_Price") + ".00");
        // sample item image
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/canteam/Byte/assets/images/Store/SampleItem.jpg")));
//        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream((String) itemInfo.get("Item_Image"))));
        mealImg.setImage(image);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setData(ItemModel.getSelectedItemInfo());

        // Make draggable
        draggable.makeWindowDraggable(statusBar);

        //Make scrollable
        draggable.makeScrollableY(scrollAnchorPane);

    }
}
