package com.canteam.Byte.Controllers;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.canteam.Byte.MongoDB.Connection;
import com.canteam.Byte.Models.UserModel;
import org.bson.Document;

public class LoginSignupController implements Initializable {

    @FXML
    private Button startButton, loginButton, signUpButton;

    @FXML
    private AnchorPane loginSignupPane, loginPane, signupPane;

    @FXML
    private ImageView statusBar;
    @FXML
    private TextField signupEmailField;
    @FXML
    private TextField signupNameField;
    @FXML
    private PasswordField signupPassField;
    @FXML
    private TextField signupUsernameField;
    @FXML
    private PasswordField loginPassField;
    @FXML
    private TextField loginUsernameField;
    @FXML
    private Label errorLabel;
    @FXML
    private Label errorLabel2;

    private MongoClient client = Connection.getInstance();
    private MongoDatabase db = client.getDatabase("Byte");
    private MongoCollection<Document> collection = db.getCollection("Users");

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
    protected void onSignUpClicked() {
        if (!UserModel.userExists(signupUsernameField.getText())) {
            if (!(signupNameField.getText().isEmpty() || signupUsernameField.getText().isEmpty() || signupEmailField.getText().isEmpty() || signupPassField.getText().isEmpty())) {
                // Create new user
                UserModel.createUser(
                        signupNameField.getText(), signupUsernameField.getText(), signupPassField.getText(),
                        null, "", "Customer", signupEmailField.getText());

                // switch to login pane
                signupPane.setVisible(false);
                loginPane.setVisible(true);

                // Set new user to true
                UserModel.setNewOldUser(true);

                // Clear fields
                signupNameField.setText(null);
                signupUsernameField.setText(null);
                signupEmailField.setText(null);
                signupPassField.setText(null);

            } else {
                errorLabel2.setText("Please fill in all fields");
            }
        } else {
            errorLabel2.setText("Username is already taken");
        }
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
        loginUsernameField.setStyle("-fx-border-width: 0");
        loginPassField.setStyle("-fx-border-width: 0");

        // Check if fields are null
        if (loginUsernameField.getText().isEmpty() || loginPassField.getText().isEmpty()){
            errorLabel.setText("Please fill in all fields");
        } else {
            String username = loginUsernameField.getText().trim();
            Document user = (Document) collection.find(new Document("Username", username)).first();

            if (user != null) {
                if (user.getString("Password").equals(loginPassField.getText())) {
                    UserModel.loginUser(username);
                    if (UserModel.getUserType().equals("Customer")) {
                        if (UserModel.isNewUser()){
                            pageNavigator.navigateToPage(loginButton, "Location");
                            return;
                        }
                        pageNavigator.forwardToPage(loginButton, "login", "Home");
                    } else if (UserModel.getUserType().equals("Store")) {
                        pageNavigator.forwardToPage(loginButton, "login", "ShopOrderList");
                    } else {
                        pageNavigator.forwardToPage(loginButton, "login", "RiderOrderList");
                    }
                } else {
                    loginPassField.setStyle(
                            "-fx-border-color: red;" +
                            "-fx-border-radius: 8px;");
                    errorLabel.setText("Incorrect Password");
                }
            } else {
                errorLabel.setText("Username does not exist");
                loginUsernameField.setStyle(
                        "-fx-border-color: red;" +
                        "-fx-border-radius: 8px;");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set up draggable
        draggable.makeWindowDraggable(statusBar);
    }
}