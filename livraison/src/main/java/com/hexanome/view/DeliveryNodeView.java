package com.hexanome.view;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.UIManager;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import org.controlsfx.control.PopOver;

/**
 * This class is the graphic component equivalent of the model delivery
 * 
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class DeliveryNodeView extends Circle implements INodeViewShape {
    private com.hexanome.model.Node node;

    public DeliveryNodeView(com.hexanome.model.Node node) {
        if (node.getDelivery().getTimeSlot() == null ||
                node.getDelivery().getDeliveryTime() > node.getDelivery().getTimeSlot().getEndTime()) {
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

    /**
     * Handler for mouse click events
     * @param context 
     */
    @Override
    public void onMouseClickedNotify(NodeView context) {
        // 
        UIManager.getInstance().getMainWindow().getDeliveryTreeView().selectDelivery(context);
        //
        ContextManager.getInstance().getCurrentState().clickOnDelivery(node.getDelivery());
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
