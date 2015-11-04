package com.hexanome.view;

import com.hexanome.controller.UIManager;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import org.controlsfx.control.PopOver;

public class EmptyNodeView extends Circle implements INodeViewShape {

    
    public EmptyNodeView() {
        setFill(Color.web("9e9e9e"));
        setRadius(5.0);
        setStroke(Color.BLACK);
        setStrokeType(StrokeType.INSIDE);
    }

    @Override
    public void onMouseClickedNotify(NodeView context) {
        UIManager.getInstance().NotifyUI(ConstView.Action.CLICK_ON_EMPTY_NODE, context);
    }

    @Override
    public Node asSceneNode() {
        return this;
    }

    @Override
    public PopOver createPopOver(com.hexanome.model.Node node) {
        return new PopOver(new PopOverContentEmptyNode(node));
    }
    

}
