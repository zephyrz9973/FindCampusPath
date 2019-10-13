package marvel.implTest;

import graph.Graph;
import marvel.MarvelPaths;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import poly.CheckAsserts;
import java.util.*;

import static org.junit.Assert.*;

public class MarvelPathsTest {
    @Rule
    public Timeout globalTimeout = Timeout.seconds(30); // 10 seconds max per method tested

    /** checks that Java asserts are enabled, and exits if not */
    @Before
    public void testAssertsEnabled() {
        CheckAsserts.checkAssertsEnabled();
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  find Path test
    ///////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void testNoPath(){
        Graph<String,String> newGraph = new Graph<>();
        newGraph.addNode("n1");
        newGraph.addNode("n2");
        newGraph.addNode("n3");
        System.out.println(1);
        newGraph.addEdge("n1","n2","a");
        newGraph.addEdge("n1","n3","b");
        MarvelPaths marvelPaths = new MarvelPaths();
        System.out.println(2);
        assertNull(marvelPaths.findPath("n2","n3",newGraph));
    }

    @Test
    public void testCyclicGraph(){
        Graph<String,String> newGraph = new Graph<>();
        newGraph.addNode("n1");
        newGraph.addNode("n2");
        newGraph.addNode("n3");
        Graph.Edge<String,String> edge1 = new Graph.Edge<>("n1","n2","a");
        newGraph.addEdge("n1","n2","a");
        Graph.Edge<String,String> edge2 = new Graph.Edge<>("n2","n3","b");
        newGraph.addEdge("n2","n3","b");
        Graph.Edge<String,String> edge3 = new Graph.Edge<>("n3","n1","c");
        newGraph.addEdge("n3","n1","c");
        List<Graph.Edge> path= new ArrayList<>();
        path.add(edge1);
        path.add(edge2);
        path.add(edge3);
        MarvelPaths marvelPaths =new MarvelPaths();
        assertEquals(marvelPaths.findPath("n1","n3",newGraph),path);
    }
}
