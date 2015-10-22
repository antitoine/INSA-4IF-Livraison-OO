package com.hexanome.view;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public abstract class PopOverContent extends BorderPane{

    public PopOverContent(String popOverType) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(popOverType));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(EmptyNodeView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
