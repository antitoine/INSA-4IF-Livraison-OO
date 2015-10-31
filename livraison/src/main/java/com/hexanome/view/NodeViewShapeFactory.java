/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.view;

/**
 * Simple factory of node view shape objects, regarding the INodeViewShape interface.
 * @author Pierre Jarsaillon
 */
public abstract class NodeViewShapeFactory {
    
    /**
     * Creates a node view shape object that matches with the node type passed
     * by parameter. 
     * @param nodeType The type of the node to create, defined in ConstView.
     * @return The node view shape newly created, or null if the type is undefined.
     */
    public static INodeViewShape createNodeViewShape(String nodeType) {
        switch (nodeType) {
            case ConstView.EMPTYNODE:
                return new EmptyNodeView();
                
            case ConstView.DELIVERYNODE:
                return new DeliveryNodeView();
                
            case ConstView.WAREHOUSENODE:
                return new WarehouseNodeView();
                
            default:
                return null;
        }
    }
} 
