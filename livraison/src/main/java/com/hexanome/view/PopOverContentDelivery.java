package com.hexanome.view;

import com.hexanome.controller.ContextManager;
import com.hexanome.model.Node;
import com.hexanome.utils.TypeWrapper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import org.controlsfx.glyphfont.Glyph;

/**
 * This class represents the popover content specific to a
 * delivery node
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
class PopOverContentDelivery extends PopOverContent {

    private Button btnDelete;
    private Text adressText;

    /**
     * @param node Node for the PopOver
     */
    public PopOverContentDelivery(Node node) {
        super(node);
        adressText = new Text();
        btnDelete = new Button("  Delete\n Delivery", new Glyph("FontAwesome", "TRASH"));
        BorderPane.setMargin(adressText, new Insets(12, 12, 12, 12));
        BorderPane.setMargin(btnDelete, new Insets(12, 12, 12, 12));
        BorderPane.setAlignment(btnDelete, Pos.CENTER);
        setRight(btnDelete);
        setCenter(adressText);

        adressText.setText("ID: " + node.getId() + "\nAdress : (" + node.getLocation().x
                + ", " + node.getLocation().y + ")");

        btnDelete.setOnAction(event -> delete());

        if (node.getDelivery() != null && node.getDelivery().getDeliveryTime() != 0) {
            adressText.setText(adressText.getText() + "\n"
                    + "Delivery Time : "
                    + TypeWrapper.secondsToTimestamp((int) node.getDelivery().getDeliveryTime()));
        }
    }

    private void delete() {
        ContextManager.getInstance().getCurrentState()
                .btnRemoveDelivery(node.getDelivery());
    }

}
