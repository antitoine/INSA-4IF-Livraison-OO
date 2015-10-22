package com.hexanome.view;

import com.hexanome.model.Delivery;
import com.hexanome.model.TimeSlot;
import java.awt.Point;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 */
public class DeliveryTreeView implements Initializable {

    static TreeView<String> deliveryTree;
    
    static TreeItem<String> rootItem;
    
    @FXML
    AnchorPane treePane;
    
    static HashMap<Integer, TreeItem<String>> timeSlotBranch;
    static HashMap<Integer, TreeItem<String>> deliveryBranch;


    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        deliveryTree = new TreeView<String>();
        
        rootItem = new TreeItem<>();
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
    
    public static void AddTimeSlot(TimeSlot ts){
        String info = ts.getStartTime() + " - " + ts.getEndTime();
//        timeSlotBranch.put(ts.getStartTime(), makeBranch(info, rootItem));
    }

    public static void AddDelivery(Delivery delivery) {
//        deliveryBranch.put(delivery.getId(), makeBranch("livraison ", rootItem));
    }

    public static void DeleteDelivery() {

    }

    public static void SwapDeliveries() {

    }

    private static TreeItem<String> makeBranch(String title, TreeItem<String> parent) {
        TreeItem<String> item = new TreeItem<>(title);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;
    }

}
