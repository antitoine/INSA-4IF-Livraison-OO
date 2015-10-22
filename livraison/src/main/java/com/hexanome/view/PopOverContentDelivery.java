package com.hexanome.view;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        btnDelete.setOnAction(MainWindow.getBtnListener());
        adressText.setText(adress);
    }
    
    @FXML
    private void delete(){
        // TODO : Call Controller
        
    }

}
