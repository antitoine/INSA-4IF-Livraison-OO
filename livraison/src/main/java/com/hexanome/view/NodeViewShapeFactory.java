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
            case ConstView.EMPTY_NODE:
                return new EmptyNodeView();
                
            case ConstView.DELIVERY_NODE:
                return new DeliveryNodeView();
                
            case ConstView.WAREHOUSE_NODE:
                return new WarehouseNodeView();
                
            default:
                return null;
        }
    }
} 
