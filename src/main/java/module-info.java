module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires mongo.java.driver;

    exports com.canteam.Byte;
    opens com.canteam.Byte to javafx.fxml;
    exports com.canteam.Byte.Controllers;
    opens com.canteam.Byte.Controllers to javafx.fxml;
}