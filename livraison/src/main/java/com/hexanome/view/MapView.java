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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Stack;
import javafx.scene.layout.AnchorPane;

/**
 * Reprensents a view of the map with nodes and arc
 *
 * This is a one-to-one representation of the Map model
 */
public class MapView extends AnchorPane implements Subscriber {

    HashMap<Node, NodeView> nodeList = new HashMap<>();
    HashSet<ArcView> arcslist = new HashSet<>();
    HashMap<NodePair, ArrayList<Arc>> arcsMap = new HashMap<>();

    /**
     * Initializes the controller class.
     */
    public void MapView() {

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
        nodeList.get(delivery.getNode()).setType(ConstView.EMPTY_NODE);
    }

    /**
     * Add an arc in the view
     *
     * @param arcs arc as described in the model
     */
    public void addRouteArcs(ArrayList<Arc> arcs) {
        clearArc();
        
        for (Arc arc : arcs) {
            NodePair np = new NodePair(arc.getSrc(), arc.getDest());
            if (arcsMap.get(np) != null) {
                arcsMap.get(np).add(arc);
            } else {
                ArrayList<Arc> s = new ArrayList<>();
                arcsMap.put(np, s);
                s.add(arc);
            }
        }

        for (Entry<NodePair, ArrayList<Arc>> entrySet : arcsMap.entrySet()) {
            ArcView av = new ArcView(entrySet.getValue(),
                    ConstView.ArcViewType.ROUTE);
            av.toBack();
            arcslist.add(av);
        }
        
        getChildren().addAll(arcslist);

    }

    public void addEmptyArcsAndNodes(Collection<Arc> arcs, Collection<Node> nodes) {
        arcslist.clear();
        for (Arc arc : arcs) {
            NodePair np = new NodePair(arc.getSrc(), arc.getDest());
            if (arcsMap.get(np) != null) {
                arcsMap.get(np).add(arc);
            } else {
                ArrayList<Arc> s = new ArrayList<>();
                arcsMap.put(np, s);
                s.add(arc);
            }
        }

        for (Entry<NodePair, ArrayList<Arc>> entrySet : arcsMap.entrySet()) {
            ArcView av = new ArcView(entrySet.getValue(),
                    ConstView.ArcViewType.STANDARD);
            arcslist.add(av);
        }

        for (Node node : nodes) {
            NodeView nv = new NodeView(ConstView.EMPTY_NODE, node);
            nodeList.put(node, nv);
            nv.relocate(node.getLocation().x - nv.getPrefWidth() / 2,
                    node.getLocation().y - nv.getPrefHeight() / 2);
            nv.toFront();
        }

        getChildren().addAll(arcslist);
        getChildren().addAll(nodeList.values());
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
                    (nodeList.get(d.getNode())).setType(ConstView.DELIVERY_NODE);
                }
            }
            (nodeList.get(planning.getWarehouse())).setType(ConstView.WAREHOUSE_NODE);
        }
        if (p instanceof Route) {
            Route route = (Route) p;
            clearArc();
            System.out.println("" + route.getPaths().toString());
            ArrayList<Arc> arcs = new ArrayList<>();
            for (Path path : route.getPaths()) {
                for (Arc a : path.getArcs()) {
                    arcs.add(a);
                }
            }
            addRouteArcs(arcs);
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
        arcsMap.clear();
    }

    /**
     * Reset all nodeView to emptyNode
     */
    public void clearDeliveries() {
        for (Entry<Node, NodeView> n : nodeList.entrySet()) {
            if (n.getValue().getCurrentNodeType().equals(ConstView.DELIVERY_NODE)
                    || n.getValue().getCurrentNodeType().equals(ConstView.WAREHOUSE_NODE)) {
                n.getValue().setType(ConstView.EMPTY_NODE);
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
