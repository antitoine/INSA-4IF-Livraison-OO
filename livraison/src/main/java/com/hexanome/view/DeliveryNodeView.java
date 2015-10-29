package com.hexanome.view;

/**
 * FXML Controller class
 */
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class DeliveryNodeView extends Circle {

    public DeliveryNodeView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ConstView.DELIVERYNODE));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(DeliveryNodeView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
