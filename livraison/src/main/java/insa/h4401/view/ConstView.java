package insa.h4401.view;

/**
 * Contains all the different path for the fxml files
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public final class ConstView {

    public static final String APP_TITLE = "Delivery Manager";

    public static final String EMPTY_NODE = "EMPTY_NODE";
    public static final String DELIVERY_NODE = "DELIVERYNODE";
    public static final String WAREHOUSE_NODE = "WAREHOUSENODE";

    public static final String MAINWINDOW = "/fxml/MainWindow.fxml";
    public static final String ROAD_MAP = "/fxml/RoadMap.fxml";

    public static final double SIZE_NODE = 6.0;

    /**
     * Must not be called !
     */
    private ConstView() {
        throw new AssertionError();
    }

    /**
     * Enumeration of the buttons existing in the user interface
     */
    public enum Button {
        REDO, UNDO, LOAD_PLANNING, COMPUTE_ROUTE, ROAD_MAP, CLEAR_MAP, CLEAR_PLANNING
    }

    /**
     * Enumeration of the tree item types
     */
    public enum TreeItemType {
        TIMESLOT, DELIVERY
    }

}
