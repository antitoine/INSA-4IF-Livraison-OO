package com.hexanome.view;

import com.hexanome.controller.UIManager;
import com.hexanome.model.Node;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import org.controlsfx.glyphfont.Glyph;

/**
 * FXML Controller class
 */
public class PopOverContentEmptyNode extends PopOverContent {

    @FXML
    Button btnValidate;

    @FXML
    ComboBox<String> prevDeliveryComboBox;

    @FXML
    Text adressText;

    /**
     * 
     * @param node Node as described in the model 
     */
    public PopOverContentEmptyNode(Node node) {
        super(ConstView.POPOVEREMPTY);
        btnValidate.setGraphic(new Glyph("FontAwesome", "CHECK"));
        prevDeliveryComboBox.getItems().addAll("livraison 1", "livraison 2");
        adressText.setText(node.toString());
    }
    
    @FXML
    private void addDelivery() {
        UIManager.getInstance().NotifyUI(ConstView.Action.ADD_DELIVERY);
    }
    
    

}
