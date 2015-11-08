package com.hexanome.view;

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

    public static final String POPOVERWAREHOUSE = "POPOVERWAREHOUSE";
    public static final String POPOVEREMPTY = "POPOVEREMPTY";
    public static final String POPOVERDELIVERY = "POPOVERDELIVERY";

    public static final String MAINWINDOW = "/fxml/MainWindow.fxml";
    public static final String ROAD_MAP = "/fxml/RoadMap.fxml";

    public static final double SIZE_NODE = 7.0;
   
    /**
     * Enumeration of the buttons existing in the user interface
     */
    public static enum Button {
        REDO, UNDO, LOAD_PLANNING, COMPUTE_ROUTE, ROAD_MAP
    }
    /**
     * Enumeration of the tree item types
     */
    public static enum TreeItemType {
        TIMESLOT, DELIVERY
    }
    /**
     * Enumeration of arcview types 
     */
    public static enum ArcViewType{
        STANDARD, ROUTE
    }

    /**
     * Must not be called !
     */
    private ConstView() {
        throw new AssertionError();
    }
}
