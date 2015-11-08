package com.hexanome.view;

import com.hexanome.controller.ContextManager;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.PopOver;

/**
 * This class is the generic graphic component equivalent of the model node
 * 
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class NodeView extends StackPane {

    PopOver popover;
    String currentNodeType;
    INodeViewShape nodeShape;

    com.hexanome.model.Node node;

    public NodeView(String nodeType, com.hexanome.model.Node node) {
        final NodeView self = this;

        setPrefHeight(10);
        setPrefWidth(10);
        setCursor(Cursor.HAND);

        this.node = node;
        currentNodeType = nodeType;
        setType(nodeType);

        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                nodeShape.onMouseClickedNotify(self);
            }
        });
    }

    public PopOverContent getPopoverContent() {
        return (PopOverContent) popover.getContentNode();
    }

    /**
     * Set the type of the node See Constview in order to see the node types
     *
     * @param nodeType
     */
    final void setType(String nodeType) {
        getChildren().clear();

        nodeShape = NodeViewShapeFactory.createNodeViewShape(nodeType, node);
        getChildren().add(nodeShape.asSceneNode());
        configurePopOver();
        setAlignment(nodeShape.asSceneNode(), Pos.CENTER);

        currentNodeType = nodeType;
    }

    private void configurePopOver() {
        popover = nodeShape.createPopOver(node);

        popover.setAutoHide(true);
        popover.setDetachable(false);

        popover.setOnAutoHide(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                ContextManager.getInstance().getCurrentState().clickSomewhereElse();
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
        Bounds bounds = this.localToScene(this.getBoundsInLocal());
        if (bounds.getMaxX() < 600 && bounds.getMaxY() < 300) {
            popover.setArrowLocation(PopOver.ArrowLocation.TOP_LEFT);
        } else if (bounds.getMaxY() < 300) {
            popover.setArrowLocation(PopOver.ArrowLocation.TOP_RIGHT);
        } else if (bounds.getMaxX() < 600) {
            popover.setArrowLocation(PopOver.ArrowLocation.BOTTOM_LEFT);
        } else {
            popover.setArrowLocation(PopOver.ArrowLocation.BOTTOM_RIGHT);
        }
        popover.show(this);
    }

    /**
     * Hide a PopOver
     */
    void hidePopOver() {
        popover.hide();
    }

}
