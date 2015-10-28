package com.hexanome.view;

import com.hexanome.controller.UIManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    ComboBox prevDeliveryComboBox;

    @FXML
    Text adressText;

    public PopOverContentEmptyNode(String adress) {

        super(ConstView.POPOVEREMPTY);

        btnValidate.setGraphic(new Glyph("FontAwesome", "CHECK"));
        btnValidate.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                UIManager.getInstance().NotifyUI(ConstView.Action.ADD_DELIVERY, adress);
            }
        });
        prevDeliveryComboBox.getItems().addAll("livraison 1", "livraison 2");
        // adressText.setText(adress);
    }

}
