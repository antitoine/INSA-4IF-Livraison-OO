package com.hexanome.view;

public final class ConstView {

    public static final String EMPTYNODE = "/fxml/EmptyNodeView.fxml";
    public static final String DELIVERYNODE = "/fxml/DeliveryNodeView.fxml";
    public static final String WAREHOUSENODE = "/fxml/WarehouseNodeView.fxml";
    public static final String POPOVERWAREHOUSE = "/fxml/PopOverContentWarehouse.fxml";
    public static final String POPOVEREMPTY = "/fxml/PopOverContentEmptyNode.fxml";
    public static final String POPOVERDELIVERY = "/fxml/PopOverContentDelivery.fxml";

    public  static enum Action {
        QUIT, VALIDATE, TUESDAY, WEDNESDAY,
        THURSDAY, FRIDAY, SATURDAY
    }

    /**
     * Private this prevents even the native class from calling this ctor as
     * well :
     */
    private ConstView() {
        throw new AssertionError();
    }
}
