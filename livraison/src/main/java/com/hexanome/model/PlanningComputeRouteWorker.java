/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexanome.model;

import com.hexanome.utils.ITSP;
import java.util.LinkedList;
import java.util.List;

/**
 * Worker Thread used to compute a route of a planning. Can be interrupted.
 *
 * @author Pierre Jarsaillon
 */
public class PlanningComputeRouteWorker extends Thread {

    /**
     * The planning to work with.
     */
    private final Planning planning;

    /**
     * Constructor of the worker thread.
     *
     * @param planning The planning to use to compute the route.
     */
    public PlanningComputeRouteWorker(Planning planning) {
        this.planning = planning;
    }

    @Override
    public void run() {
        PathGraph graph = new PathGraph();

        List<TimeSlot> timeSlots = planning.getTimeSlots();
        Node warehouse = planning.getWarehouse();
        Map map = planning.getMap();

        // Time slots processing
        for (int numTimeSlot = 0, maxTimeSlot = timeSlots.size() - 1; numTimeSlot <= maxTimeSlot; ++numTimeSlot) {
            if (isInterrupted()) {
                return;
            }

            TimeSlot ts = timeSlots.get(numTimeSlot);

            // Compute each delivery of the current time slot
            for (Delivery startDelivery : ts.getDeliveries()) {
                if (isInterrupted()) {
                    return;
                }

                Node startNode = startDelivery.getNode();

                // For the first time slot, the path with the warehouse must be found too
                if (numTimeSlot == 0) {
                    graph.addPath(map.getFastestPath(warehouse, startNode));
                } else {
                    // Compute the path between the delivery and the deliveries of the
                    // previous time slot (if it exists)
                    for (Delivery prevDelivery : timeSlots.get(numTimeSlot - 1).getDeliveries()) {
                        graph.addPath(map.getFastestPath(prevDelivery.getNode(), startNode));
                    }
                }

                // For the last time slot, the path with the warehouse muste be found, 
                // in order to come back.
                if (numTimeSlot == maxTimeSlot) {
                    graph.addPath(map.getFastestPath(startNode, warehouse));
                }

                // Find the fastest path between the current delivery and the
                // other deliveries in the same time slot
                for (Delivery nextDelivery : ts.getDeliveries()) {
                    if (isInterrupted()) {
                        return;
                    }

                    if (!startDelivery.equals(nextDelivery)) {
                        Path fastestPath = map.getFastestPath(startNode, nextDelivery.getNode());
                        graph.addPath(fastestPath);
                    }
                }
            }
        }

        if (isInterrupted()) {
            return;
        }

        // Compute the TSP algorithm on the new path graph
        ITSP tsp = new RouteTSP();

        long tempsDebut = System.currentTimeMillis();
        Integer[] solutions = null;

        try {
            solutions = tsp.computeSolution(3600000, graph);
        } catch (InterruptedException ex) {
            return;
        }

        System.out.print("Solution de longueur " + tsp.getSolutionCost() + " trouvee en " + (System.currentTimeMillis() - tempsDebut) + "ms : ");

        // Recreate the path to create the Route
        int nbEdges = graph.getNbEdges();
        LinkedList<Path> paths = new LinkedList<>();

        for (int i = 0, j = 1; j < nbEdges; ++i, ++j) {
            if (isInterrupted()) {
                return;
            }
            paths.add(graph.indexAsPath(solutions[i], solutions[j]));
        }
        if (isInterrupted()) {
            return;
        }
        paths.add(graph.indexAsPath(solutions[nbEdges - 1], solutions[0]));

        for (int i = 0; i < graph.getNbEdges(); ++i) {
            System.out.print(solutions[i] + " ");
        }

        if (isInterrupted()) {
            return;
        }

        Route route = new Route(paths);

        if (isInterrupted()) {
            return;
        }

        planning.setRoute(route);
    }

}
