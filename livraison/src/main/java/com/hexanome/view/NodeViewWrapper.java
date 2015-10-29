package com.hexanome.view;

import com.hexanome.model.Node;
import com.hexanome.utils.Publisher;
import com.hexanome.utils.Subscriber;

/**
 * Wrapper around a NodeView
 */
public class NodeViewWrapper implements Subscriber {

    Node node;
    NodeView nodeView;

    public NodeView getNodeView() {
        return nodeView;
    }

    public NodeViewWrapper(Node node, String initialNodeType) {
        this.node = node;
        setNodeType(initialNodeType);
    }

    /**
     * Allow to the change the node type
     *
     * @param nodeType
     */
    public void setNodeType(String nodeType) {
        switch (nodeType) {
            case ConstView.EMPTYNODE:
                nodeView = new EmptyNodeView(node.getLocation());
                break;
            case ConstView.DELIVERYNODE:
                nodeView = new DeliveryNodeView(node.getLocation());
                break;
            case ConstView.WAREHOUSENODE:
                nodeView = new WarehouseNodeView(node.getLocation());
        }
    }

    @Override
    public void update(Publisher p, Object arg) {
        // TODO
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
