package com.hexanome.view;

import com.hexanome.controller.UIManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoadMapView extends GridPane {

    @FXML
    TextFlow roadMapDescription;

    @FXML
    Button saveButton;

    @FXML
    Button cancelButton;

    Stage stage;

    public RoadMapView(final LinkedList<Text> texts) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ConstView.ROAD_MAP));
        fxmlLoader.setResources(ResourceBundle.getBundle("bundles.LangueBundle", new Locale("en")));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            stage = new Stage();
            stage.setTitle("Road Map");
            stage.setScene(new Scene(this, 800, 600));
            stage.show();

            roadMapDescription.setPrefHeight(480);
            roadMapDescription.getChildren().addAll(texts);

        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });

        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                UIManager.getInstance().saveRoadMapDocument(stage);
            }
        });

    }

}
