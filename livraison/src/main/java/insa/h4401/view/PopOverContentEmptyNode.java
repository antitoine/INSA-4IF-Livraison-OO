package insa.h4401.view;

import insa.h4401.controller.ContextManager;
import insa.h4401.controller.ModelManager;
import insa.h4401.model.Delivery;
import insa.h4401.model.Node;
import insa.h4401.model.TimeSlot;
import insa.h4401.utils.TypeWrapper;
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
 * This class represents the popover content specific to an
 * empty node
 * Permits to add a delivery at the selected node
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
class PopOverContentEmptyNode extends PopOverContent {

    private Button btnValidateAddDelivery;

    private ComboBox<String> prevDeliveryComboBox;
    private HashMap<String, Node> prevDeliveryMap;

    private ComboBox<String> timeSlotsComboBox;
    private HashMap<String, TimeSlot> timeSlotsMap;

    /**
     * @param node Node as described in the model
     */
    public PopOverContentEmptyNode(Node node) {
        super(node);

        prevDeliveryMap = new HashMap<>();
        timeSlotsMap = new HashMap<>();

        initPopOverLayout();
    }

    private void initPopOverLayout() {
        initTopLayout();
        initCenterLayout();
        // Set popover content
        if (ModelManager.getInstance().getPlanning() != null) {
            setComboxBox(ModelManager.getInstance().getPlanning().getWarehouse(),
                    ModelManager.getInstance().getPlanning().getDeliveries(),
                    ModelManager.getInstance().getPlanning().getTimeSlots());
        }
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

        GridPane panelForm = new GridPane();

        panelForm.setHgap(10);
        panelForm.setVgap(10);
        panelForm.setPadding(new Insets(0, 10, 0, 10));

        panelForm.add(new Text("ID: "), 0, 0);
        panelForm.add(new Text(node.getId() + ""), 1, 0);

        Text addressText = new Text("(" + node.getLocation().x + ", " + node.getLocation().y + ")");
        panelForm.add(new Text("Address: "), 0, 1);
        panelForm.add(addressText, 1, 1);

        Text prevDeliveryTitle = new Text("Previous point: ");
        panelForm.add(prevDeliveryTitle, 0, 2);
        panelForm.add(prevDeliveryComboBox, 1, 2);

        Text timeSlotTitle = new Text("Time slot: ");
        panelForm.add(timeSlotTitle, 0, 3);
        panelForm.add(timeSlotsComboBox, 1, 3);

        initBtnAddDelivery();
        panelForm.add(btnValidateAddDelivery, 0, 4, 2, 1);
        GridPane.setHalignment(btnValidateAddDelivery, HPos.CENTER);

        setCenter(panelForm);

        BorderPane.setMargin(panelForm, new Insets(12, 12, 12, 12));
    }

    /**
     * Add warehouse and deliveries to the combox box, and the time slots in a
     * specific combobox.
     *
     * @param warehouse       The warehouse
     * @param deliveriesToAdd The collection of deliveries to add in combo box
     * @param timeSlots       The collection of time slots to add in combo box
     */
    private void setComboxBox(Node warehouse, Collection<Delivery> deliveriesToAdd, Collection<TimeSlot> timeSlots) {
        //getChildren().remove(panelForm);
        //setCenter(panelForm);

        timeSlotsComboBox.getItems().clear();
        timeSlotsMap.clear();

        prevDeliveryComboBox.getItems().clear();
        prevDeliveryMap.clear();

        prevDeliveryComboBox.getItems().add("Warehouse");
        prevDeliveryMap.put("Warehouse", warehouse);

        for (Delivery deliveryToAdd : deliveriesToAdd) {
            String start = TypeWrapper.secondsToFormatedTime(deliveryToAdd.getTimeSlot().getStartTime());
            String end = TypeWrapper.secondsToFormatedTime(deliveryToAdd.getTimeSlot().getEndTime());
            String info = "Delivery " + deliveryToAdd.getNode().getId()
                    + " (" + start + " - " + end + ")";
            prevDeliveryMap.put(info, deliveryToAdd.getNode());
            prevDeliveryComboBox.getItems().add(info);
        }

        for (TimeSlot ts : timeSlots) {
            String tsDesc = getTimeSlotComboBoxText(ts);
            timeSlotsComboBox.getItems().add(tsDesc);
            timeSlotsMap.put(tsDesc, ts);
        }

        timeSlotsComboBox.setDisable(true);
        btnValidateAddDelivery.setDisable(true);
    }

    private void initComboBoxDeliveries() {

        prevDeliveryComboBox = new ComboBox<>();
        prevDeliveryComboBox.prefWidth(150);
        prevDeliveryComboBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    Node nodePreviousDelivery = prevDeliveryMap.get(newValue);

                    if (nodePreviousDelivery != null) {
                        Delivery previousDelivery = nodePreviousDelivery.getDelivery();
                        timeSlotsComboBox.setDisable(false);
                        btnValidateAddDelivery.setDisable(false);
                        if (previousDelivery == null) {
                            timeSlotsComboBox.getSelectionModel().selectFirst();
                        } else {
                            timeSlotsComboBox.getSelectionModel()
                                    .select(
                                            getTimeSlotComboBoxText(previousDelivery.getTimeSlot())
                                    );
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
        btnValidateAddDelivery.setOnAction(event -> onBtnAddDelivery());
    }

    private String getTimeSlotComboBoxText(TimeSlot ts) {
        return TypeWrapper.secondsToFormatedTime(ts.getStartTime()) +
                " - " +
                TypeWrapper.secondsToFormatedTime(ts.getEndTime());
    }

    private void onBtnAddDelivery() {
        String selectedPreviousDelivery = prevDeliveryComboBox.getSelectionModel()
                .getSelectedItem();

        String selectedTimeSlot = timeSlotsComboBox.getSelectionModel().getSelectedItem();

        ContextManager.getInstance().getCurrentState().btnAddDelivery(node,
                prevDeliveryMap.get(selectedPreviousDelivery),
                timeSlotsMap.get(selectedTimeSlot));

    }

}
