package com.hexanome.view;

import com.hexanome.controller.UIManager;
import com.hexanome.model.Delivery;
import com.hexanome.model.Planning;
import com.hexanome.model.TimeSlot;
import com.hexanome.utils.Publisher;
import com.hexanome.utils.Subscriber;
import com.hexanome.utils.TypeWrapper;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.controlsfx.glyphfont.Glyph;

/**
 * FXML Controller class
 */
public class DeliveryTreeView extends VBox implements Initializable, Subscriber {

    TreeView<String> deliveryTree;
    TreeItem<String> rootItem;

    HashMap<TimeSlot, TreeItem<String>> timeSlotBranch;
    HashMap<TimeSlot, Integer> timeSlotRow;

    HashMap<Delivery, TreeItem<String>> deliveryBranch;

    public DeliveryTreeView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ConstView.TREEVIEW));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(EmptyNodeView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        deliveryBranch = new HashMap<>();
        timeSlotBranch = new HashMap<>();

        deliveryTree = new TreeView<>();

        rootItem = new TreeItem<>("root");
        rootItem.setExpanded(true);

        deliveryTree.setRoot(rootItem);
        deliveryTree.setShowRoot(false);

        this.getChildren().add(deliveryTree);

        deliveryTree.setPrefHeight(1000);

        deliveryTree.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
            @Override
            public TreeCell<String> call(TreeView<String> p) {
                return new DeliveryTreeCell();
            }
        });

        // Ask the map to show a popover over the selected delivery
        deliveryTree.getSelectionModel().selectedItemProperty().
                addListener(new ChangeListener<TreeItem<String>>() {
                    @Override
                    public void changed(ObservableValue<? extends TreeItem<String>> observable,
                            TreeItem<String> oldValue, TreeItem<String> newValue) {
                        if (newValue.isLeaf()) {
                            UIManager.getInstance().NotifyUI(ConstView.Action.DELEVERY_SELECTED,
                                    getDeliveryFromTreeItem(newValue));
                        }
                    }
                });
    }

    /**
     * Add a timeSlot in the TreeView
     * @param ts TimeSlot
     */
    public void AddTimeSlot(TimeSlot ts) {
        String info = ts.getStartTime() + " - " + ts.getEndTime();
        timeSlotBranch.put(ts, makeBranch(info, ConstView.TreeItemType.TIMESLOT, rootItem));
    }

    /**
     * Add a delivery in the TreeView
     * @param delivery delivery
     * @param parentId TimeSlot for the delivery
     */
    public void AddDelivery(Delivery delivery, int parentId) {
        deliveryBranch.put(delivery, makeBranch("Delivery " + delivery.getNode().getId(),
                ConstView.TreeItemType.DELIVERY, timeSlotBranch.get(parentId)));
    }

    /**
     * Delete a delivery in the TreeView
     */
    public void DeleteDelivery(Delivery delivery) {
        rootItem.getChildren().remove(deliveryBranch.get(delivery));
        deliveryBranch.remove(delivery);
    }

    /**
     * Remove all the items in the TreeView
     */
    public void clearTree() {
        rootItem.getChildren().clear();
        deliveryBranch.clear();
        timeSlotBranch.clear();
    }
    
    /**
     * Return a delivery from an item 
     * in the treeView
     * @param treeItem
     * @return 
     */
    private Delivery getDeliveryFromTreeItem(TreeItem<String> treeItem) {
        Delivery result = null;
        for (Map.Entry<Delivery, TreeItem<String>> entrySet : deliveryBranch.entrySet()) {
            if (entrySet.getValue() == treeItem) {
                result = entrySet.getKey();
                break;
            }
        }
        return result;
    }

    /**
     * Create a branch in the treeView
     *
     * @param title
     * @param treeItemType
     * @param parent
     * @return
     */
    private TreeItem<String> makeBranch(String title, ConstView.TreeItemType treeItemType,
            TreeItem<String> parent) {
        TreeItem<String> item = null;
        switch (treeItemType) {
            case DELIVERY:
                item = new TreeItem<>(title, new Glyph("FontAwesome", "TRUCK"));
                break;
            case TIMESLOT:
                item = new TreeItem<>(title, new Glyph("FontAwesome", "CLOCK_ALT"));
                break;
        }
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;
    }

    @Override
    public void update(Publisher p, Object arg) {
        clearTree();
        if (p instanceof Planning) {
            for (TimeSlot ts : ((Planning) (p)).getTimeSlots()) {
                String start = TypeWrapper.secondsToTimestamp(ts.getStartTime());
                String end = TypeWrapper.secondsToTimestamp(ts.getEndTime());

                TreeItem<String> tsItem = null;
                tsItem = makeBranch(start + " - " + end,
                        ConstView.TreeItemType.TIMESLOT, rootItem);
                timeSlotBranch.put(ts, tsItem);
                for (Delivery d : ts.getDeliveries()) {
                    TreeItem<String> dItem = makeBranch("Delivery " + d.getId(),
                            ConstView.TreeItemType.DELIVERY, tsItem);
                    deliveryBranch.put(d, dItem);
                }
            }
        }
    }

    /**
     * Select the appropriate delevery in the 
     * TreeView
     * @param nodeView NodeView, delivery on the map
     */
    public void selectDelivery(NodeView nodeView) {
        for (Map.Entry<Delivery, TreeItem<String>> entrySet : deliveryBranch.entrySet()) {
            if (entrySet.getKey().getNode() == nodeView.getNode()) {
                deliveryTree.getSelectionModel()
                        .clearAndSelect(deliveryTree.getRow(entrySet.getValue()));
                break;
            }
        }
    }
}
