package com.canteam.Byte;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainApplication extends Application {
    public static Stage window;
    public static Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
        window = stage;
        changeScene("Login");
        // stage.initStyle(StageStyle.UNDECORATED); // Remove the titleBar
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void changeScene(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/canteam/Byte/fxml/"+fxml+".fxml"));
        scene = new Scene(fxmlLoader.load());
        window.setScene(scene);
    }


    public static void main(String[] args) {
        launch();
    }
}