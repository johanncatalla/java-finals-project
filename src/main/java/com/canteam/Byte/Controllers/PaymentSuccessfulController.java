package com.canteam.Byte.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class PaymentSuccessfulController {
    @FXML
    private Label changeTotal;

    @FXML
    private Button okButton;

    @FXML
    private Label paidTotal;

    @FXML
    private Label productTotal, paymentCOD;

    @FXML
    private AnchorPane paymentDetails, CODPane;

    @FXML
    void onOkClicked(ActionEvent event) {
        // close the stage
        okButton.getScene().getWindow().hide();
    }

    public void setData(String productTotal, String paidTotal, String changeTotal) {
        // if the payment method is Card
        CODPane.setVisible(false);
        paymentDetails.setVisible(true);

        // set the data
        this.productTotal.setText(productTotal);
        this.paidTotal.setText(paidTotal);
        this.changeTotal.setText(changeTotal);
    }

    public void setData(String productTotal){
        // if the payment method is COD
        CODPane.setVisible(true);
        paymentDetails.setVisible(false);

        // set the data
        this.paymentCOD.setText(productTotal);
    }
}
