package com.canteam.Byte.Controllers;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController{

    @FXML
    private Button burgerCloseIcon, burgerOpenIcon;

    @FXML
    private AnchorPane burgerMenuPane;

    @FXML
    private ImageView cartIcon;

    @FXML
    private TextField searchField;

    @FXML
    protected void onBurgerOpenIconClicked(){
        TranslateTransition burgerMenuTransition = new TranslateTransition();
        burgerMenuTransition.setNode(burgerMenuPane);
        burgerMenuTransition.setToX(390);
        burgerMenuTransition.setDuration(Duration.seconds(.5));
        burgerMenuTransition.play();
    }

    @FXML
    protected void onBurgerCloseIconClicked(){
        TranslateTransition burgerMenuTransition = new TranslateTransition();
        burgerMenuTransition.setNode(burgerMenuPane);
        burgerMenuTransition.setToX(0);
        burgerMenuTransition.setDuration(Duration.seconds(.5));
        burgerMenuTransition.play();
    }

}

