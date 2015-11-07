package com.hexanome.model;

import com.hexanome.utils.Publisher;
import com.hexanome.utils.Subscriber;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 *
 * @author paul
 */
public class Map implements Publisher {

    private HashMap<Integer, Node> nodes;
    private ArrayList<Arc> arcs;
    private ArrayList<Subscriber> subscribers;

    private HashMap<Integer, HashMap<Integer, Double>> globalMinDistances; // int = node id, double = distance from source to this node
    private HashMap<Integer, HashMap<Integer, Node>> globalPreviousNodes; // int = id of the current node, Node = the previous node

    /**
     *
     * @param nodes
     * @param arcs
     */
    public Map() {
        nodes = new HashMap<>();
        arcs = new ArrayList<>();
        subscribers = new ArrayList<>();

        globalMinDistances = new HashMap<>();
        globalPreviousNodes = new HashMap<>();
    }

    /**
     * Factory method to build nodes in map
     *
     * @param id
     * @param location
     * @return
     */
    public Node createNode(int id, Point location) {
        // Create a new node 
        Node n = new Node(id, location);
        // Add the new node to the local collection
        nodes.put(id, n);
        // Return the new node
        return n;
    }

    /**
     * Factory method to build arcs in the map
     *
     * @param streetName
     * @param length
     * @param avgSpeed
     * @param srcNodeId
     * @param destNodeId
     * @return
     */
    public Arc createArc(String streetName, float length, float avgSpeed, int srcNodeId, int destNodeId) {
        // Retreive source node
        Node srcNode = getNodeById(srcNodeId);
        // Creating a new arc
        Arc a = new Arc(streetName, length, avgSpeed, srcNode, getNodeById(destNodeId));
        // Attaching the arc to its source node
        srcNode.attachOutgoingArc(a);
        // Adding arc to the internal collection
        arcs.add(a);
        // Return the new arc
        return a;
    }

    /**
     *
     * @param id
     * @return
     */
    public Node getNodeById(int id) {
        return nodes.get(id);
    }

    /**
     *
     * @return
     */
    public ArrayList<Arc> getArcs() {
        return arcs;
    }

    /**
     *
     * @return
     */
    public HashMap<Integer, Node> getNodes() {
        return nodes;
    }

    public Path getFastestPath(Node start, Node target) {
        ArrayList<Arc> arcs = new ArrayList<>();
        
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
            //System.out.println("\n\n----------------------- src = "+src.toString());
            
            dest = src;
            src = previousNodes.get(dest.getId());
            //System.out.println("\n\n----------------------- dest = "+dest.toString());
        }

        Collections.reverse(arcs); /* reversing it so that the arcs are in the right order */
        Path path = new Path(arcs);

        return path;
    }

    private void computePathsFromSource(final Node firstNode) {
        
        HashMap<Integer, Node> previousNodes = new HashMap<>();
        HashMap<Integer, Double> minDistances = new HashMap<>();
        globalPreviousNodes.put(firstNode.getId(), previousNodes);
        globalMinDistances.put(firstNode.getId(), minDistances);
        
        PriorityQueue<Node> nodesQueue = new PriorityQueue<>(1, new Comparator<Node>() {

            @Override
            public int compare(Node node1, Node node2) {
                if (!globalMinDistances.containsKey(firstNode.getId())) {
                    return 0;
                }
                
                if (!globalMinDistances.get(firstNode.getId()).containsKey(node1.getId())) {
                    return -1;
                }
                if (!globalMinDistances.get(firstNode.getId()).containsKey(node2.getId())) {
                    return 1;
                }

                return (int) (globalMinDistances.get(firstNode.getId()).get(node1.getId()) - globalMinDistances.get(firstNode.getId()).get(node2.getId()));
            }
        });

        // Initialize data        
        previousNodes.put(firstNode.getId(), firstNode);
        for (Node n : nodes.values()) {
            minDistances.put(n.getId(), Double.MAX_VALUE);
            nodesQueue.add(n);
        }
        minDistances.put(firstNode.getId(), 0.);

        while (!nodesQueue.isEmpty()) {
            Node source = nodesQueue.poll();

            if (minDistances.containsKey(source.getId())) {

                for (Arc outgoing : source.getOutgoingArcs()) // visiting each arc exiting the source node
                {
                    Node dest = outgoing.getDest();
                    double newDist = minDistances.get(source.getId()) + outgoing.getDuration();

                    // if minimal distance from the firstNode to the source node + the duration of the outgoing arc < minimal distance from the firstNode to the dest node
                    if (!minDistances.containsKey(dest.getId()) || newDist < minDistances.get(dest.getId())) {
                        // then we update the destination node
                        nodesQueue.remove(dest);
                        minDistances.put(dest.getId(), newDist);
                        previousNodes.put(dest.getId(), source);
                        nodesQueue.add(dest);
                    }
                }
            }

        }
    }

    /**
     * Clear the map and all its data.
     */
    public void clear() {
        arcs.clear();
        globalMinDistances.clear();
        globalPreviousNodes.clear();
        nodes.clear();
        notifySubscribers();
    }
    
    /**
     * Reset the arcs in their original state.
     */
    void resetArcs() {
        for (Arc arc : arcs) {
            arc.clearAssociatedTimeSlot();
        }
    }

    @Override
    public String toString() {

        String strarcs = "";

        if (arcs.size() > 0) {
            for (Arc arc : arcs) {
                strarcs += arc.toString() + ",";
            }
            strarcs = strarcs.substring(0, strarcs.length() - 1);
        }

        String strnodes = "";
        if (nodes.entrySet().size() > 0) {
            for (java.util.Map.Entry<Integer, Node> n : nodes.entrySet()) {
                strnodes += n.getValue().toString() + ",";
            }
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

    @Override
    public void addSubscriber(Subscriber s) {
        subscribers.add(s);
        s.update(this, null);
    }

    @Override
    public void removeSubscriber(Subscriber s) {
        subscribers.remove(s);
    }

    @Override
    public void notifySubscribers() {
        for (Subscriber s : subscribers) {
            s.update(this, null);
        }
    }

    @Override
    public void clearSubscribers() {
        subscribers.clear();
    }

    

}
