package pathfinder.implTest;

import org.junit.Test;
import pathfinder.datastructures.DijkstraFinder;
import pathfinder.datastructures.Point;
import graph.Graph;
import pathfinder.datastructures.Path;
import pathfinder.parser.CampusBuilding;

import static org.junit.Assert.assertEquals;


public class TestDijkstraFinder {

    //Test two path with same cost
    @Test
    public void testPathwithSameCost(){
        Graph<Point,Double> graph = new Graph<>();
        Point node1 = new Point(1.0,1.1);
        Point node2 = new Point(1.2,1.3);
        Point node3 = new Point(1.4,1.5);
        Point node4 = new Point(1.6,1.7);
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        graph.addEdge(node1,node2,1.2);
        graph.addEdge(node2,node3,1.8);
        graph.addEdge(node2,node4,2.3);
        graph.addEdge(node1,node3,2.0);
        DijkstraFinder<Point> dijkstraFinder = new DijkstraFinder<>();
        Path<Point> leastPath = dijkstraFinder.findPath(node1,node3,graph);
        Path<Point> expectedPath1 = new Path<>(node1);
        expectedPath1 = expectedPath1.extend(node3,2.000);
        assertEquals(expectedPath1,leastPath);
    }

//    @Test
//    public void testBuilding(){
//        Graph<CampusBuilding,Double> graph = new Graph<>();
//        CampusBuilding building1 = new CampusBuilding("a","apple",1.0,1.1);
//        CampusBuilding building2 = new CampusBuilding("b", "bear", 1.2,1.3);
//        CampusBuilding building3 = new CampusBuilding("c", "cat", 1.4,1.5);
//        CampusBuilding building4 = new CampusBuilding("d", "dog",1.6,1.7);
//        graph.addNode(building1);
//        graph.addNode(building2);
//        graph.addNode(building3);
//        graph.addNode(building4);
//        DijkstraFinder<CampusBuilding> dijkstraFinder = new DijkstraFinder<>();
//        Path<CampusBuilding> leastPath = dijkstraFinder.findPath(building1,building3,graph);
//        Path<CampusBuilding> expectedPath1 = new Path<>(building1);
//        expectedPath1 = expectedPath1.extend(building3,2.000);
//        assertEquals(expectedPath1,leastPath);
//
//    }

}
