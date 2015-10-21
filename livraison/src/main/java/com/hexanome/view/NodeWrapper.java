
package com.hexanome.view;

import java.util.Observable;
import java.util.Observer;

public class NodeWrapper implements Observer{

    NodeView node;
    
    public NodeWrapper() {
    }
      
    @Override
    public void update(Observable o, Object arg) {
        // TODO
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
}
