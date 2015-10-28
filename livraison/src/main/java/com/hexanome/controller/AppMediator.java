package com.hexanome.controller;

import com.hexanome.view.ConstView;
import com.hexanome.view.MainWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class implement the Meditor Pattern
 * and is responsible for attaching the main window
 * to the scene as it is not possible to get 
 * the current instance of a javaFX application
 * 
 * The start method should be called by launch(AppMediator.class, args)
 */
public class AppMediator extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        MainWindow mainWindow = UIManager.getInstance().createMainWindow(stage);
        
        Scene scene = new Scene(mainWindow);
        scene.getStylesheets().add("/styles/Styles.css");

        // Contructs the main scene that will include all the UI
        stage.setTitle(ConstView.APP_TITLE);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

    }

}