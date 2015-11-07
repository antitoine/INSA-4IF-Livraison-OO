package com.hexanome.view;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.ModelManager;
import com.hexanome.controller.UIManager;
import com.hexanome.model.TimeSlot;
import com.hexanome.utils.TypeWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainWindow extends AnchorPane {

    final FileChooser fileChooser;
    @FXML
    ScrollPane scrollPaneMap;
    boolean legendDisplayed = false;
    double zoomLevel = 1.0;
    private MapView mapView;
    private DeliveryTreeView deliveryTreeView;
    @FXML
    private Label labelInfos;
    @FXML
    private MenuItem mntmLoadPlanning;
    @FXML
    private Button btnLoadPlanning;
    @FXML
    private Button btnRedo;
    @FXML
    private Button btnUndo;
    @FXML
    private MenuItem mntmUndo;
    @FXML
    private MenuItem mntmRedo;
    @FXML
    private Button btnRoadMap;
    @FXML
    private BorderPane deliveriesPane;
    @FXML
    private GridPane legendGridPane;
    private Stage stage;

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

        deliveryTreeView = new DeliveryTreeView();
        deliveriesPane.setCenter(deliveryTreeView);
        mapView = new MapView();
        scrollPaneMap.setContent(mapView);
        legendGridPane.setVisible(false);

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

    public DeliveryTreeView getDeliveryTreeView() {
        return deliveryTreeView;
    }

    public MapView getMapView() {
        return mapView;
    }

    public void askFile() {
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            ContextManager.getInstance().getCurrentState().btnValidateFile(file);
        } else {
            ContextManager.getInstance().getCurrentState().btnCancel();
        }
    }

    /**
     *
     * @param text set info text to display at the bottom
     */
    public void setLoadingState(final String text) {
        labelInfos.setText(text);
        stage.getScene().setCursor(Cursor.WAIT);
    }

    /**
     * Reset the cursor and the info label at the end of a loading for example
     */
    public void resetCursorAndInfoLabel() {
        labelInfos.setText("");
        stage.getScene().setCursor(Cursor.DEFAULT);

    }

    public void disablePanning() {
        scrollPaneMap.setPannable(false);
    }

    public void enablePanning() {
        scrollPaneMap.setPannable(true);
    }

    /**
     * Display an error
     *
     * @param msg error msg to display
     */
    public void displayError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error - " + msg);
        alert.setContentText(msg);
        alert.show();
    }

    /**
     * Disable the button
     *
     * @param btn btn to disable
     */
    public void disableButton(ConstView.Button btn) {
        switch (btn) {
            case UNDO:
                mntmUndo.setDisable(true);
                btnUndo.setDisable(true);
                break;
            case REDO:
                mntmRedo.setDisable(true);
                btnRedo.setDisable(true);
                break;
            case ROAD_MAP:
                btnRoadMap.setDisable(true);
                break;
            case LOAD_PLANNING:
                mntmLoadPlanning.setDisable(true);
                btnLoadPlanning.setDisable(true);
                break;
        }
    }

    /**
     * Enable the button
     *
     * @param btn btn to enable
     */
    public void enableButton(ConstView.Button btn) {
        switch (btn) {
            case UNDO:
                mntmUndo.setDisable(false);
                btnUndo.setDisable(false);
                break;
            case REDO:
                mntmRedo.setDisable(false);
                btnRedo.setDisable(false);
                break;
            case ROAD_MAP:
                btnRoadMap.setDisable(false);
                break;
            case LOAD_PLANNING:
                mntmLoadPlanning.setDisable(false);
                btnLoadPlanning.setDisable(false);
                break;
        }
    }

    /**
     * Displays the legend in the main Window if not already displayed The
     * planning must be initialized before calling this method
     */
    void setLegend() {
        if (legendDisplayed) {
            return;
        }
        legendGridPane.setVisible(true);
        int i = 1;
        Text txtNotInTime = new Text("Out of time slot");
        Circle circle = new Circle(5.0, Color.RED);
        GridPane.setMargin(circle, new Insets(0, 12, 0, 0));
        GridPane.setMargin(txtNotInTime, new Insets(0, 0, 0, 12));
        legendGridPane.add(txtNotInTime, 0, 0);
        legendGridPane.add(circle, 1, 0);
        for (TimeSlot ts : ModelManager.getInstance().getPlanning().getTimeSlots()) {
            Rectangle rect = new Rectangle(40, 5, ColorsGenerator.getTimeSlotColor(ts));
            String start = TypeWrapper.secondsToTimestamp(ts.getStartTime());
            String end = TypeWrapper.secondsToTimestamp(ts.getEndTime());
            Text txt = new Text(start + " - " + end);
            legendGridPane.add(txt, 0, i);
            legendGridPane.add(rect, 1, i);
            GridPane.setMargin(rect, new Insets(0, 12, 0, 0));
            GridPane.setMargin(txt, new Insets(0, 0, 0, 12));
            i++;
        };
        legendDisplayed = true;
    }

    @FXML
    private void quitApplication(ActionEvent event) {
        ContextManager.getInstance().exit(); // Special undoable
    }

    @FXML
    private void loadMap() {
        ContextManager.getInstance().getCurrentState().btnLoadMap();
    }

    @FXML
    private void loadPlanning() {
        ContextManager.getInstance().getCurrentState().btnLoadPlanning();
    }

    @FXML
    private void generateRoadMap() {
        // ContextManager.getInstance().getCurrentState().
        UIManager.getInstance().generateRoadMap();
    }

    @FXML
    private void undo() {
        ContextManager.getInstance().undo();
    }

    @FXML
    private void redo() {
        ContextManager.getInstance().redo();
    }

    @FXML
    private void zoomIn() {
        zoomLevel += 0.5;
        mapView.setScaleX(zoomLevel);
        mapView.setScaleY(zoomLevel);
        //TODO update scrollbar
    }

    @FXML
    private void zoomOut() {
        zoomLevel -= 0.25;
        mapView.setScaleX(zoomLevel);
        mapView.setScaleY(zoomLevel);

        scrollPaneMap.setFitToWidth(true);
        scrollPaneMap.setFitToHeight(true);
        //TODO update scrollbar
    }



}
