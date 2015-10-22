
package com.hexanome.view;

import java.util.Observable;
import java.util.Observer;

public class NodeWrapper implements Observer{

    NodeView node;

    public NodeWrapper(NodeView n) {
        node = n;
    }
    
    /**
     *
     * @param node
     */    
    public void setNode(NodeView node) {
        this.node = node;
    }
    
    
    @Override
    public void update(Observable o, Object arg) {
        // TODO
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
}
