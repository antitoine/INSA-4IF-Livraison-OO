package com.hexanome.view;

import com.hexanome.controller.UIManager;
import com.hexanome.model.*;
import com.hexanome.utils.Publisher;
import com.hexanome.utils.Subscriber;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

/**
 * Reprensents a view of the map with nodes and arc
 * This is a one-to-one representation of the Map model
 */
public class MapView extends AnchorPane implements Subscriber {

    HashMap<Node, NodeView> nodeList = new HashMap<>();
    LinkedList<ArcView> arcslist = new LinkedList<>();
    HashMap<NodePair, LinkedList<Arc>> arcsMap = new HashMap<>();

    @Override
    public void update(Publisher p, Object arg) {

        // called when a new map is loaded
        if (p instanceof Map) {
            clearMap();
            Map map = (Map) p;

            addArcs(map.getArcs());
            getChildren().addAll(arcslist);

            addEmptyNodes(map.getNodes().values());
        }

        // called when a new planning is loaded
        if (p instanceof Planning) {
            Planning planning = (Planning) p;
            ColorsGenerator.getInstance(planning.getTimeSlots());
            UIManager.getInstance().getMainWindow().setLegend();
        }

        // called when a new Route is computed
        if (p instanceof Route) {
            clearMap();
            Planning planning = (Planning) arg;
            Map map = planning.getMap();

            updateRouteOnMap(planning, map);
        }
    }


    /**
     * Select the delivery passed as parameter
     *
     * @param delivery delivery to be selected
     */
    void selectDelivery(Delivery delivery) {
        nodeList.get(delivery.getNode()).showPopOver();
    }

    /**
     * Hide the PopOver which corresponds the node passed as paramater
     *
     * @param node node which should dismiss its popover
     */
    public void hidePopOver(Node node) {
        nodeList.get(node).hidePopOver();
    }

    /**
     * Show the popover above the node passed as parameter
     *
     * @param node node which should display a popover
     */
    public void showPopOver(Node node) {
        nodeList.get(node).showPopOver();
    }


    private void updateRouteOnMap(Planning planning, Map map){
        ArrayList<Arc> mapArc = new ArrayList<>(map.getArcs());

        addArcs(mapArc);
        getChildren().addAll(arcslist);

        addEmptyNodes(map.getNodes().values());

        for (TimeSlot ts : planning.getTimeSlots()) {
            for (Delivery d : ts.getDeliveries()) {
                (nodeList.get(d.getNode())).setType(ConstView.DELIVERY_NODE);
            }
        }

        (nodeList.get(planning.getWarehouse())).setType(ConstView.WAREHOUSE_NODE);
    }

    private void addArcs(Collection <Arc> arcs){
        if(arcs == null){
            return;
        }

        for (Arc arc : arcs) {
            NodePair np = new NodePair(arc.getSrc(), arc.getDest());
            if (arcsMap.get(np) != null) {
                arcsMap.get(np).add(arc);
            } else {
                LinkedList<Arc> s = new LinkedList<>();
                arcsMap.put(np, s);
                s.add(arc);
            }
        }

        for (Entry<NodePair, LinkedList<Arc>> entrySet : arcsMap.entrySet()) {
            ArcView av  = new ArcView(entrySet.getValue());
            arcslist.add(av);
        }
    }

    private void addEmptyNodes(Collection<Node> nodes) {
        if (nodes != null) {
            for (Node node : nodes) {
                NodeView nv = new NodeView(ConstView.EMPTY_NODE, node);
                nodeList.put(node, nv);
                nv.relocate(node.getLocation().x - nv.getPrefWidth() / 2,
                        node.getLocation().y - nv.getPrefHeight() / 2);
                nv.toFront();
            }
            getChildren().addAll(nodeList.values());
        }
    }


    /**
     * Remove everything from the mapView
     */
    private void clearMap() {
        nodeList.clear();
        arcslist.clear();
        arcsMap.clear();
        getChildren().clear();
    }


}
