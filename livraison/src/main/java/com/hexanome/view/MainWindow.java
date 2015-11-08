package com.hexanome.view;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.ModelManager;
import com.hexanome.controller.UIManager;
import com.hexanome.model.TimeSlot;
import com.hexanome.utils.TypeWrapper;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
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

/**
 * This class represents the main window of the application
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class MainWindow extends AnchorPane {

    final FileChooser fileChooser;
    final double SCALE_DELTA_WHEEL = 1.1;
    final double SCALE_DELTA_BUTTON = 1.5;
    @FXML
    StackPane parentMapPane;
    Group mapGroup = null;
    ScrollPane scroller;
    Group scrollContent;
    double latestScaleFactor;
    Point2D latestScrollOffset;
    double latestHPan = -1;
    double latestWPan = -1;
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
        mapGroup = new Group(mapView);

        Parent zoomPane = configureZoomScrollPane(mapGroup);

        parentMapPane.getChildren().add(zoomPane);
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

    /**
     * Display an error
     *
     * @param msg error msg to display
     */
    public void displayError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error - " + msg);
        alert.setContentText(msg);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(480, 320);
        alert.showAndWait();
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
    public void setLegend() {
        legendGridPane.getChildren().clear();
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
        }
    }

    public void clearLegend() {
        legendGridPane.getChildren().clear();
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

    @SuppressWarnings("ConstantConditions")
    @FXML
    private void zoomIn() {
        mapGroup.setScaleX(mapGroup.getScaleX() * SCALE_DELTA_BUTTON);
        mapGroup.setScaleY(mapGroup.getScaleY() * SCALE_DELTA_BUTTON);
    }

    @SuppressWarnings("ConstantConditions")
    @FXML
    private void zoomOut() {
        mapGroup.setScaleX(mapGroup.getScaleX() * 1 / SCALE_DELTA_BUTTON);
        mapGroup.setScaleY(mapGroup.getScaleY() * 1 / SCALE_DELTA_BUTTON);
    }

    @SuppressWarnings("ConstantConditions")
    @FXML
    public void resetZoom() {
        mapGroup.setScaleX(1);
        mapGroup.setScaleY(1);
    }

    /**
     * Reset the scroller pane to its latest position
     * Use it to prevent the view being moved while redrawing
     * the mapView
     */
    public void repositionToLatestPosition() {
        if (latestScrollOffset != null) {
            repositionScroller(latestScaleFactor, latestScrollOffset);
        }
        if (latestHPan != -1 && latestWPan != -1) {
            scroller.setHvalue(Math.max(0, Math.min(scroller.getHmax(), latestHPan)));
            scroller.setVvalue(Math.max(0, Math.min(scroller.getVmax(), latestWPan)));

        }
    }

    /**
     * Method to create a zoomable scroll pane
     * inspired from http://stackoverflow.com/questions/16680295/javafx-correct-scaling
     */
    private Parent configureZoomScrollPane(final Group group) {
        final StackPane zoomPane = new StackPane();

        zoomPane.getChildren().add(group);

        scroller = new ScrollPane();
        scrollContent = new Group(zoomPane);
        scroller.setContent(scrollContent);

        scroller.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observable,
                                Bounds oldValue, Bounds newValue) {
                zoomPane.setMinSize(newValue.getWidth(), newValue.getHeight());
            }
        });

        /**
         * Allow the zoom with mouse wheel
         */
        zoomPane.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                event.consume();

                if (event.getDeltaY() == 0) {
                    return;
                }

                double scaleFactor;
                if (event.getDeltaY() > 0) {
                    scaleFactor = SCALE_DELTA_WHEEL;
                } else {
                    scaleFactor = 1 / SCALE_DELTA_WHEEL;
                }

                Point2D scrollOffset = measureScrollOffset();
                group.setScaleX(group.getScaleX() * scaleFactor);
                group.setScaleY(group.getScaleY() * scaleFactor);

                repositionScroller(scaleFactor, scrollOffset);
            }
        });

        // Configuration for Panning -----------
        final ObjectProperty<Point2D> lastMouseCoordinates = new SimpleObjectProperty<>();
        scrollContent.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lastMouseCoordinates.set(new Point2D(event.getX(), event.getY()));
                setCursor(Cursor.MOVE);
            }
        });

        scrollContent.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setCursor(Cursor.DEFAULT);
            }
        });

        scrollContent.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double deltaX = event.getX() - lastMouseCoordinates.get().getX();
                double extraWidth = scrollContent.getLayoutBounds().getWidth() - scroller.getViewportBounds().getWidth();
                double deltaH = deltaX * (scroller.getHmax() - scroller.getHmin()) / extraWidth;
                double desiredH = scroller.getHvalue() - deltaH;
                scroller.setHvalue(Math.max(0, Math.min(scroller.getHmax(), desiredH)));
                latestHPan = desiredH;

                double deltaY = event.getY() - lastMouseCoordinates.get().getY();
                double extraHeight = scrollContent.getLayoutBounds().getHeight() - scroller.getViewportBounds().getHeight();
                double deltaV = deltaY * (scroller.getHmax() - scroller.getHmin()) / extraHeight;
                double desiredV = scroller.getVvalue() - deltaV;
                scroller.setVvalue(Math.max(0, Math.min(scroller.getVmax(), desiredV)));
                latestWPan = desiredV;
            }
        });
        // end configuration for Panning -----------

        return scroller;
    }

    /**
     * Permits to move the viewport so that old center remains in the center after the
     * scaling
     */
    private void repositionScroller(double scaleFactor, Point2D scrollOffset) {
        latestScaleFactor = scaleFactor;
        latestScrollOffset = scrollOffset;

        double scrollXOffset = scrollOffset.getX();
        double scrollYOffset = scrollOffset.getY();
        double extraWidth = scrollContent.getLayoutBounds().getWidth()
                - scroller.getViewportBounds().getWidth();
        if (extraWidth > 0) {
            double halfWidth = scroller.getViewportBounds().getWidth() / 2;
            double newScrollXOffset = (scaleFactor - 1) * halfWidth + scaleFactor * scrollXOffset;
            scroller.setHvalue(scroller.getHmin() + newScrollXOffset * (scroller.getHmax()
                    - scroller.getHmin()) / extraWidth);
        } else {
            scroller.setHvalue(scroller.getHmin());
        }
        double extraHeight = scrollContent.getLayoutBounds().getHeight()
                - scroller.getViewportBounds().getHeight();
        if (extraHeight > 0) {
            double halfHeight = scroller.getViewportBounds().getHeight() / 2;
            double newScrollYOffset = (scaleFactor - 1) * halfHeight + scaleFactor * scrollYOffset;
            scroller.setVvalue(scroller.getVmin() + newScrollYOffset *
                    (scroller.getVmax() - scroller.getVmin()) / extraHeight);
        } else {
            scroller.setHvalue(scroller.getHmin());
        }


    }

    /**
     * amount of scrolling in each direction in scrollContent coordinate
     */
    private Point2D measureScrollOffset() {
        double extraWidth = scrollContent.getLayoutBounds().getWidth()
                - scroller.getViewportBounds().getWidth();
        double hScrollProportion = (scroller.getHvalue() - scroller.getHmin()) /
                (scroller.getHmax() - scroller.getHmin());
        double scrollXOffset = hScrollProportion * Math.max(0, extraWidth);
        double extraHeight = scrollContent.getLayoutBounds().getHeight()
                - scroller.getViewportBounds().getHeight();
        double vScrollProportion = (scroller.getVvalue() - scroller.getVmin()) /
                (scroller.getVmax() - scroller.getVmin());
        double scrollYOffset = vScrollProportion * Math.max(0, extraHeight);
        return new Point2D(scrollXOffset, scrollYOffset);
    }


}
