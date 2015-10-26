package com.hexanome.view;

import com.hexanome.model.Delivery;
import com.hexanome.model.TimeSlot;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 */
public class DeliveryTreeView extends AnchorPane implements Initializable {

    TreeView<String> deliveryTree;

    TreeItem<String> rootItem;

    @FXML
    AnchorPane treePane;

    HashMap<Integer, TreeItem<String>> timeSlotBranch;
    HashMap<Integer, TreeItem<String>> deliveryBranch;

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

        treePane.getChildren().add(deliveryTree);

        deliveryTree.getSelectionModel().selectedItemProperty().
                addListener(new ChangeListener<TreeItem<String>>() {
                    @Override
                    public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue, TreeItem<String> newValue) {
                    }
                });

    }

    public void AddTimeSlot(TimeSlot ts) {
        String info = ts.getStartTime() + " - " + ts.getEndTime();
        timeSlotBranch.put(ts.getStartTime(), makeBranch(info, rootItem));
    }

    public void AddDelivery(Delivery delivery, int parentId) {
        deliveryBranch.put(delivery.getNode().getId(), makeBranch("livraison " + delivery.getNode().getId(),
                timeSlotBranch.get(parentId)));
    }

    public void DeleteDelivery() {

    }

    public void SwapDeliveries() {

    }

    private TreeItem<String> makeBranch(String title, TreeItem<String> parent) {
        TreeItem<String> item = new TreeItem<>(title);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;
    }

}
