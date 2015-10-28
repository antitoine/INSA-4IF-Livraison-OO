package com.hexanome.view;

import com.hexanome.controller.UIManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import org.controlsfx.glyphfont.Glyph;

/**
 * FXML Controller class
 */
public class PopOverContentDelivery extends PopOverContent {
    
    @FXML
    Button btnDelete;
    
    @FXML
    Text adressText;
    
    public PopOverContentDelivery(String adress) {
        super(ConstView.POPOVERDELIVERY);
        
        btnDelete.setGraphic(new Glyph("FontAwesome", "TRASH"));
        btnDelete.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                UIManager.getInstance().NotifyUI(ConstView.Action.DELETE_DELIVERY, adress);
            }
        });
        adressText.setText(adress);
    }
    
    @FXML
    private void delete() {
        // TODO : Call Controller
        
    }
    
}
