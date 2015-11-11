package insa.h4401.controller;

import insa.h4401.utils.RouteDocument;
import insa.h4401.view.ColorsGenerator;
import insa.h4401.view.MainWindow;
import insa.h4401.view.RoadMapView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

/**
 * This controller manages all the view and is notified when something happens 
 * on the view.
 * 
 * It implements the sigleton design pattern and only one instance should exist
 * when the application is running.
 * 
 * Should always be used as following UIManager.getInstance(). ...
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class UIManager {

    /** The unique instance of UIManager. */
    private static UIManager uimanager = null;

    /**
     * Main Window of the application managed by the UIManager.
     * @see MainWindow 
     */
    private MainWindow mainWindow;

    /**
     * Constructs the UIManager.
     */
    private UIManager() {
        // Nothing to do here, but it's a private method.
    }

    /**
     * @return The single instance of the UIManager.
     */
    public static UIManager getInstance() {
        if (uimanager == null) {
            uimanager = new UIManager();
        }
        return uimanager;
    }

    /**
     * Creates the main Window and returns it.
     *
     * @param stage The main stage to use to create the main window.
     * @return The mainWindow which should be integrated into the scene.
     */
    protected MainWindow createMainWindow(Stage stage) {
        mainWindow = new MainWindow(stage);
        return mainWindow;
    }

    /**
     * Asks for a confirmation message.
     *
     * @param message The message for the confirmation dialog.
     * @return True if the user selects ok, false otherwise.
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
     * @return The mainWindow managed by the UIManager.
     */
    public MainWindow getMainWindow() {
        return mainWindow;
    }

    /**
     * Updates mainwindow according to the action of loading a map.
     */
    public void beginLoadMap() {
        mainWindow.setLoadingState("Loading Map...");
        mainWindow.getMapView().clearMap();
        mainWindow.getDeliveryTreeView().clearTree();
    }

    /**
     * Updates mainwindow according to the action of stoping map loading process.
     */
    public void endLoadMap() {
        ModelManager.getInstance().getMap().clearSubscribers();
        ModelManager.getInstance().getMap().addSubscriber(mainWindow.getMapView());
        mainWindow.endLoadingState();
        mainWindow.clearLegend();
        mainWindow.resetZoom();
    }

    /**
     * Updates mainwindow according to the action of loading a planning.
     */
    public void beginLoadPlanning() {
        mainWindow.setLoadingState("Loading Planning...");
        mainWindow.getDeliveryTreeView().clearTree();
    }

    /**
     * Updates mainwindow according to the action of stoping planning loading process.
     */
    public void endLoadPlanning() {
        ModelManager.getInstance().getPlanning().clearSubscribers();
        ModelManager.getInstance().getPlanning().addSubscriber(mainWindow.getDeliveryTreeView());
        ColorsGenerator.getInstance().createColors(ModelManager.getInstance().getPlanning().getTimeSlots());
        ModelManager.getInstance().getPlanning().addSubscriber(mainWindow.getMapView());

        mainWindow.clearLegend();
        mainWindow.setLegend();
        mainWindow.resetZoom();
        
        mainWindow.endLoadingState();
    }

    /**
     * Updates the view according to the action of starting the route computing.
     */
    public void beginComputingRoute() {
        mainWindow.setLoadingState("Computing Route...");
    }

    /**
     * Updates the view according to the end of the route computing. Updates
     * the planning view.
     */
    public void endRouteComputation() {
        mainWindow.endLoadingState();
        ModelManager.getInstance().getPlanning().getRoute().removeSubscriber(mainWindow.getMapView());
        ModelManager.getInstance().getPlanning().getRoute().addSubscriber(mainWindow.getMapView());
        ModelManager.getInstance().getPlanning().notifySubscribers();
        mainWindow.repositionToLatestPosition();
    }

    /**
     * Updates mainwindow to display an error message.
     */
    public void showError(String msg) {
        mainWindow.endLoadingState();
        mainWindow.displayError(msg);
    }

    /**
     * Generates the road Map file.
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
     * Saves the roadmap to a file.
     *
     * @param stage Stage above which should be displayed the file Chooser.
     * @param text  The text describing the road map.
     */
    public void saveRoadMapDocument(Stage stage, String text) {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            if (!IOManager.getInstance().saveRouteDocument(file, text)) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("There was a problem when saving your file !");
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
