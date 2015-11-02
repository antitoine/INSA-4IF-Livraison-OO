package com.hexanome.view;

import com.hexanome.controller.UIManager;
import com.hexanome.model.Delivery;
import com.hexanome.model.Node;
import com.hexanome.utils.TypeWrapper;
import java.util.Collection;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import org.controlsfx.glyphfont.Glyph;

/**
 * FXML Controller class
 */
public class PopOverContentEmptyNode extends PopOverContent {

    Button btnValidate;
    ComboBox<String> prevDeliveryComboBox;
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

        prevDeliveryComboBox = new ComboBox<>();
        prevDeliveryComboBox.prefWidth(150);

        btnValidate = new Button(null, new Glyph("FontAwesome", "CHECK"));
        btnValidate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addDelivery();
            }
        });
        adressText = new Text();
        BorderPane.setMargin(adressText, new Insets(12, 12, 12, 12));
        BorderPane.setMargin(prevDeliveryComboBox, new Insets(12, 12, 12, 12));
        BorderPane.setMargin(btnValidate, new Insets(12, 12, 12, 12));

        setTop(adressText);

        adressText.setText("Adress : (" + node.getLocation().x + ", " + node.getLocation().y + ")");
    }

    private void addDelivery() {
        String s = prevDeliveryComboBox.getSelectionModel().getSelectedItem();
        Object[] obj = new Object[2];
        obj[0] = node;
        obj[1] = deliveryNames.get(s).getNode();
        UIManager.getInstance().NotifyUI(ConstView.Action.ADD_DELIVERY, obj);
    }

    /**
     * Add deliveries to the combox box
     *
     * @param collection
     */
    public void setComboxBox(Collection<Delivery> collection) {
        getChildren().remove(prevDeliveryComboBox);
        setCenter(prevDeliveryComboBox);
        getChildren().remove(btnValidate);
        setRight(btnValidate);
        prevDeliveryComboBox.getItems().clear();
        deliveries = collection;
        for (Delivery d : collection) {
            String start = TypeWrapper.secondsToTimestamp(d.getTimeSlot().getStartTime());
            String end = TypeWrapper.secondsToTimestamp(d.getTimeSlot().getEndTime());
            String info = "Delivery " + d.getId()
                    + " (" + start + " - " + end + ")";
            deliveryNames.put(info, d);
            prevDeliveryComboBox.getItems().add(info);
        }
    }

}
