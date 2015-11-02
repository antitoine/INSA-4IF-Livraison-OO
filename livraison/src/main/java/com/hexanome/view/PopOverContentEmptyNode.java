package com.hexanome.view;

import com.hexanome.controller.UIManager;
import com.hexanome.model.Delivery;
import com.hexanome.model.Node;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

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

    Collection<Delivery> deliveries;
    HashMap<String, Delivery> deliveryNames;

    /**
     *
     * @param node Node as described in the model
     */
    public PopOverContentEmptyNode(Node node) {
        super(ConstView.POPOVEREMPTY, node);
        deliveryNames = new HashMap<>();
        adressText.setText("Adress : (" + node.getLocation().x + ", " + node.getLocation().y + ")");
    }

    @FXML
    private void addDelivery() {
        String s = prevDeliveryComboBox.getSelectionModel().getSelectedItem();
        Object[] obj = new Object[2];
        obj[0] = node;
        obj[1] = deliveryNames.get(s);
        UIManager.getInstance().NotifyUI(ConstView.Action.ADD_DELIVERY, obj);
    }

    /**
     * Add deliveries to the combox box
     *
     * @param collection
     */
    public void setComboxBox(Collection<Delivery> collection) {
        prevDeliveryComboBox.getItems().clear();
        deliveries = collection;
        for (Delivery d : collection) {
            String info = "Delivery " + d.getId()
                    + " (" + d.getTimeSlot().getStartTime() + " - "
                    + d.getTimeSlot().getEndTime() + ")";
            deliveryNames.put(info, d);
            prevDeliveryComboBox.getItems().add(info);
        }
    }

}
