package com.hexanome.view;

import com.hexanome.controller.ContextManager;
import com.hexanome.model.Node;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.PopOver;

/**
 * This class is the generic graphic component equivalent of the model node
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
class NodeView extends StackPane {

    private PopOver popover;
    private INodeViewShape nodeShape;

    private com.hexanome.model.Node node;

    /**
     * Create a node view of a defined type
     *
     * @param nodeType node type
     * @param node     Node as described in the model,
     *                 which should be associated with the node view
     * @param cursor   cursor shape when overing node
     */
    public NodeView(Node node, Cursor cursor) {
        final NodeView self = this;

        setPrefHeight(10);
        setPrefWidth(10);
        setCursor(Cursor.HAND);

        this.node = node;
        setType(ConstView.EMPTY_NODE);

        setCursor(cursor);
        setOnMouseClicked(event -> nodeShape.onMouseClickedNotify(self));
    }

    /**
     * Set the type of the node See Constview in order to see the node types
     *
     * @param nodeType Node type
     */
    final void setType(String nodeType) {
        getChildren().clear();

        nodeShape = NodeViewShapeFactory.createNodeViewShape(nodeType, node);
        getChildren().add(nodeShape.asSceneNode());
        configurePopOver();
        setAlignment(nodeShape.asSceneNode(), Pos.CENTER);
    }

    private void configurePopOver() {
        popover = nodeShape.createPopOver(node);

        popover.setAutoHide(true);
        popover.setDetachable(false);

        popover.setOnAutoHide(event ->
                ContextManager.getInstance().getCurrentState().clickSomewhereElse());
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
