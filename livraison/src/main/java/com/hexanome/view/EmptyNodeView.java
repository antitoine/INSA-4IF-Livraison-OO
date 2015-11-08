package com.hexanome.view;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.ModelManager;
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

    /**
     * Handler for mouse click events
     * @param context 
     */
    @Override
    public void onMouseClickedNotify(NodeView context) {
        // Set popover content
        if (ModelManager.getInstance().getPlanning() != null) {
            PopOverContentEmptyNode pop = (PopOverContentEmptyNode) context.getPopoverContent();
            pop.setComboxBox(ModelManager.getInstance().getPlanning().getWarehouse(),
                             ModelManager.getInstance().getPlanning().getDeliveries(),
                             ModelManager.getInstance().getPlanning().getTimeSlots());
        }
        // Call current state related action
        ContextManager.getInstance().getCurrentState().clickOnEmptyNode(node);
    }

    @Override
    public Node asSceneNode() {
        return this;
    }

    /**
     * Creates popover content for an EmptyNode
     * @param node
     * @return 
     */
    @Override
    public PopOver createPopOver(com.hexanome.model.Node node) {
        return new PopOver(new PopOverContentEmptyNode(node));
    }
    

}
