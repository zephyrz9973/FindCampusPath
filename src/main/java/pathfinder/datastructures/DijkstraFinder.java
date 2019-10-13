package pathfinder.datastructures;
import graph.Graph;
import java.util.*;

/**
 * This graph represent the idea of the minimum path finder using Dijkstra Algorithm
 *
 * Abstract invariant:
 * Nodes for no path known yet is not in the queue
 */
public class DijkstraFinder<Node> {
    /**
     * return the Path with the least cost using Dijkstra Algorithm
     * @param  start :Node //the start node of the path
     * @param  dest :Node //the end node of the path
     * @param  graph :Graph<Node, Double> //the graph with Double label
     * @param  graph has no negative weights
     * @return the set of edges represent the minimum cost path
     */
    public Path<Node> findPath(Node start, Node dest, Graph<Node, Double> graph) {

        //priority queue
        PriorityQueue<Path<Node>> active = new PriorityQueue<>(new PathComparator<>());
        // Each element is a path from start to a given node.
        // A path's “priority” in the queue is the total cost of that path.
        // Nodes for which no path is known yet are not in the queue.
        //A set of nodes for which we know the minimum-cost path from start
        Set<Node> finished = new HashSet<>();

        // Initially we only know of the path from start to itself, which has
        // a cost of zero because it contains no edges.
        Path<Node> startPath = new Path<>(start);
        startPath.extend(start, 0.0);
        active.add(startPath);

        while (!active.isEmpty()) {
            // minPath is the lowest-cost path in active and,
            // if minDest isn't already 'finished,' is the
            // minimum-cost path to the node minDest
            Path<Node> minPath = active.remove();
            Node MinDest = minPath.getEnd();

            if (dest.equals(MinDest)) {
                return minPath;
            }
            if (finished.contains(MinDest)) {
                continue;
            }
            for (Graph.Edge<Node, Double> edge : graph.getEdgeSet(MinDest)) {
                Node childNode = edge.getEndNode();
                if (!finished.contains(childNode)) {
                    Path<Node> newPath = minPath.extend(childNode, edge.getLabel());
                    active.add(newPath);
                }
            }
            finished.add(MinDest);
        }
        return null;

    }
}
