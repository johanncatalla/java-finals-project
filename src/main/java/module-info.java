module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires org.json;
    requires org.mongodb.bson;

    exports com.canteam.Byte;
    opens com.canteam.Byte to javafx.fxml;
    exports com.canteam.Byte.Controllers;
    opens com.canteam.Byte.Controllers to javafx.fxml;
}