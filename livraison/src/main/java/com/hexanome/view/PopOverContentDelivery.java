package com.hexanome.view;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.UIManager;
import com.hexanome.model.Node;
import com.hexanome.utils.TypeWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import org.controlsfx.glyphfont.Glyph;

/**
 *
 */
public class PopOverContentDelivery extends PopOverContent {

    Button btnDelete;
    Text adressText;

    /**
     *
     * @param node
     */
    public PopOverContentDelivery(Node node) {
        super(ConstView.POPOVERDELIVERY, node);
        adressText = new Text();
        btnDelete = new Button("  Delete\n Delivery", new Glyph("FontAwesome", "TRASH"));
        BorderPane.setMargin(adressText, new Insets(12, 12, 12, 12));
        BorderPane.setMargin(btnDelete, new Insets(12, 12, 12, 12));
        BorderPane.setAlignment(btnDelete, Pos.CENTER);
        setRight(btnDelete);
        setCenter(adressText);

        adressText.setText("Adress : (" + node.getLocation().x
                + ", " + node.getLocation().y + ")");

        btnDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                delete();
            }
        });

        if (node.getDelivery() != null && node.getDelivery().getDeliveryTime() != 0) {
            adressText.setText(adressText.getText() + "\n"
                    + "Delivery Time : " 
                    + TypeWrapper.secondsToTimestamp((int) node.getDelivery().getDeliveryTime()));
        }
    }

    private void delete() {
        ContextManager.getInstance().getCurrentState().btnRemoveDelivery(node.getDelivery());
    }

}
