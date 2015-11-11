package insa.h4401.view;

import insa.h4401.controller.ContextManager;
import insa.h4401.controller.UIManager;
import insa.h4401.model.Delivery;
import insa.h4401.model.Planning;
import insa.h4401.model.TimeSlot;
import insa.h4401.utils.Publisher;
import insa.h4401.utils.Subscriber;
import insa.h4401.utils.TypeWrapper;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.controlsfx.glyphfont.Glyph;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Planning reprensention at the left list of deliveries
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class DeliveryTreeView extends VBox implements Subscriber {

    private static HashMap<String, Delivery> deliveresByName;
    private Boolean displaceViewtoNode;
    private TreeView<String> deliveryTree;
    private TreeItem<String> rootItem;
    private HashMap<Delivery, TreeItem<String>> deliveryBranch;

    public DeliveryTreeView() {
        BorderPane.setAlignment(this, Pos.CENTER);
        deliveryBranch = new HashMap<>();
        deliveresByName = new HashMap<>();
        deliveryTree = new TreeView<>();
        deliveryTree.setPrefHeight(1000);

        rootItem = new TreeItem<>("root");
        rootItem.setExpanded(true);
        deliveryTree.setRoot(rootItem);
        deliveryTree.setShowRoot(false);

        this.getChildren().add(deliveryTree);
        deliveryTree.setCellFactory(p -> new DeliveryTreeCell(false));

        // indicate that a delivery is selected
        displaceViewtoNode = true;
        deliveryTree.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    if (newValue != null && newValue.isLeaf()) {
                        Delivery d = getDeliveryFromTreeItem(newValue);
                        if (displaceViewtoNode) {
                            UIManager.getInstance().getMainWindow()
                                    .repositionScroller(1, new Point2D(d.getNode().getLocation().x,
                                            d.getNode().getLocation().y));
                        }
                        ContextManager.getInstance().getCurrentState()
                                .clickOnDelivery(d);
                    }
                });
    }

    /**
     * Returns a delivery using its name
     *
     * @param string delivery as displayed in the treeView
     * @return a Delivery as described in the model
     */
    static Delivery getDeliveryFromName(String string) {
        return deliveresByName.get(string);
    }

    /**
     * Return a delivery from an item in the treeView
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

    @Override
    public void update(Publisher p, Object arg) {

        if (p instanceof Planning) {
            clearTree();
            for (TimeSlot ts : ((Planning) (p)).getTimeSlots()) {
                String start = TypeWrapper.secondsToFormatedTime(ts.getStartTime());
                String end = TypeWrapper.secondsToFormatedTime(ts.getEndTime());

                TreeItem<String> tsItem;
                tsItem = makeBranch(start + " - " + end,
                        ConstView.TreeItemType.TIMESLOT, rootItem, null, null);

                List<Delivery> deliveries = ts.getDeliveries();
                Collections.sort(deliveries);

                for (Delivery d : deliveries) {
                    String deliveryName = "Delivery " + d.getNode().getId();
                    if (d.getDeliveryTime() > 0) {
                        deliveryName = TypeWrapper.secondsToFormatedTime((int) d.getDeliveryTime()) + " | " + deliveryName;
                    }

                    TreeItem<String> dItem;
                    if (d.getDeliveryTime() > 0 && d.getDeliveryTime() > d.getTimeSlot().getEndTime()) {
                        dItem = makeBranch(deliveryName, ConstView.TreeItemType.DELIVERY, tsItem, d, true);
                    } else {
                        dItem = makeBranch(deliveryName, ConstView.TreeItemType.DELIVERY, tsItem, d, false);
                    }
                    deliveryBranch.put(d, dItem);
                }
            }
        }
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
     * Create a branch in the treeView
     */
    private TreeItem<String> makeBranch(String title, ConstView.TreeItemType treeItemType,
                                        TreeItem<String> parent, Delivery d, Boolean outOfTimeSlot) {
        TreeItem<String> item = null;
        switch (treeItemType) {
            case DELIVERY:
                deliveresByName.put(title, d);
                if (outOfTimeSlot) {
                    Glyph g = new Glyph("FontAwesome", "EXCLAMATION_TRIANGLE");
                    g.setColor(Color.RED);
                    item = new TreeItem<>(title, g);
                } else {
                    item = new TreeItem<>(title, new Glyph("FontAwesome", "TRUCK"));
                }
                break;
            case TIMESLOT:
                item = new TreeItem<>(title, new Glyph("FontAwesome", "CLOCK_ALT"));
                break;
        }
        parent.getChildren().add(item);
        item.setExpanded(true);

        return item;
    }

    /**
     * Select the appropriate delevery in the TreeView
     *
     * @param nodeView NodeView, delivery on the map
     */
    public void selectDelivery(NodeView nodeView) {
        for (Map.Entry<Delivery, TreeItem<String>> entrySet : deliveryBranch.entrySet()) {
            displaceViewtoNode = false;
            if (entrySet.getKey().getNode() == nodeView.getNode()) {
                deliveryTree.getSelectionModel()
                        .clearAndSelect(deliveryTree.getRow(entrySet.getValue()));
            }
            displaceViewtoNode = true;
        }
    }

    /**
     * Allow to enable drag and drop interaction on the treeView
     *
     * @param setEnable true if you want to enable drag and drop
     */
    public void enableDragAndDrop(Boolean setEnable) {
        deliveryTree.setCellFactory(p -> new DeliveryTreeCell(setEnable));
    }


}
