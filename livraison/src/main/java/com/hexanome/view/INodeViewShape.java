/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.view;

import javafx.scene.Node;
import org.controlsfx.control.PopOver;

/**
 *
 * @author pierre
 */
public interface INodeViewShape {
    
    /**
     * Notify the controller of a click on the node view shape.
     * @param context The parent context of the node view shape.
     */
    public void onMouseClickedNotify(NodeView context);
    
    /**
     * Create the pop over to associate with the node view shape and the node
     * passed by parameter.
     * @param node The model node to represent by the popover.
     * @return The created PopOver object.
     */
    public PopOver createPopOver(com.hexanome.model.Node node);
    
    /**
     * Cast the object to a javafx.scene.Node object.
     * @return The same object, as javafx.scene.Node.
     */
    public Node asSceneNode();
}
