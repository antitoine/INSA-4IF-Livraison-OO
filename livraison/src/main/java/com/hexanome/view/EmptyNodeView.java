package com.hexanome.view;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.ModelManager;
import com.hexanome.controller.UIManager;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import org.controlsfx.control.PopOver;

/**
 * This class is the graphic component equivalent of the model node
 * 
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class EmptyNodeView extends Circle implements INodeViewShape {
    com.hexanome.model.Node node;
    
    public EmptyNodeView(com.hexanome.model.Node node) {
        setFill(Color.web("9e9e9e"));
        setRadius(5.0);
        setStroke(Color.BLACK);
        setStrokeType(StrokeType.INSIDE);
        this.node = node;

    }

    @Override
    public void onMouseClickedNotify(NodeView context) {
        ContextManager.getInstance().getCurrentState().clickOnEmptyNode(node);
        if (ModelManager.getInstance().getPlanning() != null) {
            PopOverContentEmptyNode pop = (PopOverContentEmptyNode) context.getPopoverContent();
            pop.setComboxBox(ModelManager.getInstance().getPlanning().getWarehouse(),
                             ModelManager.getInstance().getPlanning().getDeliveries(),
                             ModelManager.getInstance().getPlanning().getTimeSlots());
        }
        UIManager.getInstance().getMainWindow().disablePanning();
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
