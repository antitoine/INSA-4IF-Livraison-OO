package com.hexanome.view;

import java.awt.Point;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 */
public class WarehouseNodeView extends Label {

    /**
     * Initializes the controller class.
     */
    public WarehouseNodeView() {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ConstView.WAREHOUSENODE));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(WarehouseNodeView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
