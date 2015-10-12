package com.hexanome.livraison;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

public class FXMLController implements Initializable {
    
    
    @FXML
    private MenuItem quitMenuItem;
    
    @FXML
    private void quitApplication(ActionEvent event){
        System.exit(0);
    }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
