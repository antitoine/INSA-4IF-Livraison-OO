package com.hexanome.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeView;

/**
 * FXML Controller class
 */
public class DeliveryTreeView implements Initializable {

    @FXML
    TreeView deliveryTree; 
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        deliveryTree = new TreeView<String>();
    }   
    
    public void AddDelivery(){
        
    }
    
    public void DeleteDelivery(){
        
    }
    
    public void SwapDeliveries(){
        
    }
    
    
}
