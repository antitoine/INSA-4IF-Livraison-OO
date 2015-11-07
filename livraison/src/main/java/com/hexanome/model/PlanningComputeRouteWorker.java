package com.hexanome.model;

import com.hexanome.utils.ITSP;
import java.util.LinkedList;
import java.util.List;
import javafx.concurrent.Task;

/**
 * Worker Thread used to compute a route of a planning. Can be interrupted.
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class PlanningComputeRouteWorker extends Task<Void> {

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
    protected Void call() throws Exception {
        PathGraph graph = new PathGraph();

        List<TimeSlot> timeSlots = planning.getTimeSlots();
        Node warehouse = planning.getWarehouse();
        Map map = planning.getMap();

        // Time slots processing
        for (int numTimeSlot = 0, maxTimeSlot = timeSlots.size() - 1; numTimeSlot <= maxTimeSlot; ++numTimeSlot) {

            TimeSlot ts = timeSlots.get(numTimeSlot);

            // Compute each delivery of the current time slot
            for (Delivery startDelivery : ts.getDeliveries()) {

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

                    if (!startDelivery.equals(nextDelivery)) {
                        Path fastestPath = map.getFastestPath(startNode, nextDelivery.getNode());
                        graph.addPath(fastestPath);
                    }
                }
            }
        }

        // Compute the TSP algorithm on the new path graph
        ITSP tsp = new RouteTSP();

        long tempsDebut = System.currentTimeMillis();
        Integer[] solutions = null;

        try {
            solutions = tsp.computeSolution(3600000, graph);
        } catch (InterruptedException ex) {
            return null;
        }
        
        if (solutions == null || solutions[0] == null) {
            throw new ArithmeticException("Any route can't be found with the current map and planning");
        }

        System.out.print("Solution de longueur " + tsp.getSolutionCost() + " trouvee en " + (System.currentTimeMillis() - tempsDebut) + "ms : ");

        // Recreate the path to create the Route
        int nbEdges = graph.getNbArcs();
        LinkedList<Path> paths = new LinkedList<>();

        for (int i = 0, j = 1; j < nbEdges; ++i, ++j) {
            paths.add(graph.indexAsPath(solutions[i], solutions[j]));
        }

        paths.add(graph.indexAsPath(solutions[nbEdges - 1], solutions[0]));

        for (int i = 0; i < graph.getNbArcs(); ++i) {
            System.out.print(solutions[i] + " ");
        }
        System.out.print("\n");

        Route route = new Route(planning, paths);

        planning.setRoute(route);
        return null;
    }

    @Override
    protected void succeeded() {
        super.succeeded();
        System.out.println("Done!");
    }

}
