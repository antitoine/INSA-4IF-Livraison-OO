package com.hexanome.model;

import com.hexanome.utils.Publisher;
import com.hexanome.utils.Subscriber;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * This class represents a map, a graph, composed of arcs (streets) and nodes
 * (crossroads).
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class Map implements Publisher {

    /**
     * Nodes contained in the map. Key : Id of the node. Value : The node
     * object.
     */
    private java.util.Map<Integer, Node> nodes;

    /**
     * List of the arcs contained in the map.
     */
    private List<Arc> arcs;

    /**
     * Subscribers to notify when the map changes.
     */
    private List<Subscriber> subscribers;

    /**
     * The minimal distances already computed by the dijkstra algorithm. Key :
     * Id of the start node Value : Map with, - Key : Id of the end node - Value
     * : The minimal distance from the source computed.
     */
    private java.util.Map<Integer, HashMap<Integer, Double>> globalMinDistances;

    /**
     * Map with the previous nodes computed by the dijkstra algorithm, to find
     * the fastest path between two nodes in the map. Key : If of the start node
     * used to compute fastest paths. Value : Map with, - Key : Id of a node -
     * Value : The previous node associated with the key
     */
    private java.util.Map<Integer, HashMap<Integer, Node>> globalPreviousNodes;

    /**
     * Builds a new instance of Map.
     */
    public Map() {
        nodes = new HashMap<>();
        arcs = new ArrayList<>();
        subscribers = new ArrayList<>();

        globalMinDistances = new HashMap<>();
        globalPreviousNodes = new HashMap<>();
    }

    /**
     * Returns a list of all arcs present in the map
     *
     * @return The arcs contained in the map.
     */
    public List<Arc> getArcs() {
        return Collections.unmodifiableList(arcs);
    }

    /**
     * Returns a collection of all nodes present in the map
     *
     * @return All the nodes contained in the map.
     */
    public Collection<Node> getNodes() {
        return nodes.values();
    }

    /**
     * Factory method to build nodes in map.
     *
     * @param id The id of the node.
     * @param location The location (x, y) of the node.
     *
     * @return The node newly created and added to the map.
     */
    public Node createNode(int id, Point location) {
        Node node = new Node(id, location);
        nodes.put(id, node);
        return node;
    }

    /**
     * Factory method to build arcs in the map.
     *
     * @param streetName The name of the new arc (the street).
     * @param length The length in meters.
     * @param avgSpeed The speed in meters/seconds.
     * @param srcNodeId The id of the source node, that must exist in the map.
     * @param destNodeId the id of the dest node, that must exist in the map.
     *
     * @return The arc newly created and added to the map.
     */
    public Arc createArc(String streetName, float length, float avgSpeed,
            int srcNodeId, int destNodeId) {
        Node srcNode = getNodeById(srcNodeId);
        Arc arc = new Arc(streetName, length, avgSpeed, srcNode, getNodeById(destNodeId));
        srcNode.attachOutgoingArc(arc);
        arcs.add(arc);
        return arc;
    }

    /**
     * Returns the node of the map which id is given or null if the id doesn't
     * exists.
     *
     * @param id Id of the node to return.
     *
     * @return The node found by its id, or null if it doesn't exist.
     */
    public Node getNodeById(int id) {
        return nodes.get(id);
    }

    /**
     * Returns the fastest path (collection of arcs) computed between two nodes.
     *
     * @param start The begenning of the path.
     * @param target The end of the path.
     *
     * @return A Path that is the fastest path (on duration) between the start
     * node and the target node.
     */
    protected Path getFastestPath(Node start, Node target) {
        LinkedList<Arc> arcs = new LinkedList<>();

        // Check if it's necessary to compute dijkstra algorithm, or if the
        // start node was already computed.
        if (!globalPreviousNodes.containsKey(start.getId())) {
            computePathsFromSource(start);
        }

        HashMap<Integer, Node> previousNodes = globalPreviousNodes.get(start.getId());

        Node src = previousNodes.get(target.getId());

        if (src == null) {
            return null;
        }

        Node dest = target;

        while (dest != start) {
            arcs.add(src.getOutgoingArc(dest));

            dest = src;
            src = previousNodes.get(dest.getId());
        }

        // Reversing it to return a path in the right order
        Collections.reverse(arcs);

        return new Path(arcs);
    }

    /**
     * Computes the fastest path from the node passed by parameter, using
     * Dijkstra algorithm. The fastest path is on arc's duration.
     *
     * @param firstNode The start node to compute.
     */
    private void computePathsFromSource(final Node firstNode) {

        HashMap<Integer, Node> previousNodes = new HashMap<>();
        HashMap<Integer, Double> minDistances = new HashMap<>();
        
        globalPreviousNodes.put(firstNode.getId(), previousNodes);
        globalMinDistances.put(firstNode.getId(), minDistances);

        // Prority queue with custom comparator, using the globalMinDistances Map
        PriorityQueue<Node> nodesQueue = new PriorityQueue<>(1, (Comparator<Node>) (node1, node2) -> {
            if (!globalMinDistances.containsKey(firstNode.getId())) {
                return 0;
            }

            if (!globalMinDistances.get(firstNode.getId()).containsKey(node1.getId())) {
                return -1;
            }
            
            if (!globalMinDistances.get(firstNode.getId()).containsKey(node2.getId())) {
                return 1;
            }

            return (int) (globalMinDistances.get(firstNode.getId()).get(node1.getId())
                        - globalMinDistances.get(firstNode.getId()).get(node2.getId()));
        });

        // Initialize data        
        previousNodes.put(firstNode.getId(), firstNode);
        for (Node node : nodes.values()) {
            minDistances.put(node.getId(), Double.MAX_VALUE);
            nodesQueue.add(node);
        }
        minDistances.put(firstNode.getId(), 0.);

        while (!nodesQueue.isEmpty()) {
            Node source = nodesQueue.poll();

            if (minDistances.containsKey(source.getId())) {

                source.getOutgoingArcs().stream().forEach((outgoing) -> {
                    Node dest = outgoing.getDest();
                    double newDist = minDistances.get(source.getId()) + outgoing.getDuration();

                    // If minimal distance from the firstNode to the source 
                    // node + the duration of the outgoing arc < minimal distance
                    // from the firstNode to the dest node
                    if (!minDistances.containsKey(dest.getId())
                            || newDist < minDistances.get(dest.getId())) {
                        // Then we update the destination node
                        nodesQueue.remove(dest);
                        minDistances.put(dest.getId(), newDist);
                        previousNodes.put(dest.getId(), source);
                        nodesQueue.add(dest);
                    }
                }); // visiting each arc exiting the source node
            }
        }
    }

    /**
     * Clears the map and all its data.
     */
    public void clear() {
        arcs.clear();
        globalMinDistances.clear();
        globalPreviousNodes.clear();
        nodes.clear();
        notifySubscribers();
    }

    /**
     * Resets the arcs in their original state.
     */
    public void resetArcs() {
        arcs.stream().forEach((arc) -> {
            arc.clearAssociatedTimeSlot();
        });
    }

    /**
     * Resets the nodes in their original state, without any delivery.
     */
    public void resetNodes() {
        nodes.values().stream().forEach((node) -> {
            node.attachDelivery(null);
        });
    }

    /**
     * Adds one subscriber and updates it.
     *
     * @param subscriber Subscriber to add.
     */
    @Override
    public void addSubscriber(Subscriber subscriber) {
        subscribers.add(subscriber);
        subscriber.update(this, null);
    }

    /**
     * Remove one subscriber.
     *
     * @param subscriber Subscriber to remove
     */
    @Override
    public void removeSubscriber(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    /**
     * Notify all subscribers.
     */
    @Override
    public void notifySubscribers() {
        subscribers.stream().forEach((subscriber) -> {
            subscriber.update(this, null);
        });
    }

    /**
     * Removes all subscribers.
     */
    @Override
    public void clearSubscribers() {
        subscribers.clear();
    }

    /**
     * Returns the string describing the objet, used for debug only.
     *
     * @return a string describing the object.
     */
    @Override
    public String toString() {

        String strarcs = "";

        if (arcs.size() > 0) {
            strarcs = arcs.stream()
                          .map((arc) -> arc.toString() + ",")
                          .reduce(strarcs, String::concat);
            strarcs = strarcs.substring(0, strarcs.length() - 1);
        }

        String strnodes = "";
        if (nodes.entrySet().size() > 0) {
            strnodes = nodes.entrySet()
                            .stream()
                            .map((n) -> n.getValue().toString() + ",")
                            .reduce(strnodes, String::concat);
            strnodes = strnodes.substring(0, strnodes.length() - 1) + "";
        }

        return String.format(""
                + "{"
                + "\"nodes\":[\n"
                + "%s\n"
                + "],\n"
                + "\"arcs\":[\n"
                + "%s\n"
                + "]\n"
                + "}", strnodes, strarcs);
    }
}
