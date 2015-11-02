package com.hexanome.view;

import com.hexanome.model.Arc;
import com.hexanome.model.Delivery;
import com.hexanome.model.Map;
import com.hexanome.model.Node;
import com.hexanome.model.Path;
import com.hexanome.model.Planning;
import com.hexanome.model.Route;
import com.hexanome.model.TimeSlot;
import com.hexanome.utils.Publisher;
import com.hexanome.utils.Subscriber;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

/**
 * Reprensents a view of the map with nodes and arc
 *
 * This is a one-to-one representation of the Map model
 */
public class MapView extends AnchorPane implements Subscriber, Initializable {

    static HashMap<Node, NodeView> nodeList;
    HashSet<ArcView> arcslist;
    HashMap<PairPoint, ArcView> arcsMap;

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
     * @param type Node Type
     * @param node node as describes in the model
     */
    private void addNode(String type, Node node) {
        NodeView nodeView = new NodeView(type, node);
        getChildren().add(nodeView);
        nodeView.toFront();
        nodeView.relocate(node.getLocation().x - nodeView.getPrefWidth() / 2,
                node.getLocation().y - nodeView.getPrefHeight() / 2);
        nodeList.put(node, nodeView);
    }

    /**
     * Delete a delivery from the map
     *
     * @param delivery
     */
    void deleteDelivery(Delivery delivery) {
        nodeList.get(delivery.getNode()).setType(ConstView.EMPTYNODE);
    }

    /**
     * Add an arc in the view
     *
     * @param arc arc as described in the model
     */
    public void addRouteArc(Arc arc) {
        MapView mv = this;
        ArcView av = new ArcView(arc, ConstView.ArcViewType.ROUTE);
        mv.getChildren().add(av);
        arcslist.add(av);
    }

    public void addEmptyArcsAndNodes(Collection<Arc> arcs, Collection<Node> nodes) {
        arcslist.clear();
        for (Arc arc : arcs) {
            ArcView av = new ArcView(arc, ConstView.ArcViewType.STANDARD);
            arcslist.add(av);
        }
        for (Node node : nodes) {
            NodeView nv = new NodeView(ConstView.EMPTYNODE, node);
            nodeList.put(node, nv);
            nv.relocate(node.getLocation().x - nv.getPrefWidth() / 2,
                    node.getLocation().y - nv.getPrefHeight() / 2);
        }

        getChildren().addAll(arcslist);
        getChildren().addAll(nodeList.values());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nodeList = new HashMap<>();
        arcslist = new HashSet<>();
    }

    @Override
    public void update(Publisher p, Object arg) {
        if (p instanceof Map) {
            Map map = (Map) p;
            addEmptyArcsAndNodes(map.getArcs(), map.getNodes().values());
        }
        if (p instanceof Planning) {
            clearArc();
            Planning planning = (Planning) p;
            clearDeliveries();
            ColorsGenerator.getInstance(planning.getTimeSlots());
            for (TimeSlot ts : planning.getTimeSlots()) {
                for (Delivery d : ts.getDeliveries()) {
                    (nodeList.get(d.getNode())).setType(ConstView.DELIVERYNODE);
                }
            }
            (nodeList.get(planning.getWarehouse())).setType(ConstView.WAREHOUSENODE);
        }
        if (p instanceof Route) {
            Route route = (Route) p;
            clearArc();
            System.out.println(""+route.getPaths().toString());
            for (Path path : route.getPaths()) {
                for (Arc a : path.getArcs()) {
                    addRouteArc(a);
                }
            }
        }
    }

    /**
     * Remove all nodeview and arview from the mapView
     */
    public void clearMap() {
        nodeList.clear();
        arcslist.clear();
        getChildren().clear();
    }

    /**
     * Remove all arcView
     */
    public void clearArc() {
        getChildren().removeAll(arcslist);
        arcslist.clear();
    }

    /**
     * Reset all nodeView to emptyNode
     */
    public void clearDeliveries() {
        for (Entry<Node, NodeView> n : nodeList.entrySet()) {
            if (n.getValue().getCurrentNodeType().equals(ConstView.DELIVERYNODE)
                    || n.getValue().getCurrentNodeType().equals(ConstView.WAREHOUSENODE)) {
                n.getValue().setType(ConstView.EMPTYNODE);
            }
        }
    }

    /**
     * Select the delivery passed as parameter
     *
     * @param delivery
     */
    public void selectDelivery(Delivery delivery) {
        nodeList.get(delivery.getNode()).showPopOver();
    }

    /**
     * Add the PopOver which corresponds the node passed as paramater
     *
     * @param node
     */
    public void hidePopOver(Node node) {
        nodeList.get(node).hidePopOver();
    }

}
