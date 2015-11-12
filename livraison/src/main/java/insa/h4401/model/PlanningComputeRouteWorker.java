package insa.h4401.model;

import insa.h4401.utils.DefaultTSP;
import insa.h4401.utils.ITSP;
import javafx.concurrent.Task;
import javafx.event.EventHandler;

import java.util.LinkedList;
import java.util.List;
import javafx.concurrent.WorkerStateEvent;

/**
 * Worker Thread used to compute a route of a planning. Can be interrupted.
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
class PlanningComputeRouteWorker extends Task<Void> {

    /**
     * Timeout computation time, in seconds.
     * The default time out is of an hour, because the task can be interrupted.
     */
    private final int TIME_LIMIT = 3600000;

    /**
     * The planning to work with.
     */
    private final Planning planning;

    /**
     * Constructor of the worker thread.
     *
     * @param planning The planning to use to compute the route.
     * @param handler  The handler to notify when the task ends.
     */
    public PlanningComputeRouteWorker(Planning planning, EventHandler<WorkerStateEvent> handler) {
        this.planning = planning;
        setOnSucceeded(handler);
    }

    /**
     * Constructor of the worker thread, without handler to notify when the task
     * is done.
     *
     * @param planning The planning to use to compute the route.
     */
    public PlanningComputeRouteWorker(Planning planning) {
        this.planning = planning;
    }
    
    /**
     * Create a new Path Graph with all the path solutions, to do all the
     * deliveries.
     * 
     * @return The PathGraph computed.
     */
    private PathGraph computeSolutionsGraph() {
        PathGraph graph = new PathGraph();
        
        Map map = planning.getMap();
        List<TimeSlot> timeSlots = planning.getTimeSlots();
        Node warehouse = planning.getWarehouse();
        
        // Time slots processing
        for (int numTimeSlot = 0, maxTimeSlot = timeSlots.size() - 1;
             numTimeSlot <= maxTimeSlot; ++numTimeSlot) {

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
        
        return graph;
    }

    @Override
    protected Void call() throws Exception {        
        Map map = planning.getMap();
        map.resetArcs();
        
        PathGraph graph = computeSolutionsGraph();        

        // Compute the TSP algorithm on the new path graph
        ITSP tsp = new DefaultTSP();

        Integer[] solutions;

        try {
            solutions = tsp.computeSolution(TIME_LIMIT, graph);
        } catch (InterruptedException ex) {
            return null;
        }

        if (solutions == null || solutions[0] == null) {
            throw new ArithmeticException("Any route can't be found with the current map and planning");
        }

        // Recreate the paths to create the Route
        int nbArcs = graph.getNbArcs();
        LinkedList<Path> paths = new LinkedList<>();

        for (int i = 0, j = 1; j < nbArcs; ++i, ++j) {
            paths.add(graph.indexAsPath(solutions[i], solutions[j]));
        }

        paths.add(graph.indexAsPath(solutions[nbArcs - 1], solutions[0]));

        Route route = new Route(planning, paths);

        planning.setRoute(route);
        
        // Return null required by the Task<Void> extend.
        return null;
    }

}
