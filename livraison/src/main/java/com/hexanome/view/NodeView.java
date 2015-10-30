package com.hexanome.view;

import com.hexanome.controller.UIManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.PopOver;

/**
 * NodeView
 *
 */
public class NodeView extends StackPane {

    PopOver popover;
    String currentNodeType;
    javafx.scene.Node nodeShape;

    com.hexanome.model.Node node;

    public NodeView(String nodeType, com.hexanome.model.Node node) {

        final NodeView self = this;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ConstView.NODE_VIEW));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(NodeView.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.node = node;
        currentNodeType = nodeType;
        setType(nodeType);

        setOnMouseClicked(new EventHandler<MouseEvent>() {
            // \todo pourquoi ne pas mettre en place un simple mécanisme de polymorphisme ? Les switch c'est le mal !
            @Override
            public void handle(MouseEvent event) {
                switch (currentNodeType) {
                    case ConstView.EMPTYNODE:
                        UIManager.getInstance().NotifyUI(ConstView.Action.CLICK_ON_EMPTY_NODE, self);
                        break;
                    case ConstView.DELIVERYNODE:
                        UIManager.getInstance().NotifyUI(ConstView.Action.CLICK_ON_DELIVERY_NODE, self);
                        break;
                    case ConstView.WAREHOUSENODE:
                        UIManager.getInstance().NotifyUI(ConstView.Action.CLICK_ON_WAREHOUSE, self);
                }
            }
        });

    }

    void setType(String nodeType) {
        getChildren().clear();
        switch (nodeType) {
            case ConstView.EMPTYNODE:
                nodeShape = new EmptyNodeView();
                break;
            case ConstView.DELIVERYNODE:
                nodeShape = new DeliveryNodeView();
                break;
            case ConstView.WAREHOUSENODE:
                nodeShape = new WarehouseNodeView();
                break;
        }
        getChildren().add(nodeShape);
        configurePopOver(nodeType);
        setAlignment(nodeShape, Pos.CENTER);
        currentNodeType = nodeType;
    }

    String getCurrentNodeType() {
        return currentNodeType;
    }
    

    private void configurePopOver(String nodeType) {
        switch (nodeType) {
            case ConstView.EMPTYNODE:
                popover = new PopOver(new PopOverContentEmptyNode(node));
                break;
            case ConstView.DELIVERYNODE:
                popover = new PopOver(new PopOverContentDelivery(node));
                break;
            case ConstView.WAREHOUSENODE:
                popover = new PopOver(new PopOverContentWarehouse(node));
                break;
        }
        popover.setAutoHide(true);
        popover.setArrowLocation(PopOver.ArrowLocation.BOTTOM_LEFT);
        popover.setDetachable(false);

        popover.setOnAutoHide(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                UIManager.getInstance().NotifyUI(ConstView.Action.HIDE_POPOVER);
            }
        });
    }

    /**
     * Return the Node of the nodeview
     *
     * @return the Node of the nodeview
     */
    public com.hexanome.model.Node getNode() {
        return node;
    }

    /**
     * Show a popover above a nodeview
     */
    public void showPopOver() {
        popover.show(this);
    }

}
