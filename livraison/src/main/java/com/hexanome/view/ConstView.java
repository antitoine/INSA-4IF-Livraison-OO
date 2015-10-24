package com.hexanome.view;

public final class ConstView {

    public static final String EMPTYNODE = "/fxml/EmptyNodeView.fxml";
    public static final String DELIVERYNODE = "/fxml/DeliveryNodeView.fxml";
    public static final String WAREHOUSENODE = "/fxml/WarehouseNodeView.fxml";
    public static final String POPOVERWAREHOUSE = "/fxml/PopOverContentWarehouse.fxml";
    public static final String POPOVEREMPTY = "/fxml/PopOverContentEmptyNode.fxml";
    public static final String POPOVERDELIVERY = "/fxml/PopOverContentDelivery.fxml";
    public static final String MAPVIEW = "/fxml/MapView.fxml";
    public static final String MAINWINDOW = "/fxml/MainWindow.fxml";

    public static enum Action {

        QUIT, ADD_NODE, DELETE_NODE
    }

    /**
     * must not be called !
     */
    private ConstView() {
        throw new AssertionError();
    }
}
