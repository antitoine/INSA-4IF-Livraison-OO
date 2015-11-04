package com.hexanome.view;

import com.hexanome.model.Node;
import java.util.Objects;

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
        hash = 79 * hash + Objects.hashCode(this.node1);
        hash = 79 * hash + Objects.hashCode(this.node2);
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

        if ((this.node1 == other.node1 || this.node1 == other.node2)
                && (this.node2 == other.node1 || this.node2 == other.node2)) {
            return true;
        }
        return false;
    }

}
