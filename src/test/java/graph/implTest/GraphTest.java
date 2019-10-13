package graph.implTest;
import static org.junit.Assert.*;

import graph.Graph;
import org.junit.Before;
import org.junit.Test;

import org.junit.Rule;
import org.junit.rules.Timeout;
import poly.CheckAsserts;

import java.util.*;

public class GraphTest {
    @Rule public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    /** checks that Java asserts are enabled, and exits if not */
    @Before
    public void testAssertsEnabled() {
        CheckAsserts.checkAssertsEnabled();
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  Constructor
    ///////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void testGraphEmpty(){
        Graph testGraph = new Graph();
        assertTrue(testGraph.isEmpty());
    }
    ///////////////////////////////////////////////////////////////////////////////////////
    ////  addNode(AdN) Test
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testAdNExistNode(){
        Graph<String,String> testGraph = new Graph<>();
        assertTrue(testGraph.addNode("n1"));
        assertTrue(testGraph.addNode("n2"));
        assertFalse(testGraph.addNode("n2"));
        assertTrue(testGraph.addNode(" n2"));
        assertTrue(testGraph.addNode("_n2"));
        assertEquals(testGraph.getParentNodeSet().size(),4);
        assertTrue(testGraph.getParentNodeSet().contains(" n2"));
        assertTrue(testGraph.getParentNodeSet().contains("_n2"));

    }

    @Test
    public void testAdNNullNode(){
        Graph<String,String> testGraph = new Graph<>();
        assertFalse(testGraph.addNode(null));
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  addEdge(AdE) Test
    ///////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void testAdEChildIsNotFound(){
        Graph<String,String> testGraph = new Graph<>();
        testGraph.addNode("n1");
        assertFalse(testGraph.getParentNodeSet().contains("n2"));
        testGraph.addEdge("n1","n2","n1->n2");
        assertTrue(testGraph.getParentNodeSet().contains("n2"));
        assertTrue(testGraph.getChildrenSet("n1").contains("n2"));
        Graph.Edge<String,String> edge = new Graph.Edge<>("n1","n2","n1->n2");
        assertTrue(testGraph.getEdgeSet("n1").contains(edge));
    }

    @Test
    public void testAdEParameterNull(){
        Graph<String,String> testGraph = new Graph<>();
        assertFalse(testGraph.addEdge(null,null,null));
        assertTrue(testGraph.getParentNodeSet().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAdEParentIsNotFound(){
        Graph<String,String> testGraph = new Graph<>();
        Graph.Edge<String,String> edge = new Graph.Edge<>("n1","n2","n1->n2");
        testGraph.addNode("n2");
        assertFalse(testGraph.addEdge("n1","n2","n1->n2"));
        assertFalse(testGraph.getEdgeSet("n1").contains(edge));
        assertFalse(testGraph.getParentNodeSet().contains("n1"));
        assertTrue(testGraph.getParentNodeSet().contains("n2"));
    }

    @Test
    public void testAdESelfPointed(){
        Graph<String,String> testGraph = new Graph<>();
        Graph.Edge<String,String> edge = new Graph.Edge<>("n1","n1","n1->n1");
        testGraph.addNode("n1");
        testGraph.addEdge("n1","n1","n1->n1");
        assertTrue(testGraph.getChildrenSet("n1").contains("n1"));
        assertTrue(testGraph.getEdgeSet("n1").contains(edge));
        assertTrue(testGraph.getParentNodeSet().contains("n1"));
    }

    @Test
    public void testAdECyclicGraph(){
        Graph<String,String> testGraph = new Graph<>();
        Graph.Edge<String,String> edge1 = new Graph.Edge<>("n1","n2","n1->n2");
        Graph.Edge<String,String> edge2 = new Graph.Edge<>("n2","n3","n2->n3");
        Graph.Edge<String,String> edge3 = new Graph.Edge<>("n3","n1","n3->n1");
        Graph.Edge<String,String> edge4 = new Graph.Edge<>("n1","n3","n1->n3");
        assertTrue(testGraph.addNode("n1"));
        assertTrue(testGraph.addNode("n2"));
        assertTrue(testGraph.addNode("n3"));

        assertTrue(testGraph.addEdge("n1","n2","n1->n2"));
        assertTrue(testGraph.addEdge("n2","n3","n2->n3"));
        assertTrue(testGraph.addEdge("n3","n1","n3->n1"));
        assertTrue(testGraph.addEdge("n1","n3","n1->n3"));

        assertEquals(3,testGraph.getParentNodeSet().size());
        assertTrue(testGraph.getParentNodeSet().contains("n1"));
        assertTrue(testGraph.getParentNodeSet().contains("n2"));
        assertTrue(testGraph.getParentNodeSet().contains("n3"));

        assertEquals(2,testGraph.getEdgeSet("n1").size());
        assertEquals(1,testGraph.getEdgeSet("n2").size());
        assertEquals(1,testGraph.getEdgeSet("n3").size());
        assertTrue(testGraph.getEdgeSet("n1").contains(edge1));
        assertTrue(testGraph.getEdgeSet("n2").contains(edge2));
        assertTrue(testGraph.getEdgeSet("n3").contains(edge3));
        assertTrue(testGraph.getEdgeSet("n1").contains(edge4));

        assertTrue(testGraph.getChildrenSet("n1").contains("n2"));
        assertTrue(testGraph.getChildrenSet("n2").contains("n3"));
        assertTrue(testGraph.getChildrenSet("n3").contains("n1"));
        assertTrue(testGraph.getChildrenSet("n1").contains("n3"));

    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  getChildrenSet(gCS) Tests
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void testgCSParentNotExist(){
        Graph<String,String> testGraph = new Graph<>();
        testGraph.getChildrenSet("n1");
    }

    @Test
    public void testgCSNoChildren(){
        Graph<String,String> testGraph = new Graph<>();
        // Graph.GraphNode n1 = testGraph.new GraphNode("n1");
        testGraph.addNode("n1");
        assertTrue(testGraph.getChildrenSet("n1").isEmpty());
    }

    @Test
    public void testgCSSelfPointed(){
        Graph<String,String> testGraph = new Graph<>();
        testGraph.addNode("n1");
        testGraph.addNode("n2");
        testGraph.addEdge("n1","n1","n1->n1");
        testGraph.addEdge("n1","n2","n1->n2");
        assertTrue(testGraph.getChildrenSet("n1").contains("n1"));
        assertTrue(testGraph.getChildrenSet("n1").contains("n2"));
    }

    @Test
    public void testgCSCyclicGraph(){
        Graph<String,String> testGraph = new Graph<>();
        Graph.Edge<String,String> edge1 = new Graph.Edge<>("n1","n2","n1->n2");
        Graph.Edge<String,String> edge2 = new Graph.Edge<>("n2","n3","n2->n3");
        Graph.Edge<String,String> edge3 = new Graph.Edge<>("n3","n1","n3->n1");
        Graph.Edge<String,String> edge4 = new Graph.Edge<>("n1","n3","n1->n3");
        assertTrue(testGraph.addNode("n1"));
        assertTrue(testGraph.addNode("n2"));
        assertTrue(testGraph.addNode("n3"));

        assertTrue(testGraph.addEdge("n1","n2","n1->n2"));
        assertTrue(testGraph.addEdge("n2","n3","n2->n3"));
        assertTrue(testGraph.addEdge("n3","n1","n3->n1"));
        assertTrue(testGraph.addEdge("n1","n3","n1->n3"));

        assertTrue(testGraph.getChildrenSet("n1").contains("n2"));
        assertEquals(testGraph.getChildrenSet("n1").size(),2);
        assertTrue(testGraph.getChildrenSet("n1").contains("n3"));
        assertTrue(testGraph.getChildrenSet("n2").contains("n3"));
        assertTrue(testGraph.getChildrenSet("n3").contains("n1"));
        assertEquals(testGraph.getChildrenSet("n2").size(),1);
        assertEquals(testGraph.getChildrenSet("n3").size(),1);
    }


    ///////////////////////////////////////////////////////////////////////////////////////
    ////  getEdgeSet Test
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void testgESTPParentNotExist(){
        Graph<String,String> testGraph = new Graph<>();
        // Graph.GraphNode n1 = testGraph.new GraphNode("n1");
        testGraph.getEdgeSet("n1");
    }

    @Test
    public void testgESTPNoChildren(){
        Graph<String,String> testGraph = new Graph<>();
        testGraph.addNode("n1");
        assertTrue(testGraph.getEdgeSet("n1").isEmpty());
    }

    @Test
    public void testgESTPSelfPointed(){
        Graph<String,String> testGraph = new Graph<>();
        Graph.Edge<String,String> edge1 = new Graph.Edge<>("n1","n1","n1->n1");
        Graph.Edge<String,String> edge2 = new Graph.Edge<>("n1","n2","n1->n2");
        testGraph.addNode("n1");
        testGraph.addNode("n2");
        testGraph.addEdge("n1","n1","n1->n1");
        testGraph.addEdge("n1","n2","n1->n2");
        assertTrue(testGraph.getEdgeSet("n1").contains(edge1));
        assertTrue(testGraph.getEdgeSet("n1").contains(edge2));
    }

    @Test
    public void testgESTPCyclicGraph(){
        Graph<String,String> testGraph = new Graph<>();
        Graph.Edge<String,String> edge1 = new Graph.Edge<>("n1","n2","n1->n2");
        Graph.Edge<String,String> edge2 = new Graph.Edge<>("n2","n3","n2->n3");
        Graph.Edge<String,String> edge3 = new Graph.Edge<>("n3","n1","n3->n1");
        Graph.Edge<String,String> edge4 = new Graph.Edge<>("n1","n3","n1->n3");
        assertTrue(testGraph.addNode("n1"));
        assertTrue(testGraph.addNode("n2"));
        assertTrue(testGraph.addNode("n3"));

        assertTrue(testGraph.addEdge("n1","n2","n1->n2"));
        assertTrue(testGraph.addEdge("n2","n3","n2->n3"));
        assertTrue(testGraph.addEdge("n3","n1","n3->n1"));
        assertTrue(testGraph.addEdge("n1","n3","n1->n3"));

        assertTrue(testGraph.getEdgeSet("n1").contains(edge1));
        assertTrue(testGraph.getEdgeSet("n1").contains(edge4));
        assertEquals(testGraph.getEdgeSet("n1").size(),2);
        assertTrue(testGraph.getEdgeSet("n2").contains(edge2));
        assertTrue(testGraph.getEdgeSet("n3").contains(edge3));
        assertEquals(testGraph.getEdgeSet("n2").size(),1);
        assertEquals(testGraph.getEdgeSet("n3").size(),1);
    }


}

