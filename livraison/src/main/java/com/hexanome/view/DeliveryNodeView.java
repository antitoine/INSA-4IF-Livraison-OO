package com.hexanome.view;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.UIManager;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import org.controlsfx.control.PopOver;

public class DeliveryNodeView extends Circle implements INodeViewShape {
    com.hexanome.model.Node node;

    public DeliveryNodeView(com.hexanome.model.Node node) {
        if (node.getDelivery().getTimeSlot() == null) {
            setFill(Color.RED);
        } else {
            setFill(ColorsGenerator
                    .getTimeSlotColor(node.getDelivery().getTimeSlot()));
        }
        setRadius(5.0);
        setStroke(Color.BLACK);
        setStrokeType(StrokeType.INSIDE);
        this.node = node;
    }

    @Override
    public void onMouseClickedNotify(NodeView context) {
        // 
        UIManager.getInstance().getMainWindow().getDeliveryTreeView().selectDelivery(context);
        //
        ContextManager.getInstance().getCurrentState().clickOnDelivery(node.getDelivery());
        //
        UIManager.getInstance().getMainWindow().disablePanning();
    }
    
    @Override
    public Node asSceneNode() {
        return this;
    }

    @Override
    public PopOver createPopOver(com.hexanome.model.Node node) {
        return new PopOver(new PopOverContentDelivery(node));
    }

}
