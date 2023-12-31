package com.canteam.Byte;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Application extends javafx.application.Application {
    public static Stage window;
    public static Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("fxml/login.fxml"));
        Scene home = new Scene(fxmlLoader.load());
        window = stage;
        stage.initStyle(StageStyle.UNDECORATED); // Remove the titleBar
        window.setScene(home);
        window.setTitle("Byte");
        // set icon
        window.getIcons().add(new javafx.scene.image.Image(Application.class.getResourceAsStream("assets/byte.png")));
        stage.setResizable(false);
        stage.show();
    }

    public static void changeScene(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/com/canteam/Byte/fxml/"+fxml+".fxml"));
        scene = new Scene(fxmlLoader.load());
        window.setScene(scene);
    }


    public static void main(String[] args) {
        launch();
    }
}