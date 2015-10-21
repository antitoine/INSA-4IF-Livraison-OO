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
public class PopOverContentDelivery extends BorderPane {
    
    @FXML
    Button btnDelete;

    @FXML
    Text adressText;
    
    public PopOverContentDelivery(String adress) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/PopOverContentDelivery.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(EmptyNodeView.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        btnDelete.setGraphic(new Glyph("FontAwesome", "TRASH"));
        btnDelete.setOnAction(MainWindow.getBtnListener());
        adressText.setText(adress);
    }
    
    @FXML
    private void delete(){
        // TODO : Call Controller
        
    }

}
