package com.hexanome.view;

import com.hexanome.controller.ContextManager;
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
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainWindow extends AnchorPane {

    @FXML
    private Label labelInfos;

    @FXML
    private MapView mapView;

    @FXML
    ScrollPane scrollPaneMap;

    @FXML
    private DeliveryTreeView deliveryTreeView;

    final FileChooser fileChooser;
    private Stage stage;
    double zoomLevel = 1.0;

    /**
     * Main window
     *
     * @param stage Application stage
     */
    public MainWindow(Stage stage) {
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

    /**
     * Notify the UI Manager that the user wants to quit the application
     *
     * @param event
     */
    @FXML
    private void quitApplication(ActionEvent event) {
        UIManager.getInstance().NotifyUI(ConstView.Action.QUIT);
    }

    public MapView getMapView() {
        return mapView;
    }

    public DeliveryTreeView getDeliveryTreeView() {
        return deliveryTreeView;
    }

    @FXML
    private void loadMap() {
        ContextManager.getInstance().getCurrentState().btnLoadMap();
    }

    @FXML
    private void loadPlanning() {
        ContextManager.getInstance().getCurrentState().btnLoadPlanning();
    }

    public void askFile() {
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            ContextManager.getInstance().getCurrentState().btnValidateFile(file);
        } else {
            ContextManager.getInstance().getCurrentState().btnCancel();
        }
    }

    @FXML
    private void undo() {
        UIManager.getInstance().NotifyUI(ConstView.Action.UNDO);
    }

    @FXML
    private void redo() {
        UIManager.getInstance().NotifyUI(ConstView.Action.REDO);
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

    public void SetLoadingState(final String text) {
        labelInfos.setText(text);
        stage.getScene().setCursor(Cursor.WAIT);
    }

    public void SetLoadingDone() {
        labelInfos.setText("");
        stage.getScene().setCursor(Cursor.DEFAULT);

    }

    public void disablePanning() {
        scrollPaneMap.setPannable(false);
    }

    public void ennablePanning() {
        scrollPaneMap.setPannable(true);
    }

    @FXML
    public void zoomIn() {
        zoomLevel += 0.5;
        mapView.setScaleX(zoomLevel);
        mapView.setScaleY(zoomLevel);
        //TODO update scrollbar
    }

    @FXML
    public void zoomOut() {
        zoomLevel -= 0.25;
        mapView.setScaleX(zoomLevel);
        mapView.setScaleY(zoomLevel);

        scrollPaneMap.setFitToWidth(true);
        scrollPaneMap.setFitToHeight(true);
        //TODO update scrollbar
    }

    public DeliveryTreeView getDeliveryTree() {
        return deliveryTreeView;
    }

    public void displayError(String msg) {
        SetLoadingDone();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error - "+msg);
        alert.setContentText(msg);
        alert.showAndWait();
    }

}
