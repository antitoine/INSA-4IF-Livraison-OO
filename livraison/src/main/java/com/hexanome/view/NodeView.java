package com.hexanome.view;

import com.hexanome.controller.UIManager;
import java.awt.Point;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.controlsfx.control.PopOver;

/**
 * NodeView
 *
 */
public abstract class NodeView extends Pane {

    Point location;
    PopOver popover;
    String currentNodeType;

    @FXML
    Node nodeShape;

    public NodeView(String nodeType, Point p) {

        NodeView self = this;
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(nodeType));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(NodeView.class.getName()).log(Level.SEVERE, null, ex);
        }

        location = p;
        configurePopOver(nodeType);
        currentNodeType = nodeType;

        nodeShape.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                switch (currentNodeType) {
                    case ConstView.EMPTYNODE:
                        UIManager.getInstance().NotifyUI(ConstView.Action.CLICK_ON_EMPTY_NODE, self);
                        break;
                    case ConstView.DELIVERYNODE:
                        UIManager.getInstance().NotifyUI(ConstView.Action.DELETE_DELIVERY, self);
                        break;
                    case ConstView.WAREHOUSENODE:
                        UIManager.getInstance().NotifyUI(ConstView.Action.CLICK_ON_WAREHOUSE, self);
                }
            }
        });

    }

    private void configurePopOver(String nodeType) {
        switch (nodeType) {
            case ConstView.EMPTYNODE:
                popover = new PopOver(new PopOverContentEmptyNode("" + location.x + ", " + location.y));
                break;
            case ConstView.DELIVERYNODE:
                popover = new PopOver(new PopOverContentDelivery("" + location.x + ", " + location.y));
                break;
            case ConstView.WAREHOUSENODE:
                popover = new PopOver(new PopOverContentWarehouse("" + location.x + ", " + location.y));
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
     * Return the location of the nodeview
     * @return 
     */
    public Point getLocation() {
        return location;
    }

    /**
     * Show a popover above a nodeview
     */
    public void showPopOver() {
        popover.show(nodeShape);
    }

}
