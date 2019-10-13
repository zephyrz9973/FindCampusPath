package marvel;
import graph.Graph;

import java.util.*;

public class MarvelPaths {
//    public static void main(String[] args) {
////        MarvelParser newParser = new MarvelParser();
////        MarvelPaths newPath = new MarvelPaths();
////        Graph newGraph = newParser.createGraph("/Users/xuanhezhou/Downloads/cse331-19sp-xuanhz/src/main/resources/marvel/data/marvelTest.tsv");
////        List<Graph.Edge> pathEdges = newPath.findPath("Cat","Snake",newGraph);
////        for(Graph.Edge edge: pathEdges){
////            System.out.println(edge.getStartNode());
////            System.out.println(edge.getEndNode());
////            System.out.println("book"+edge.getLabel());
////        }
//    }

    public List<Graph.Edge<String,String>> findPath(String startChar, String destChar, Graph<String,String> graph) {
        if (startChar == null || !graph.containsNode(startChar)) {
            throw new IllegalArgumentException("unknown character "+startChar);
        } else if(destChar==null||!graph.containsNode(destChar)){
            throw new IllegalArgumentException("unknown character "+destChar);
        } else if(startChar.equals(destChar)){
            return new ArrayList<>();
        }
        else {
            //set up for path finding
            Queue<String> exploreNodes = new LinkedList<String>();
            Map<String, List<Graph.Edge<String,String>>> PathToNodes = new HashMap<>();
            exploreNodes.add(startChar);
            PathToNodes.put(startChar, new ArrayList<>());
            //explore for path
            while (!exploreNodes.isEmpty()) {
                String currentChar = exploreNodes.remove();
                if (currentChar.equals(destChar)) {
                    //found destination
                    return PathToNodes.get(currentChar);
                } else {
                    //put all child of that node
                    List<Graph.Edge<String,String>> EdgeSet = new ArrayList<>(graph.getEdgeSet(currentChar));
//                    Collections.sort(EdgeSet);
                    for (Graph.Edge<String,String> edge : EdgeSet) {
                        String childNode = edge.getEndNode();
                        if (!PathToNodes.containsKey(childNode)) {
                            List<Graph.Edge<String,String>> PathToCurrentChar = PathToNodes.get(currentChar);
                            List<Graph.Edge<String,String>> PathToUnvisitedChild = new ArrayList<>(PathToCurrentChar);
                            PathToUnvisitedChild.add(edge);
                            PathToNodes.put(childNode,PathToUnvisitedChild);
                            exploreNodes.add(childNode);
                        }
                    }
                }
            }
            return null;
        }
    }

}
