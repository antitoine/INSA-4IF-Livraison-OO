package insa.h4401.model;

import insa.h4401.utils.IGraph;

import java.util.HashMap;

/**
 * This class represents a graph with vertices representing Path object, 
 * allowing to find the best way to visit each node thanks to a ITSP algorithm
 * for example.
 * 
 * This class implements the IGraph interface, used bye the ITSP algorithm.
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 * @see IGraph
 */
public class PathGraph implements IGraph {

    /**
     * Graph represented by a map, with :
     * - Key : The id of the initial vertex
     * - Value : The terminal verticies, associated with their Path object.
     */
    private HashMap<Integer, HashMap<Integer, Path>> graph;

    /**
     * Map with the association of a vertice number and its id :
     * - Key : Vertex number (0 started)
     * - Value : Vertex id, ie Node object id.
     */
    private HashMap<Integer, Integer> nodes;

    /**
     * The number of vertices stored in the graph.
     */
    private int graphSize;

    /**
     * Constructs an empty Path Graph.
     */
    public PathGraph() {
        graph = new HashMap<>();
        nodes = new HashMap<>();
        graphSize = 0;
    }

    /**
     * @return The total count of arcs in the graph.
     */
    @Override
    public int getNbArcs() {
        return graph.keySet().size();
    }

    /**
     * Returns the cost of the walk between two vertices, representing by their
     * number. The number of a vertex correspond to its insert order in the
     * graph.
     *
     * @param firstVertexNumber The number (0 started) of the first vertex.
     * @param secondVertexNumber The number (0 started) of the second vertex.
     * 
     * @return The cost of the walk between those two vertices. The cost is the
     * duration associated with the Path object related to the vertices. 
     */
    @Override
    public float getCost(int firstVertexNumber, int secondVertexNumber) {
        if (!nodes.containsKey(firstVertexNumber) || !nodes.containsKey(secondVertexNumber)) {
            return -1;
        }

        // Convert the index to ids
        int srcId = nodes.get(firstVertexNumber);
        int destId = nodes.get(secondVertexNumber);

        HashMap<Integer, Path> initialVertex = graph.get(srcId);

        if (initialVertex == null) {
            return -1;
        }

        Path path = initialVertex.get(destId);

        return (path == null) ? -1 : path.getPathDuration();
    }
    
    /**
     * Checks if an arc exists in the graph.
     *
     * @param initialVertexNumber The number of the initial vertex. (starts to 0)
     * @param terminalVertexNumber The number of the terminal vertex. (starts to 0)
     * 
     * @return True if the directed arc exists, false otherwise.
     */
    @Override
    public boolean isArc(int initialVertexNumber, int terminalVertexNumber) {
        if (!nodes.containsKey(initialVertexNumber) 
                || !nodes.containsKey(terminalVertexNumber)) {
            return false;
        }

        // Convert the index to ids
        int srcId = nodes.get(initialVertexNumber);
        int destId = nodes.get(terminalVertexNumber);

        HashMap<Integer, Path> initialVertex = graph.get(srcId);

        return initialVertex != null && initialVertex.containsKey(destId);
    }

    /**
     * Adds a Path to the current graph. Ignore it if path is null.
     *
     * @param path The Path to add.
     */
    public void addPath(Path path) {
        if (path == null) {
            return;
        }

        Node firstNode = path.getFirstNode();

        if (!nodes.containsValue(firstNode.getId())) {
            nodes.put(graphSize, firstNode.getId());
            graphSize++;
        }

        Node lastNode = path.getLastNode();

        if (!nodes.containsValue(lastNode.getId())) {
            nodes.put(graphSize, lastNode.getId());
            graphSize++;
        }

        HashMap<Integer, Path> initialVertex = graph.get(firstNode.getId());

        if (initialVertex == null) {
            initialVertex = new HashMap<>();
            graph.put(firstNode.getId(), initialVertex);
        }

        initialVertex.put(lastNode.getId(), path);
    }

    /**
     * Finds the path associated with the first node having the initialVertexNumber
     * number and the last node having the terminalVertexNumber number.
     *
     * @param initialVertexNumber The number of the first node of the path to find.
     * @param terminalVertexNumber The number of the last node of the path to find.
     * @return The path if it exists, null otherwise.
     */
    public Path indexAsPath(int initialVertexNumber, int terminalVertexNumber) {
        if (!nodes.containsKey(initialVertexNumber) 
                || !nodes.containsKey(terminalVertexNumber)) {
            return null;
        }

        // Convert the index in ids
        int srcId = nodes.get(initialVertexNumber);
        int destId = nodes.get(terminalVertexNumber);

        HashMap<Integer, Path> initialVertex = graph.get(srcId);

        return (initialVertex == null) ? null : initialVertex.get(destId);
    }
}
