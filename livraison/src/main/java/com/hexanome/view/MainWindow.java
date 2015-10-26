package com.hexanome.view;

import com.hexanome.controller.UIManager;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainWindow extends AnchorPane {

    @FXML
    private MenuItem quitMenuItem;

    @FXML
    private Label labelInfos;

    @FXML
    private BorderPane mainPane;

    @FXML
    private MapView mapView;

    @FXML
    private DeliveryTreeView deliveryTreeView;

    final FileChooser fileChooser;

    static BtnListener btnListener;

    private Stage stage;

    /**
     * Main window
     *
     * @param stage Main stage for the application
     */
    public MainWindow(Stage stage) {
        btnListener = new BtnListener();
        fileChooser = new FileChooser();
        configureFileChooser(fileChooser, "Load file...");
        this.stage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ConstView.MAINWINDOW));
        fxmlLoader.setResources(ResourceBundle.getBundle("bundles.LangueBundle", new Locale("en")));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        mapView.initialize(null, null);

    }

    public static BtnListener getBtnListener() {
        return btnListener;
    }

    /**
     *
     * @param event
     */
    @FXML
    private void quitApplication(ActionEvent event) {
        UIManager.NotifyUI(ConstView.Action.QUIT);
    }

    public MapView getMapView() {
        return mapView;
    }

    public DeliveryTreeView getDeliveryTreeView() {
        return deliveryTreeView;
    }

    @FXML
    private void loadMap() {
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            // TODO : voir comment on implémente ça
            // openFile(file);
        }
    }

    @FXML
    private void loadPlanning() {
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            //  openFile(file);
        }
    }

    @FXML
    private void undo() {
        // TODO
    }

    @FXML
    private void redo() {
        // TODO
    }

    private static void configureFileChooser(final FileChooser fileChooser, String title) {
        fileChooser.setTitle(title);
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("xml", "*.xml"),
                new FileChooser.ExtensionFilter("All files", "*.*")
        );
    }

}
