package com.hexanome.view;

import com.hexanome.controller.ContextManager;
import com.hexanome.controller.UIManager;
import com.hexanome.model.Delivery;
import com.hexanome.model.Node;
import com.hexanome.model.TimeSlot;
import com.hexanome.utils.TypeWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.controlsfx.glyphfont.Glyph;

import java.util.Collection;
import java.util.HashMap;

/**
 * This class represent the popover content specific to an 
 * empty node
 * Permits to add a delivery at the selected node
 * 
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class PopOverContentEmptyNode extends PopOverContent {

    Button btnValidateAddDelivery;
    
    Text addressText;
    
    ComboBox<String> prevDeliveryComboBox;
    HashMap<String, Node> prevDeliveryMap;
    
    ComboBox<String> timeSlotsComboBox;
    HashMap<String, TimeSlot> timeSlotsMap;
    
    GridPane panelForm;

    /**
     *
     * @param node Node as described in the model
     */
    public PopOverContentEmptyNode(Node node) {
        super(ConstView.POPOVEREMPTY, node);
        
        prevDeliveryMap = new HashMap<>();
        timeSlotsMap = new HashMap<>();

        initPopOverLayout();       
    }
    
    private String getTimeSlotComboBoxText(TimeSlot ts) {
        return TypeWrapper.secondsToTimestamp(ts.getStartTime()) +
               " - " +
               TypeWrapper.secondsToTimestamp(ts.getEndTime());
    }

    private void onBtnAddDelivery() {
       String selectedPreviousDelivery = prevDeliveryComboBox.getSelectionModel()
                                                              .getSelectedItem();

        String selectedTimeSlot = timeSlotsComboBox.getSelectionModel().getSelectedItem();

        ContextManager.getInstance().getCurrentState().btnAddDelivery(node,
               prevDeliveryMap.get(selectedPreviousDelivery), 
               timeSlotsMap.get(selectedTimeSlot));

    }

    /**
     * Add warehouse and deliveries to the combox box, and the time slots in a
     * specific combobox.
     *
     * @param warehouse The warehouse
     * @param deliveriesToAdd The collection of deliveries to add in combo box
     * @param timeSlots The collection of time slots to add in combo box
     */
    public void setComboxBox(Node warehouse, Collection<Delivery> deliveriesToAdd, Collection<TimeSlot> timeSlots) {
        //getChildren().remove(panelForm);
        //setCenter(panelForm);
        
        timeSlotsComboBox.getItems().clear();
        timeSlotsMap.clear();
        
        prevDeliveryComboBox.getItems().clear();
        prevDeliveryMap.clear();
        
        prevDeliveryComboBox.getItems().add("Warehouse");
        prevDeliveryMap.put("Warehouse", warehouse);
        
        for (Delivery deliveryToAdd : deliveriesToAdd) {
            String start = TypeWrapper.secondsToTimestamp(deliveryToAdd.getTimeSlot().getStartTime());
            String end = TypeWrapper.secondsToTimestamp(deliveryToAdd.getTimeSlot().getEndTime());
            String info = "Delivery " + deliveryToAdd.getId()
                    + " (" + start + " - " + end + ")";
            prevDeliveryMap.put(info, deliveryToAdd.getNode());
            prevDeliveryComboBox.getItems().add(info);
        }
        
        for (TimeSlot ts : timeSlots) {
            String tsDesc = getTimeSlotComboBoxText(ts);
            timeSlotsComboBox.getItems().add(tsDesc);
            timeSlotsMap.put(tsDesc, ts);
        }
    }

    private void initComboBoxDeliveries() {
        prevDeliveryComboBox = new ComboBox<>();
        prevDeliveryComboBox.prefWidth(150);
        prevDeliveryComboBox.getSelectionModel()
                            .selectedItemProperty()
                            .addListener(new ChangeListener<String>() {

            /**
             * Update the time slot combobox when a preivous delivery is selected
             */
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Node nodePreviousDelivery = prevDeliveryMap.get(newValue);
                
                if (nodePreviousDelivery != null) {
                    Delivery previousDelivery = nodePreviousDelivery.getDelivery();
                    
                    if (previousDelivery == null) {
                        timeSlotsComboBox.getSelectionModel().selectFirst();
                    } else {
                        timeSlotsComboBox.getSelectionModel()
                                         .select(
                                            getTimeSlotComboBoxText(previousDelivery.getTimeSlot())
                                         );
                    }
                } 
            }
        });
    }

    private void initComboBoxTimeSlots() {
        timeSlotsComboBox = new ComboBox<>();
        timeSlotsComboBox.prefWidth(100);
    }
    
    private void initBtnAddDelivery() {
        btnValidateAddDelivery = new Button("Add delivery", new Glyph("FontAwesome", "PLUS"));
        btnValidateAddDelivery.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                onBtnAddDelivery();
            }
        });
    }

    private void initPopOverLayout() {
        initTopLayout();
        initCenterLayout();

    }

    private void initTopLayout() {
        HBox topLayout = new HBox();
        
        Text popOverTitle = new Text("Available address");
        topLayout.getChildren().add(popOverTitle);
        topLayout.setAlignment(Pos.CENTER);
        
        setTop(topLayout);
        
        BorderPane.setMargin(topLayout, new Insets(12, 12, 12, 12));
    }
    
    private void initCenterLayout() {
        initComboBoxDeliveries();
        initComboBoxTimeSlots();
        
        panelForm = new GridPane();
        
        panelForm.setHgap(10);
        panelForm.setVgap(10);
        panelForm.setPadding(new Insets(0, 10, 0, 10));
        
        addressText = new Text();
        addressText.setText("(" + node.getLocation().x + ", " + node.getLocation().y + ")");
        panelForm.add(new Text("Address: "), 0, 0);
        panelForm.add(addressText, 1, 0);
        
        Text prevDeliveryTitle = new Text("Previous point: ");
        panelForm.add(prevDeliveryTitle, 0, 1);
        panelForm.add(prevDeliveryComboBox, 1, 1);
        
        Text timeSlotTitle = new Text("Time slot: ");
        panelForm.add(timeSlotTitle, 0, 2);
        panelForm.add(timeSlotsComboBox, 1, 2);
        
        initBtnAddDelivery();
        panelForm.add(btnValidateAddDelivery, 0, 3, 2, 1);
        GridPane.setHalignment(btnValidateAddDelivery, HPos.CENTER);
        
        setCenter(panelForm);

        BorderPane.setMargin(panelForm, new Insets(12, 12, 12, 12));
    }

}
