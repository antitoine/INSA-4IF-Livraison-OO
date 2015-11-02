package com.hexanome.view;

/**
 * Contains all the different path for the fxml files, and also the different
 * action that are handled by the UIManager
 */
public final class ConstView {

    public static final String APP_TITLE = "Delivery Manager";

    public static final String NODE_VIEW = "/fxml/NodeView.fxml";
    public static final String EMPTYNODE = "/fxml/EmptyNodeView.fxml";
    public static final String DELIVERYNODE = "/fxml/DeliveryNodeView.fxml";
    public static final String WAREHOUSENODE = "/fxml/WarehouseNodeView.fxml";

    public static final String ARCVIEW = "/fxml/ArcView.fxml";

    public static final String POPOVERWAREHOUSE = "/fxml/PopOverContentWarehouse.fxml";
    public static final String POPOVEREMPTY = "/fxml/PopOverContentEmptyNode.fxml";
    public static final String POPOVERDELIVERY = "/fxml/PopOverContentDelivery.fxml";

    public static final String MAPVIEW = "/fxml/MapView.fxml";
    public static final String TREEVIEW = "/fxml/DeliveryTreeView.fxml";
    public static final String MAINWINDOW = "/fxml/MainWindow.fxml";

    public static final double SIZE_NODE = 8.0;
   
    public static enum Button {
        REDO, UNDO, LOAD_PLANNING, COMPUTE_ROUTE, ROAD_MAP
    }

    public static enum Action {

        QUIT, ADD_DELIVERY, DELETE_DELIVERY, SWAP_DELIVERIES, UNDO, REDO,
        COMPUTE_ROUTE, GENERATE_ROADMAP, CLICK_ON_EMPTY_NODE, CLICK_ON_DELIVERY_NODE,
        CLICK_ON_WAREHOUSE, HIDE_POPOVER, DELEVERY_SELECTED
    }

    public static enum TreeItemType {
        TIMESLOT, DELIVERY
    }
    public static enum ArcViewType{
        STANDARD, ROUTE
    }

    /**
     * must not be called !
     */
    private ConstView() {
        throw new AssertionError();
    }
}
