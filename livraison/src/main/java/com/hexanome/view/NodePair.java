package com.hexanome.view;

import com.hexanome.model.Node;
import java.util.Objects;

/**
 * This class represents a pair of nodes
 * 
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class NodePair {

    Node node1;
    Node node2;

    public NodePair(Node node1, Node node2) {
        this.node1 = node1;
        this.node2 = node2;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.node1) +
               79 * hash + Objects.hashCode(this.node2);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NodePair other = (NodePair) obj;

       return (this.node1 == other.node1 && this.node2 == other.node2)
               || (this.node1 == other.node2 && this.node2 == other.node1);
    }

}
