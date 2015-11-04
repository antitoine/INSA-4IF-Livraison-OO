package com.hexanome.view;

import com.hexanome.controller.UIManager;
import javafx.scene.Node;
import javafx.scene.control.Label;
import org.controlsfx.control.PopOver;
import org.controlsfx.glyphfont.Glyph;

public class WarehouseNodeView extends Label implements INodeViewShape {

    /**
     * Initializes the controller class.
     */
    public WarehouseNodeView() {
        setGraphic(new Glyph("FontAwesome", "HOME"));
    }

    @Override
    public void onMouseClickedNotify(NodeView context) {
        UIManager.getInstance().NotifyUI(ConstView.Action.CLICK_ON_WAREHOUSE, context);
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
