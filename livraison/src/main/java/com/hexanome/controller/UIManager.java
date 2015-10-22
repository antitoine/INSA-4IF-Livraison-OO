/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.controller;

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
    }
    
    public void NotifyUI(final String Action){
        
    }
}
