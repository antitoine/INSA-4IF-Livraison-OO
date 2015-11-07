package com.hexanome.view;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.UIManager;
import javafx.scene.Node;
import javafx.scene.control.Label;
import org.controlsfx.control.PopOver;
import org.controlsfx.glyphfont.Glyph;

public class WarehouseNodeView extends Label implements INodeViewShape {
    com.hexanome.model.Node node;
    /**
     * Initializes the controller class.
     */
    public WarehouseNodeView(com.hexanome.model.Node node) {
        setGraphic(new Glyph("FontAwesome", "HOME"));
        this.node = node;
    }

    @Override
    public void onMouseClickedNotify(NodeView context) {
        ContextManager.getInstance().getCurrentState().clickOnWarehouse(node);
        UIManager.getInstance().getMainWindow().disablePanning();
    }

    @Override
    public Node asSceneNode() {
        return this;
    }

    @Override
    public PopOver createPopOver(com.hexanome.model.Node node) {
        return new PopOver(new PopOverContentWarehouse(node));
    }
}
