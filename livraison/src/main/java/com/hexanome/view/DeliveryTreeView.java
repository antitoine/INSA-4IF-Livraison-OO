package com.hexanome.view;

import com.hexanome.controller.UIManager;
import com.hexanome.model.Delivery;
import com.hexanome.model.Planning;
import com.hexanome.model.TimeSlot;
import com.hexanome.utils.Publisher;
import com.hexanome.utils.Subscriber;
import com.hexanome.utils.TypeWrapper;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.controlsfx.glyphfont.Glyph;

/**
 * Planning reprensention at the left list of deliveries
 */
public class DeliveryTreeView extends VBox implements Subscriber {

    TreeView<String> deliveryTree;
    TreeItem<String> rootItem;

    HashMap<TimeSlot, TreeItem<String>> timeSlotBranch;
    HashMap<TimeSlot, Integer> timeSlotRow;

    HashMap<Delivery, TreeItem<String>> deliveryBranch;

    public DeliveryTreeView() {
        BorderPane.setAlignment(this, Pos.CENTER);
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
                            UIManager.getInstance().getMainWindow()
                                    .getMapView().selectDelivery(getDeliveryFromTreeItem(newValue));
                        }
                    }
                });

    }

    /**
     * Add a timeSlot in the TreeView
     *
     * @param ts TimeSlot
     */
    public void AddTimeSlot(TimeSlot ts) {
        String info = ts.getStartTime() + " - " + ts.getEndTime();
        timeSlotBranch.put(ts, makeBranch(info, ConstView.TreeItemType.TIMESLOT, rootItem));
    }

    /**
     * Delete a delivery in the TreeView
     *
     * @param delivery
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
     * Return a delivery from an item in the treeView
     *
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
            int nbTimeSlots = 0;
            for (TimeSlot ts : ((Planning) (p)).getTimeSlots()) {
                nbTimeSlots++;
                String start = TypeWrapper.secondsToTimestamp(ts.getStartTime());
                String end = TypeWrapper.secondsToTimestamp(ts.getEndTime());

                TreeItem<String> tsItem = null;
                tsItem = makeBranch(start + " - " + end,
                        ConstView.TreeItemType.TIMESLOT, rootItem);
                timeSlotBranch.put(ts, tsItem);
                for (Delivery d : ts.getDeliveries()) {
                    TreeItem<String> dItem = makeBranch("Delivery " + d.getId() +" - "+nbTimeSlots,
                            ConstView.TreeItemType.DELIVERY, tsItem);
                    deliveryBranch.put(d, dItem);
                }
            }
        }
    }

    /**
     * Select the appropriate delevery in the TreeView
     *
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
