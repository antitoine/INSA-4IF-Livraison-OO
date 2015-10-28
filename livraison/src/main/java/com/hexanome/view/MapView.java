package com.hexanome.view;

import com.hexanome.model.Arc;
import com.hexanome.model.Map;
import com.hexanome.model.Node;
import com.hexanome.utils.Publisher;
import com.hexanome.utils.Subscriber;
import java.awt.Point;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * Reprensents a view of the map with nodes and arc
 *
 * This is a one-to-one representation of the Map model
 */
public class MapView extends AnchorPane implements Subscriber, Initializable {

    static HashMap<Node, NodeView> nodeList;
    static HashMap<Point, ArcView> arcList;

    /**
     * Initializes the controller class.
     */
    public void MapView() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ConstView.MAPVIEW));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(EmptyNodeView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Add a node a the position pt.x & pt.y
     *
     * @param Type Node Type
     * @param node node as describes in the model
     */
    public void AddNode(String Type, Node node) {

        MapView mv = this;

        NodeView nodeview = null;

        switch (Type) {
            case ConstView.EMPTYNODE:
                nodeview = new EmptyNodeView(node.getLocation());
                break;
            case ConstView.DELIVERYNODE:
                nodeview = new DeliveryNodeView(node.getLocation());
                break;
            case ConstView.WAREHOUSENODE:
                nodeview = new WarehouseNodeView(node.getLocation());
                break;
        }
        
        mv.getChildren().add(nodeview);
        nodeview.toFront();
        nodeview.relocate(node.getLocation().x - nodeview.getPrefWidth() / 2,
        node.getLocation().y - nodeview.getPrefHeight() / 2);
        nodeList.put(node, nodeview);

    }

    public void SwapNode(Point pt1, Point pt2) {
        // TODO
    }

    /**
     * Delete a node a the position pt
     *
     * @param pt
     */
    public void DeleteNode(Point pt) {

        this.getChildren().remove((NodeView) nodeList.get(pt));
        nodeList.remove(pt);
    }

    public void AddArc(Point pt1, Point pt2) {
        MapView mv = this;
        ArcView av = new ArcView(pt1, pt2);
        mv.getChildren().add(av);
        arcList.put(pt1, av);
        av.toBack();
    }

    public void deleteArc(Point pt1, Point pt2) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        nodeList = new HashMap<>();
        arcList = new HashMap<>();

    }

    @Override
    public void update(Publisher p, Object arg) {
        if (p instanceof Map) {
            for (Entry<Integer, Node> n : ((Map) (p)).getNodes().entrySet()) {
                AddNode(ConstView.EMPTYNODE, n.getValue());
            }
            for (Arc a : ((Map) (p)).getArcs()) {
                AddArc(a.getSrc().getLocation(), a.getDest().getLocation());
            }
        }
    }

    public void ClearMap() {
        nodeList.clear();
        arcList.clear();
        getChildren().clear();
    }

}
