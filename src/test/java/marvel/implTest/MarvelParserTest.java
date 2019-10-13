package marvel.implTest;

import graph.Graph;
import marvel.MarvelParser;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import poly.CheckAsserts;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MarvelParserTest {
    @Rule
    public Timeout globalTimeout = Timeout.seconds(30); // 10 seconds max per method tested

    /** checks that Java asserts are enabled, and exits if not */
    @Before
    public void testAssertsEnabled() {
        CheckAsserts.checkAssertsEnabled();
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  ParserTest
    ///////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void testParser(){
        Map<String,ArrayList<String>> result = MarvelParser.parseData("/Users/xuanhezhou/Downloads/cse331-19sp-xuanhz/src/main/resources/marvel/data/marvelTest.tsv");
        assertTrue(result.containsKey("Turtle"));
        assertTrue(result.containsKey("Cat"));
        assertTrue(result.containsKey("Pig"));
        assertTrue(result.containsKey("Snake"));
        assertTrue(result.containsKey("Jellyfish"));

    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  GraphTest
    ///////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void testGraphCreation(){
        MarvelParser parser = new MarvelParser();
        Graph<String,String> newGraph = parser.createGraph("/Users/xuanhezhou/Downloads/cse331-19sp-xuanhz/src/main/resources/marvel/data/marvelTest.tsv");
        Set<String> ExpectedNodeSet = new HashSet<>();
        ExpectedNodeSet.add("Turtle");
        ExpectedNodeSet.add("Cat");
        ExpectedNodeSet.add("Pig");
        ExpectedNodeSet.add("Snake");
        ExpectedNodeSet.add("Jellyfish");
        assertEquals(ExpectedNodeSet,newGraph.getParentNodeSet());
        Graph.Edge v1 = new Graph.Edge<>("Turtle","Cat","Vertebrate");
        Graph.Edge v1_ = new Graph.Edge<>("Cat","Turtle","Vertebrate");
        Graph.Edge v2 = new Graph.Edge<>("Turtle","Pig","Vertebrate");
        Graph.Edge v2_ = new Graph.Edge<>("Pig","Turtle","Vertebrate");
        Graph.Edge v3 = new Graph.Edge<>("Cat","Pig","Vertebrate");
        Graph.Edge v3_ = new Graph.Edge<>("Pig","Cat","Vertebrate");
        Graph.Edge v4 = new Graph.Edge<>("Cat","Pig","Mammal");
        Graph.Edge v4_ = new Graph.Edge<>("Pig","Cat","Mammal");
        Graph.Edge v5 = new Graph.Edge<>("Turtle","Snake","Reptiles");
        Graph.Edge v5_ = new Graph.Edge<>("Snake","Turtle","Reptiles");
        Set<Graph.Edge> turtleEdge = new HashSet<>();
        turtleEdge.add(v1);
        turtleEdge.add(v2);
        turtleEdge.add(v5);
        Set<Graph.Edge> catEdge = new HashSet<>();
        catEdge.add(v1_);
        catEdge.add(v3);
        catEdge.add(v4);
        Set<Graph.Edge> pigEdge = new HashSet<>();
        pigEdge.add(v2_);
        pigEdge.add(v3_);
        pigEdge.add(v4_);
        Set<Graph.Edge> snakeEdge = new HashSet<>();
        snakeEdge.add(v5_);
        Map<String,Set<Graph.Edge>> graph = new HashMap<>();
        graph.put("Turtle",turtleEdge);
        graph.put("Cat",catEdge);
        graph.put("Pig",pigEdge);
        graph.put("Snake",snakeEdge);
        graph.put("Jellyfish",new HashSet<>());
        for(String node:newGraph.getParentNodeSet()){
            assertEquals(graph.get(node),newGraph.getEdgeSet(node));
        }




    }

}
