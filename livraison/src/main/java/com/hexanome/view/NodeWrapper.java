package com.hexanome.view;

import com.hexanome.model.Node;
import java.util.Observable;
import java.util.Observer;

/**
 * Wrapper around a Node
 */
public class NodeWrapper implements Observer {

    Node node;
    NodeView nodeView;

    public NodeWrapper(Node node, String initialNodeType) {
        this.node = node;
    }

    @Override
    public void update(Observable o, Object arg) {
        // TODO
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @param nodeView
     */
    public void setNode(NodeView nodeView) {
        this.nodeView = nodeView;
    }

    /**
     * Allow to the change the node type
     *
     * @param nodeType
     */
    private void changeNodeType(String nodeType) {
        switch (nodeType) {
            case ConstView.EMPTYNODE:
                break;
            case ConstView.DELIVERYNODE:
                break;
        }
    }

}
