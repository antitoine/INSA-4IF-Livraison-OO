package com.hexanome.view;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 */
public class EmptyNodeView extends Circle{

    /**
     * Initializes the controller class.
     */
    public EmptyNodeView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ConstView.EMPTYNODE));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(EmptyNodeView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
