package com.hexanome.view;

import com.hexanome.model.Node;
import javafx.scene.layout.BorderPane;

/**
 * This class represents a generic popover content
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
abstract class PopOverContent extends BorderPane {

    Node node;

    PopOverContent(Node node) {
        this.node = node;
    }

}
