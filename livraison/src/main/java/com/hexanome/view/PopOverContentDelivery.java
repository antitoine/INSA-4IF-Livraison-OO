package com.hexanome.view;

import com.hexanome.controller.UIManager;
import com.hexanome.model.Node;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.controlsfx.glyphfont.Glyph;

/**
 * 
 */
public class PopOverContentDelivery extends PopOverContent {

    @FXML
    Button btnDelete;

    @FXML
    Text adressText;

    /**
     * 
     * @param node 
     */
    public PopOverContentDelivery(Node node) {
        super(ConstView.POPOVERDELIVERY);
        adressText.setText("Adress : (" + node.getLocation().x +
                ", " + node.getLocation().y + ")");
    }

    @FXML
    private void delete() {
        UIManager.getInstance().NotifyUI(ConstView.Action.DELETE_DELIVERY);
    }

}
