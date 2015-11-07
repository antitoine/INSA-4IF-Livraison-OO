package com.hexanome.view;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.UIManager;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
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
    INodeViewShape nodeShape;

    com.hexanome.model.Node node;

    public NodeView(String nodeType, com.hexanome.model.Node node) {
        final NodeView self = this;

        setPrefHeight(20);
        setPrefWidth(20);
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

    String getCurrentNodeType() {
        return currentNodeType;
    }

    private void configurePopOver() {
        popover = nodeShape.createPopOver(node);

        popover.setAutoHide(true);
        popover.setArrowLocation(PopOver.ArrowLocation.BOTTOM_LEFT);
        popover.setDetachable(false);

        popover.setOnAutoHide(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                ContextManager.getInstance().getCurrentState().clickSomewhereElse();
                UIManager.getInstance().getMainWindow().enablePanning();
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

    /**
     * Hide a PopOver
     */
    void hidePopOver() {
        popover.hide();
    }

}
