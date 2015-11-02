package com.hexanome.view;

import com.hexanome.model.Node;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public abstract class PopOverContent extends BorderPane{
    
    Node node;

    public PopOverContent(String popOverType, Node node) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(popOverType));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(PopOverContent.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.node = node;

    }

}
