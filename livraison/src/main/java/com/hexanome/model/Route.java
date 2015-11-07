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
 * @author pierre
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
     * Update the delivery time of each delivery contained in the path
     * collection.
     */
    private void updateDeliveriesTime() {
        // Get for each path the delivery object to update
        for (int i = 0, iMax = paths.size() - 1; i <= iMax; ++i) {
            Path path = paths.get(i);
            Path previousPath = (i == 0) ? null : paths.get(i - 1);

            Delivery delivery = path.getFirstNode().getDelivery();

            if (delivery != null) {
                float deliveryTime = path.getPathDuration();

                if (previousPath != null) {
                    Delivery previousDelivery = previousPath.getFirstNode().getDelivery();
                    if (previousDelivery != null) {
                        deliveryTime += previousDelivery.getDeliveryTime();
                    }
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

            if (delivery != null) {
                for (Arc arc : p.getArcs()) {
                    arc.addAssociatedTimeSlot(delivery.getTimeSlot());
                }
            }
        }
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
     * Adds a delivery to the route.
     *
     * @param delivery the delivery to add.
     * @param nodePreviousDelivery The node with the delivery that will be
     * executed before the one to add.
     */
    void addDelivery(Delivery delivery, Node nodePreviousDelivery) {
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

        updateDeliveriesTime();
        updateArcTimeSlots();

        notifySubscribers();
    }

    /**
     * Removes a delivery from the route.
     *
     * @param deliveryToRemove the delivery to remove.
     */
    void removeDelivery(Delivery deliveryToRemove) {
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

            updateDeliveriesTime();
            updateArcTimeSlots();

            notifySubscribers();
        }
    }

    /**
     * Swaps two deliveries.
     *
     * @param delivery1 the first delivery to swap.
     * @param delivery2 the second delivery to swap.
     */
    void swapDeliveries(Delivery delivery1, Delivery delivery2) {
        // Find the first path with delivery 1 or delivery 2 as source
        int indexPreviousPath = -1, indexNextPath = -1;
        Delivery previousDelivery = null, nextDelivery = null;
        Path previousPath = null, nextPath = null;

        for (int indexPath = 0, maxIndexPath = paths.size() - 1;
                (previousPath == null || nextPath == null)
                && indexPath <= maxIndexPath; ++indexPath) {
            Path p = paths.get(indexPath);

            if (previousPath == null) {
                Delivery destDelivery = p.getLastNode().getDelivery();
                if (destDelivery != null) {
                    if (destDelivery.equals(delivery1)) {
                        previousPath = p;
                        nextDelivery = delivery1;
                        previousDelivery = delivery2;
                        indexPreviousPath = indexPath;
                    } else if (destDelivery.equals(delivery2)) {
                        previousPath = p;
                        nextDelivery = delivery2;
                        previousDelivery = delivery1;
                        indexPreviousPath = indexPath;
                    }
                }
            }

            if (previousDelivery != null && nextPath == null) {
                Delivery srcDelivery = p.getFirstNode().getDelivery();
                if (srcDelivery != null && srcDelivery.equals(previousDelivery)) {
                    nextPath = p;
                    indexNextPath = indexPath;
                }
            }
        }

        if (previousPath != null && nextPath != null) {

            Path pathToNewFirstDelivery = planning.getMap().getFastestPath(
                    previousPath.getFirstNode(),
                    nextPath.getFirstNode()
            );

            Path newFirstDeliveryToNextDelivery = planning.getMap().getFastestPath(
                    nextPath.getFirstNode(),
                    paths.get(indexPreviousPath + 1).getLastNode()
            );

            Path previousNewFirstDeliveryToNewLastDelivery = planning.getMap().getFastestPath(
                    paths.get(indexNextPath - 1).getFirstNode(),
                    previousPath.getLastNode()
            );

            Path newLastDeliveryToNewFirstDeliveryNext = planning.getMap().getFastestPath(
                    previousPath.getLastNode(),
                    nextPath.getLastNode()
            );

            paths.remove(indexPreviousPath);
            paths.add(indexPreviousPath, pathToNewFirstDelivery);

            paths.remove(indexPreviousPath + 1);
            paths.add(indexPreviousPath + 1, newFirstDeliveryToNextDelivery);

            paths.remove(indexNextPath - 1);
            paths.add(indexNextPath - 1, previousNewFirstDeliveryToNewLastDelivery);

            paths.remove(indexNextPath);
            paths.add(indexNextPath, newLastDeliveryToNewFirstDeliveryNext);

            updateDeliveriesTime();
            updateArcTimeSlots();

            notifySubscribers();
        }
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

    @Override
    public String toString() {
        String strpaths = "{";
        for (Path path : paths) {
            strpaths += path.toString() + ",";
        }
        strpaths = strpaths.substring(0, strpaths.length() - 1) + "}";
        return String.format("{ \"Route\" : { \"paths\":%s } }", strpaths);
    }

    @Override
    public void addSubscriber(Subscriber s) {
        subscribers.add(s);
        s.update(this, planning);
    }

    @Override
    public void removeSubscriber(Subscriber s) {
        subscribers.remove(s);
    }

    @Override
    public void notifySubscribers() {
        for (Subscriber s : subscribers) {
            s.update(this, planning);
        }
    }

    @Override
    public void clearSubscribers() {
        subscribers.clear();
    }

    public List<Path> getPaths() {
        return Collections.unmodifiableList(paths);
    }

}
