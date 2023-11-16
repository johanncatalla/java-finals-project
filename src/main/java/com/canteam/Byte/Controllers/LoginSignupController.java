package com.canteam.Byte.Controllers;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginSignupController implements Initializable {

    @FXML
    private Button startButton, loginButton, signUpButton;

    @FXML
    private AnchorPane loginSignupPane, loginPane, signupPane;

    @FXML
    private ImageView statusBar;

    PageNavigator pageNavigator = new PageNavigator();

    Draggable draggable = new Draggable();

    @FXML
    protected void onLogoClicked(){

        // LoginSlider
        TranslateTransition loginSlider = new TranslateTransition();
        loginSlider.setNode(loginSignupPane);
        loginSlider.setToY(-386);
        loginSlider.setDuration(Duration.seconds(.5));
        loginSlider.play();

        //LogoSlider
        TranslateTransition logoSlider = new TranslateTransition();
        logoSlider.setNode(startButton);
        logoSlider.setToY(-136);
        logoSlider.setDuration(Duration.seconds(.5));
        logoSlider.play();
    }

    @FXML
    protected void onToSignUpClicked(){
        // Switch visibility to signup pane
        signupPane.setVisible(true);
        loginPane.setVisible(false);
    }

    @FXML
    protected void onToLoginClicked(){
        // Switch visibility to login pane
        signupPane.setVisible(false);
        loginPane.setVisible(true);
    }

    @FXML
    protected void onLoginButtonClicked() throws IOException {
        pageNavigator.navigateToPage(loginButton, "Home");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set up draggable
        draggable.makeWindowDraggable(statusBar);
    }
}