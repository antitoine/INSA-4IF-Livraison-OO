package insa.h4401.view;

import insa.h4401.model.Node;
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