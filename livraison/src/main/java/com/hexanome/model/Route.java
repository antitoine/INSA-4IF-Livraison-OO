package com.hexanome.model;

import com.hexanome.utils.Publisher;
import com.hexanome.utils.Subscriber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a route, with the entire path to follow to execute
 * every delivery.
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class Route implements Publisher {

    /**
     * Collection of path representing the route.
     */
    private LinkedList<Path> paths;

    /**
     * List of the subscribers.
     */
    private ArrayList<Subscriber> subscribers;

    /**
     * Planning associated with this route.
     */
    private Planning planning;

    /**
     * Construct the route with the paths passed by parameter and update the
     * deliveries times.
     *
     * @param planning
     * @param paths The paths representing the route.
     */
    public Route(Planning planning, LinkedList<Path> paths) {
        this.planning = planning;
        this.paths = paths;
        subscribers = new ArrayList<>();

        updateDeliveriesTime();
        updateArcTimeSlots();
    }

    /**
     * Rteurns a list of paths
     *
     * @return
     */
    public List<Path> getPaths() {
        return Collections.unmodifiableList(paths);
    }

    /**
     * Find the delivery passed by parameter and return the next delivery if
     * exists.
     *
     * @param delivery The delivery to find in the route.
     * @return The next delivery, or null if it doesn't exist.
     */
    public Delivery getNextDelivery(Delivery delivery) {
        for (Path p : paths) {
            Delivery currentDelivery = p.getFirstNode().getDelivery();

            if (currentDelivery != null) {
                if (currentDelivery.equals(delivery)) {
                    return p.getLastNode().getDelivery();
                }
            }
        }

        return null;
    }

    /**
     * Find and return the node of the previous delivery done before the
     * delivery passed by parameter.
     *
     * @param delivery The delivery to find.
     * @return The node of the previous delivery in the current route.
     */
    Node getNodePreviousDelivery(Delivery delivery) {
        for (Path p : paths) {
            if (p.getLastNode().getDelivery() != null
                    && p.getLastNode().getDelivery().equals(delivery)) {
                return p.getFirstNode();
            }
        }
        return null;
    }

    /**
     * Update the delivery time of each delivery contained in the path
     * collection.
     */
    private void updateDeliveriesTime() {
        // Get for each path the delivery object to update
        for (int i = 0, iMax = paths.size() - 1; i <= iMax; ++i) {
            Path path = paths.get(i);

            Delivery delivery = path.getLastNode().getDelivery();
            Delivery previousDelivery = path.getFirstNode().getDelivery();

            if (delivery != null) {
                float deliveryTime = path.getPathDuration();

                if (previousDelivery != null) {
                    deliveryTime += previousDelivery.getDeliveryEndTime();
                } else { // Start is warehouse
                    deliveryTime += delivery.getTimeSlot().getStartTime();
                }

                if (deliveryTime < delivery.getTimeSlot().getStartTime()) {
                    delivery.setDeliveryTime(delivery.getTimeSlot().getStartTime());
                } else {
                    delivery.setDeliveryTime(deliveryTime);
                }
            }
        }
    }

    /**
     * Update the associated time slots of the arcs contained in the path.
     */
    private void updateArcTimeSlots() {
        planning.getMap().resetArcs();

        for (Path p : paths) {
            Delivery delivery = p.getLastNode().getDelivery();

            if (delivery == null) {
                delivery = p.getFirstNode().getDelivery();
            }

            if (delivery != null) {
                for (Arc arc : p.getArcs()) {
                    arc.addAssociatedTimeSlot(delivery.getTimeSlot());
                }
            }
        }
    }

    /**
     * Adds a delivery to the route, and notify the subscribers.
     * @param delivery The delivery to add
     * @param nodePreviousDelivery The node with the delivery that will be
     * executed before the one to add.
     */
    void addDelivery(Delivery delivery, Node nodePreviousDelivery) {
        addDelivery(delivery, nodePreviousDelivery, true);
    }

    /**
     * Adds a delivery to the route.
     *
     * @param delivery the delivery to add.
     * @param nodePreviousDelivery The node with the delivery that will be
     * executed before the one to add.
     * @param update Notify the subscribers if update is true.
     */
    private void addDelivery(Delivery delivery, Node nodePreviousDelivery, boolean update) {
        // Find the previous delivery in the list of paths
        int indexPreviousPath = -1;
        Path pathToReplace = null;

        boolean previousPathFound = false;
        for (int indexPath = 0, maxIndexPath = paths.size() - 1;
                !previousPathFound && indexPath <= maxIndexPath;
                ++indexPath) {
            Path p = paths.get(indexPath);

            Node currentNode = p.getFirstNode();

            if (currentNode != null && currentNode.equals(nodePreviousDelivery)) {
                previousPathFound = true;
                indexPreviousPath = indexPath;
                pathToReplace = p;
            }
        }

        // Previous path with the previous delivery found
        if (pathToReplace != null) {
            // Compute the path between the previous delivery and the new delivery
            Path previousPath = planning.getMap().getFastestPath(pathToReplace.getFirstNode(),
                    delivery.getNode());

            // Compute the path between the next delivery and the new delivery
            Path nextPath = planning.getMap().getFastestPath(delivery.getNode(),
                    pathToReplace.getLastNode());

            // Replace the paths in the list
            paths.remove(indexPreviousPath);
            paths.add(indexPreviousPath, nextPath);
            paths.add(indexPreviousPath, previousPath);

        } else {
            Path previousPath = planning.getMap().getFastestPath(nodePreviousDelivery, delivery.getNode());
            Path nextPath = planning.getMap().getFastestPath(delivery.getNode(), nodePreviousDelivery);
            paths.add(previousPath);
            paths.add(nextPath);
        }

        if (update) {
            updateDeliveriesTime();
            updateArcTimeSlots();

            notifySubscribers();
        }
    }

    /**
     * Remove a delivery from the route and notify the subscribers.
     * 
     * @param deliveryToRemove The delivery to remove.
     */
    void removeDelivery(Delivery deliveryToRemove) {
        removeDelivery(deliveryToRemove, true);
    }

    /**
     * Removes a delivery from the route.
     *
     * @param deliveryToRemove the delivery to remove.
     * @param update Notify the subscribers if update is true.
     */
    private void removeDelivery(Delivery deliveryToRemove, boolean update) {
        // Search the path to remove, according to the delivery to remove
        int indexPathtoRemove = -1;
        boolean pathToRemoveFound = false;
        Path pathToRemove = null;
        for (int indexPath = 0, maxIndex = paths.size() - 1;
                indexPath <= maxIndex && !pathToRemoveFound;
                ++indexPath) {
            Path path = paths.get(indexPath);
            Node currentNode = path.getLastNode();
            if (currentNode != null && currentNode.equals(deliveryToRemove.getNode())) {
                pathToRemoveFound = true;
                indexPathtoRemove = indexPath;
                pathToRemove = path;
            }
        }

        // Remove the path if it was previously found
        if (pathToRemove != null) {
            Node firstNode = pathToRemove.getFirstNode();
            Node lastNode = paths.get(indexPathtoRemove + 1).getLastNode();

            boolean addNewPath = (paths.size() > 2);

            // Remove the old paths in the list and the new one
            paths.remove(indexPathtoRemove + 1);
            paths.remove(indexPathtoRemove);

            // If the delivery to remove is in the last path, it's useless to
            // add a new path.
            if (addNewPath) {
                Path newPath = planning.getMap().getFastestPath(firstNode, lastNode);
                paths.add(indexPathtoRemove, newPath);
            }

            TimeSlot ts = deliveryToRemove.getTimeSlot();
            ts.removeDelivery(deliveryToRemove);

            if (update) {
                updateDeliveriesTime();
                updateArcTimeSlots();

                notifySubscribers();
            }            
        }
    }

    /**
     * Swaps two deliveries.
     *
     * @param delivery1 the first delivery to swap.
     * @param delivery2 the second delivery to swap.
     */
    void swapDeliveries(Delivery delivery1, Delivery delivery2) {

        Delivery previousDelivery = null, nextDelivery = null;
        
        // Find the delivery done first.
        for (Path path : paths) {
            Delivery destDelivery = path.getLastNode().getDelivery();
            if (destDelivery.equals(delivery1)) {
                nextDelivery = delivery1;
                previousDelivery = delivery2;
                break;
            } else if (destDelivery.equals(delivery2)) {
                nextDelivery = delivery2;
                previousDelivery = delivery1;
                break;
            }
        }

        // Swap deliveries
        if (previousDelivery != null && nextDelivery != null) {
            TimeSlot timeSlot = nextDelivery.getTimeSlot();
            removeDelivery(nextDelivery, false);
            
            timeSlot.addDelivery(nextDelivery);
            addDelivery(nextDelivery, previousDelivery.getNode(), false);

            updateDeliveriesTime();
            updateArcTimeSlots();

            notifySubscribers();
        }
    }

    /**
     * Add one subscriber
     *
     * @param s Subscriber to add
     */
    @Override
    public void addSubscriber(Subscriber s) {
        subscribers.add(s);
        s.update(this, planning);
    }

    /**
     * Removes one subscriber
     *
     * @param s Subscriber to remove
     */
    @Override
    public void removeSubscriber(Subscriber s) {
        subscribers.remove(s);
    }

    /**
     * Notifies all subscribers
     */
    @Override
    public void notifySubscribers() {
        for (Subscriber s : subscribers) {
            s.update(this, planning);
        }
    }

    /**
     * Remove all subscribers
     */
    @Override
    public void clearSubscribers() {
        subscribers.clear();
    }

    /**
     * Comparison operator for equality
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Route)) {
            return false;
        }

        Route route = (Route) obj;

        if (this.subscribers.size() != route.subscribers.size() || this.paths.size() != route.paths.size() || !(this.planning.equals(route.planning))) {
            return false;
        }

        for (int i = 0; i < this.paths.size(); i++) {
            if (!(this.paths.get(i).equals(route.paths.get(i)))) {
                return false;
            }
        }

        for (int j = 0; j < this.subscribers.size(); j++) {
            if (!(this.subscribers.get(j).equals(route.subscribers.get(j)))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the string describing the objet, used for debug only
     *
     * @return a string describing the object
     */
    @Override
    public String toString() {
        String strpaths = "{";
        for (Path path : paths) {
            strpaths += path.toString() + ",";
        }
        strpaths = strpaths.substring(0, strpaths.length() - 1) + "}";
        return String.format("{ \"Route\" : { \"paths\":%s } }", strpaths);
    }

}
