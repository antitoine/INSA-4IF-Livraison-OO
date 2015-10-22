package com.hexanome.view;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import org.controlsfx.glyphfont.Glyph;

/**
 * FXML Controller class
 */
public class PopOverContentEmptyNode extends PopOverContent {
    
    @FXML
    Button btnValidate;
    
    @FXML
    ComboBox prevDeliveryComboBox; 

    @FXML
    Text adressText;
    
    public PopOverContentEmptyNode(String adress) {
        
        super(ConstView.POPOVEREMPTY);
        
        btnValidate.setGraphic(new Glyph("FontAwesome", "CHECK"));
        btnValidate.setOnAction(MainWindow.getBtnListener());
        prevDeliveryComboBox.getItems().addAll("livraison 1", "livraison 2");
        adressText.setText(adress);
    }
    
    @FXML
    private void validate(){
        // TODO : Call Controller
        
    }

}
