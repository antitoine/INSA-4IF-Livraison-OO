package com.hexanome.view;

import com.hexanome.controller.ContextManager;
import com.hexanome.model.Delivery;
import com.hexanome.model.Planning;
import com.hexanome.model.TimeSlot;
import com.hexanome.utils.Publisher;
import com.hexanome.utils.Subscriber;
import com.hexanome.utils.TypeWrapper;
import javafx.geometry.Pos;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.controlsfx.glyphfont.Glyph;

import java.util.HashMap;
import java.util.Map;

/**
 * Planning reprensention at the left list of deliveries
 * 
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class DeliveryTreeView extends VBox implements Subscriber {

    private static HashMap<String, Delivery> deliveresByName;
    private TreeView<String> deliveryTree;
    private TreeItem<String> rootItem;
    private HashMap<Delivery, TreeItem<String>> deliveryBranch;

    public DeliveryTreeView() {
        BorderPane.setAlignment(this, Pos.CENTER);
        deliveryBranch = new HashMap<>();
        deliveresByName = new HashMap<>();

        deliveryTree = new TreeView<>();

        rootItem = new TreeItem<>("root");
        rootItem.setExpanded(true);

        deliveryTree.setRoot(rootItem);
        deliveryTree.setShowRoot(false);

        this.getChildren().add(deliveryTree);

        deliveryTree.setPrefHeight(1000);

        deliveryTree.setCellFactory(p -> new DeliveryTreeCell());

        // indicate that a delivery is selected
        deliveryTree.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    if (newValue != null && newValue.isLeaf()) {
                        ContextManager.getInstance().getCurrentState()
                                .clickOnDelivery(getDeliveryFromTreeItem(newValue));

                    }
                });
    }

    /**
     * Returns a delivery using its name
     * @param string
     * @return 
     */
    public static Delivery getDeliveryFromName(String string) {
        return deliveresByName.get(string);
    }

    /**
     * Remove all the items in the TreeView
     */
    public void clearTree() {
        rootItem.getChildren().clear();
        deliveryBranch.clear();
        deliveresByName.clear();
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
                                        TreeItem<String> parent, Delivery d) {
        TreeItem<String> item = null;
        switch (treeItemType) {
            case DELIVERY:
                deliveresByName.put(title, d);
                item = new TreeItem<>(title, new Glyph("FontAwesome", "TRUCK"));
                break;
            case TIMESLOT:
                item = new TreeItem<>(title, new Glyph("FontAwesome", "CLOCK_ALT"));
                break;
        }
        parent.getChildren().add(item);
        item.setExpanded(true);

        return item;
    }

    @Override
    public void update(Publisher p, Object arg) {

        if (p instanceof Planning) {
            clearTree();
            for (TimeSlot ts : ((Planning) (p)).getTimeSlots()) {
                String start = TypeWrapper.secondsToTimestamp(ts.getStartTime());
                String end = TypeWrapper.secondsToTimestamp(ts.getEndTime());

                TreeItem<String> tsItem;
                tsItem = makeBranch(start + " - " + end,
                        ConstView.TreeItemType.TIMESLOT, rootItem, null);

                for (Delivery d : ts.getDeliveries()) {
                    TreeItem<String> dItem = makeBranch("Delivery " +
                                    "" + d.getNode().getId() + " (" +
                                    d.getNode().getLocation().x + ", "
                                    + d.getNode().getLocation().y + ")",
                            ConstView.TreeItemType.DELIVERY, tsItem, d);
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
