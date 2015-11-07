package com.hexanome.view;

import com.hexanome.model.Node;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
/**
 * This class represents the popover content specific to the
 * warehouse node
 * 
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class PopOverContentWarehouse extends PopOverContent {

    public PopOverContentWarehouse(Node node) {
        super(ConstView.POPOVERWAREHOUSE, node);

        Text adressText = new Text("Warehouse");
        BorderPane.setMargin(adressText, new Insets(12, 12, 12, 12));
        setCenter(adressText);
    }

}
