/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.controller;

import com.hexanome.model.Delivery;
import com.hexanome.model.Node;
import com.hexanome.model.TimeSlot;
import com.hexanome.view.DeliveryTreeView;
import java.util.Random;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author hverlin
 */
public class UIManager extends Application{

    public UIManager() {
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        // créer la fenêtre principale
        stage.setTitle("Editeur de livraisons");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
        
        test();
    }
    
    public void NotifyUI(final String Action){
        
    }
    
    private void test(){
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
                DeliveryTreeView.AddTimeSlot(new TimeSlot(r.nextInt(24), r.nextInt(24),null) );
            for(int j = 0; j < 10 ; j++){
                DeliveryTreeView.AddDelivery(new Delivery(new Node(r.nextInt(10000), j, j)));
            }
        }
        
    }
}
