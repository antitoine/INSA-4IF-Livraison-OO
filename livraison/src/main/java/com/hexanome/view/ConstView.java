package com.hexanome.view;

/**
 * Contains all the different path for the fxml files, and also the different 
 * action that are handled by the UIManager
 */
public final class ConstView {
    
    public static final String APP_TITLE = "Delivery Manager";
    
    public static final String EMPTYNODE = "/fxml/EmptyNodeView.fxml";
    public static final String DELIVERYNODE = "/fxml/DeliveryNodeView.fxml";
    public static final String WAREHOUSENODE = "/fxml/WarehouseNodeView.fxml";
    public static final String POPOVERWAREHOUSE = "/fxml/PopOverContentWarehouse.fxml";
    public static final String POPOVEREMPTY = "/fxml/PopOverContentEmptyNode.fxml";
    public static final String POPOVERDELIVERY = "/fxml/PopOverContentDelivery.fxml";
    public static final String MAPVIEW = "/fxml/MapView.fxml";
    public static final String TREEVIEW = "/fxml/DeliveryTreeView.fxml";
    public static final String MAINWINDOW = "/fxml/MainWindow.fxml";

    public static enum Action {
        QUIT, ADD_DELIVERY, DELETE_DELIVERY, SWAP_DELIVERIES, UNDO, REDO, LOAD_MAP, LOAD_PLANNING,
        COMPUTE_ROUTE, GENERATE_ROADMAP, CLICK_ON_EMPTY_NODE, CLICK_ON_DELIVERY_NODE
    }
    
    public static enum TreeItemType {
        TIMESLOT, DELIVERY
    }

    /**
     * must not be called !
     */
    private ConstView() {
        throw new AssertionError();
    }
}
