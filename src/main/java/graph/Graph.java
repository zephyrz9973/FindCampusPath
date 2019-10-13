package graph;
import java.util.*;
/**
 * This class represents the idea of the graph
 * @spec.specfield graph: Map<Node,Set<Edge<Node,label>> //the graph represented by an adjacency list which maps parent node to edges
 *
 * Abstract Invariant:
 *    the graph can not be null
 *    if node in the graph have child node, the child node has to be in graph
 *
 */
public class Graph<Node,Label> {

    /**
     * The map of parent node to Edge
     */
    private Map<Node, Set<Edge<Node,Label>>> graph;


    // Abstraction Function:
    //   AF(r) = Map<ParenNode,EdgeSet of the ParentNode>
    //
    //
    // Representational Invariant:
    //      nodeSet!=null && graph !=null && edgeSet!=null
    //      if the edgeSet of the parentNode is not null, then every edge's endNode is also in the graph

    /**
     * Creates an empty new graph
     *
     * @spec.effects constructs an empty graph with empty nodesSet and edgeSet
     */
    public Graph() {
        this.graph = new HashMap<>();
        //checkRep();
    }

    /**
     * Add an Edge from parentNode to exist childNode with the given edgeLength
     *
     * @param parentNode : Type Node the parentNode
     * @param childNode  the childNode
     * @param label the label of edge from parent to child
     * @return true if successfully add, false if fail to add
     * @spec.effects if childNode do not exist create a child node, otherwise, create a new edge from parentNode to childNode with edgeLength and add the (edge, childNode) pair to the childAndedge list of the parent node
     */
    public boolean addEdge(Node parentNode, Node childNode, Label label) {
        //checkRep();
        if (!(this.containsNode(parentNode)) || parentNode == null || childNode == null || label == null) {
            //checkRep();
            return false;
        } else {
            if(!this.containsNode(childNode)){
                addNode(childNode);
            }
            Edge<Node,Label> edge = new Edge<>(parentNode,childNode,label);
            graph.get(parentNode).add(edge);
           // checkRep();
            return true;
        }
    }

    /**
     * Add a new node to the graph
     *
     * @param newNode : GraphNode // the node need to be add to graph
     * @return true if successfully add, false if fail to add
     * @spec.effects if node do not exist in graph, add a new node to the graph
     */
    public boolean addNode(Node newNode) {
        //checkRep();
        if (newNode == null || this.containsNode(newNode)) {
           // checkRep();
            return false;
        } else {
            Set<Edge<Node,Label>> emptyEdgeSet = new HashSet<>();
            graph.put(newNode, emptyEdgeSet);
            //checkRep();
            return true;
        }
    }



    /**
     * Return the set of parent nodes
     *
     * @return the graph represent by adjacency list
     */
    public Set<Node> getParentNodeSet(){
        //checkRep();
        return this.graph.keySet();
    }

    /**
     * Return all the Edge of the parentNode
     *
     * @param parentNode the node that need to be examined
     * @return the set containing all the edge connected to the parent node
     * @throws IllegalArgumentException if parentNode do not exist in the graph or it is null
     */
    public Set<Edge<Node,Label>> getEdgeSet(Node parentNode) {
       // checkRep();
        if (!(graph.keySet().contains(parentNode)) || parentNode == null) {
            throw new IllegalArgumentException("The parent node do not exist.");
        } else {
            return graph.get(parentNode);
        }
    }

    /**
     * Return all the direct child of the parentNode
     *
     * @param parentNode the node that need to be examined
     * @return the set containing all the children of the parentNode
     * @throws IllegalArgumentException if parentNode do not exist in the graph or it is null
     */
    public Set<Node> getChildrenSet(Node parentNode) {
        if (!(graph.containsKey(parentNode)) || parentNode == null) {
          //  checkRep();
            throw new IllegalArgumentException("The parent node do not exist.");
        } else {
            Set<Node> childNodeSet = new HashSet<>();
            for (Edge<Node,Label> valueEdge : graph.get(parentNode)) {
                childNodeSet.add(valueEdge.getEndNode());
            }
            return childNodeSet;
        }
    }

    /**
     * Check if node contain in the graph
     * @param node the node need to Check whether in the Graph
     * @return true if the node is in the graph false if not
     */
    public boolean containsNode(Node node){
        return graph.containsKey(node);
    }

    /**
     * Check if graph is empty
     * @return true if it is empty
     */
    public boolean isEmpty(){
        return graph.isEmpty();
    }

    /**
     * Check if the representation invariant is broken and throw exception if it is broken
     */
    // Abstraction Function:
    //   AF(r) = Map<Edge,ParentNode>
    //          for graphNode in graph and edges in graph
    //              nodesSet contains graphNode
    //              edgeSet contains edges
    //
    // Representational Invariant:
    //      nodeSet!=null && graph !=null && edgeSet!=null
    //      if node in nodeSet is in the graph
    //          for every edge that the nodes has,
    //               nodesSet contains the end node of the edge
//    private void checkRep() {
//        assert (graph != null) : "graph can not be null";
//        for (String parentNode : graph.keySet()) {
//            for (Edge edge : graph.get(parentNode)) {
//                assert (graph.containsKey(edge.getEndNode()));
//            }
//        }
//    }


    //------Starting Edge class----//

    /**
     * This class represents the idea of edges from parent to child.
     *
     * @spec.specfield label : int //labels on the edge
     * @spec.specfield parentNode : Node //the starting node of the edge is connect to this node
     * @spec.specfield childNode : Node //the ending node of the edge is connect to this node
     * <p>
     * Abstract Invariant:
     * startNode != null and endNode != null and both of them have to be in graph
     */
    public static class Edge<Node ,Label>{

        /**
         * The label on the edge
         */
        private Label label;

        /**
         * The start node (parent node)that edge is connect to
         */
        private Node startNode;

        /**
         * The end node (child node)that edge is connect to
         */
        private Node endNode;

        // Abstraction Function:
        //   AF(r) = an edge,e, start from parentNode to childNode with e.label = r.label
        //
        // Representational Invariant:
        //      label !=null && startNode != null && endNode !=null

        /**
         * Creates a new Edge with label = length from parentNode to childNode.
         *
         * @param parentNode the parentNode that edge connect from
         * @param childNode  the childNode that the edge connect to
         * @param label      the label of the edge
         * @spec.requires length greater than or equal to 0 parentNode != null and childNode != null and both of them can be found in graph
         * @spec.effects constructs an new Edge e with label = length and startNode = parentNode to endNode = childNode if parentNode = childNode, label = 0
         */
        public Edge(Node parentNode, Node childNode, Label label) {
            this.startNode = parentNode;
            this.endNode = childNode;
            this.label = label;
        }

        /**
         * Return the label of the edge
         *
         * @return label of the edge
         */
        public Label getLabel() {
            checkRep();
            return label;
        }

        /**
         * Return the startNode of the label
         *
         * @return startNode of the label
         */
        public Node getStartNode() {
            checkRep();
            return startNode;
        }

        /**
         * Return the endNode of the label
         *
         * @return endNode of the label
         */
        public Node getEndNode() {
            checkRep();
            return endNode;
        }

        /**
         * Decide if two edge is equal
         *
         * @param obj the object need to compare
         * @return true if the startNode is the same and endNode is the same and label is the same else false
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            } else if (obj instanceof Edge<?,?>) {
                Edge<?,?> edge = (Edge<?,?>) obj;
                return label.equals(edge.label)
                        && startNode.equals(edge.startNode)
                        && endNode.equals(edge.endNode);
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return label.hashCode();
        }

        private void checkRep() {
            assert (label != null) : "label is null";
            assert (startNode != null) : "start node is null";
            assert (endNode != null) : "end node is null";
        }

        /**
         * The comparator used to compare edges
         * @return if the nodes have the name return the comparation of edge else return the comparation of nodes
         */
    public class EdgeComparator implements Comparator<Edge<String,String>>{
        @Override
        public int compare(Edge<String,String> edge1, Edge<String,String> edge2){
            if(edge1.getEndNode().compareTo(edge2.getEndNode())==0){
                return edge1.getLabel().compareTo(edge2.getLabel());
            }else{
                return edge1.getEndNode().compareTo(edge2.getEndNode());}
        }
     }
    }
}


