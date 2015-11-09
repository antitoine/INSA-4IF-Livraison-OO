package com.hexanome.controller;

import com.hexanome.utils.RouteDocument;
import com.hexanome.view.ColorsGenerator;
import com.hexanome.view.MainWindow;
import com.hexanome.view.RoadMapView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

/**
 * This controller manage all the view and is notify when something happens on
 * the view
 * <p>
 * It implements the sigleton design pattern and only one instance should exist
 * when the application is running
 * <p>
 * Should always be use as following UIManager.getInstance(). ...
 * 
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class UIManager {

    private static UIManager uimanager = null;

    /**
     * Main Window of the application see MainWindow class for more information
     */
    private MainWindow mainWindow;

    private UIManager() {
    }

    /**
     * Return the single of the UIManager
     *
     * @return the UIManager Instance
     */
    public static UIManager getInstance() {
        if (uimanager == null) {
            uimanager = new UIManager();
        }
        return uimanager;
    }

    /**
     * Create the main Window and return it
     *
     * @param stage the main stage
     * @return the mainWindow which should be integrated into the scene
     */
    MainWindow createMainWindow(Stage stage) {
        mainWindow = new MainWindow(stage);
        return mainWindow;
    }

    /**
     * Asks for confirmation
     *
     * @param message message for the dialog
     * @return true if the user select ok, false otherwise
     */
    public boolean askConfirmation(String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(message);
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        return (result.get() == ButtonType.OK);
    }

    /**
     * @return the mainWindow
     */
    public MainWindow getMainWindow() {
        return mainWindow;
    }

    /**
     * Modifies mainwindow according to the action of loading a map
     */
    public void beginLoadMap() {
        mainWindow.setLoadingState("Loading Map...");
        mainWindow.getMapView().clearMap();
        mainWindow.getDeliveryTreeView().clearTree();
    }

    /**
     * Modifies mainwindow according to the action of stoping map loading process
     */
    public void endLoadMap() {
        ModelManager.getInstance().getMap().clearSubscribers();
        ModelManager.getInstance().getMap().addSubscriber(mainWindow.getMapView());
        mainWindow.endLoadingState();
        mainWindow.clearLegend();
        mainWindow.resetZoom();
    }

    /**
     * Modifies mainwindow according to the action of loading a planning
     */
    public void beginLoadPlanning() {
        mainWindow.setLoadingState("Loading Planning...");
        mainWindow.getDeliveryTreeView().clearTree();
    }

    /**
     * Modifies mainwindow according to the action of stoping planning loading process
     */
    public void endLoadPlanning() {
        // Add view subscribers to the model
        ModelManager.getInstance().getPlanning().clearSubscribers();
        ModelManager.getInstance().getPlanning().addSubscriber(mainWindow.getDeliveryTreeView());
        ColorsGenerator.getInstance().createColors(ModelManager.getInstance().getPlanning().getTimeSlots());
        ModelManager.getInstance().getPlanning().addSubscriber(mainWindow.getMapView());
        
        mainWindow.clearLegend();
        mainWindow.setLegend();
        // Update mainwindow
        mainWindow.resetZoom();
        mainWindow.endLoadingState();
    }

    public void beginComputingRoute() {
        mainWindow.setLoadingState("Computing Route...");
    }

    /**
     * Change subscribers of the route
     */
    public void endRouteComputation() {
        mainWindow.endLoadingState();
        ModelManager.getInstance().getPlanning().getRoute().removeSubscriber(mainWindow.getMapView());
        ModelManager.getInstance().getPlanning().getRoute().addSubscriber(mainWindow.getMapView());
        ModelManager.getInstance().getPlanning().notifySubscribers();
        mainWindow.repositionToLatestPosition();
    }

    /**
     * Modifies mainwindow to display an error message
     */
    public void showError(String msg) {
        mainWindow.endLoadingState();
        // Ask main window to display error
        mainWindow.displayError(msg);
    }

    /**
     * Generate the road Map file
     * (TODO : display it in a dialog)
     */
    public void generateRoadMap() {
        RoadMapView roadMapView = new RoadMapView(
                RouteDocument.generateFormatedRouteDocumentContent(
                        ModelManager.getInstance().getPlanning().getRoute()
                )
        );
        roadMapView.display();
    }

    /**
     * Saves the roadmap to a file
     * @param stage 
     * @param text The text describing the road map.
     */
    public void saveRoadMapDocument(Stage stage, String text) {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            if (!IOManager.getInstance().saveRouteDocument(file, text)) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("There was a problem when saving your file ! ");
                alert.show();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Success !");
            alert.setContentText("Your Road map is saved ! ");
            alert.show();
            stage.close();

        }
    }
}
