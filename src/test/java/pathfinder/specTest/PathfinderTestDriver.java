/*
 * Copyright ©2019 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2019 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package pathfinder.specTest;

import graph.Graph;
import pathfinder.datastructures.DijkstraFinder;
import pathfinder.datastructures.Path;

import java.io.*;
import java.util.*;

/**
 * This class implements a test driver that uses a script file format
 * to test an implementation of Dijkstra's algorithm on a graph.
 */
public class PathfinderTestDriver {
  /*
   * Copyright ©2019 Hal Perkins.  All rights reserved.  Permission is
   * hereby granted to students registered for University of Washington
   * CSE 331 for use solely during Spring Quarter 2019 for purposes of
   * the course.  No other use, copying, distribution, or modification
   * is permitted without prior written consent. Copyrights for
   * third-party components of this work must be honored.  Instructors
   * interested in reusing these course materials should contact the
   * author.
   */

    public static void main(String args[]) {
      try {
        if (args.length > 1) {
          printUsage();
          return;
        }

        pathfinder.specTest.PathfinderTestDriver td;

        if (args.length == 0) {
          td = new pathfinder.specTest.PathfinderTestDriver(new InputStreamReader(System.in),
                  new OutputStreamWriter(System.out));
        } else {

          String fileName = args[0];
          File tests = new File (fileName);

          if (tests.exists() || tests.canRead()) {
            td = new pathfinder.specTest.PathfinderTestDriver(new FileReader(tests),
                    new OutputStreamWriter(System.out));
          } else {
            System.err.println("Cannot read from " + tests.toString());
            printUsage();
            return;
          }
        }

        td.runTests();

      } catch (IOException e) {
        System.err.println(e.toString());
        e.printStackTrace(System.err);
      }
    }

    private static void printUsage() {
      System.err.println("Usage:");
      System.err.println("to read from a file: java graph.specTest.MarvelTestDriver <name of input script>");
      System.err.println("to read from standard in: java graph.specTest.MarvelTestDriver");
    }

    /** String -> Graph: maps the names of graphs to the actual graph **/
    //TODO for the student: Parameterize the next line correctly.
    private final Map<String, Graph<String,Double>> graphs = new HashMap<>();
    private final PrintWriter output;
    private final BufferedReader input;

    /**
     * @requires r != null && w != null
     *
     * @effects Creates a new MarvelTestDriver which reads command from
     * <tt>r</tt> and writes results to <tt>w</tt>.
     **/
    public PathfinderTestDriver(Reader r, Writer w) {
      input = new BufferedReader(r);
      output = new PrintWriter(w);
    }

    /**
     * @effects Executes the commands read from the input and writes results to the output
     * @throws IOException if the input or output sources encounter an IOException
     **/
    public void runTests()
            throws IOException
    {
      String inputLine;
      while ((inputLine = input.readLine()) != null) {
        if ((inputLine.trim().length() == 0) ||
                (inputLine.charAt(0) == '#')) {
          // echo blank and comment lines
          output.println(inputLine);
        }
        else
        {
          // separate the input line on white space
          StringTokenizer st = new StringTokenizer(inputLine);
          if (st.hasMoreTokens()) {
            String command = st.nextToken();

            List<String> arguments = new ArrayList<String>();
            while (st.hasMoreTokens()) {
              arguments.add(st.nextToken());
            }

            executeCommand(command, arguments);
          }
        }
        output.flush();
      }
    }

    private void executeCommand(String command, List<String> arguments) {
      try {
        if (command.equals("CreateGraph")) {
          createGraph(arguments);
        } else if (command.equals("AddNode")) {
          addNode(arguments);
        } else if (command.equals("AddEdge")) {
          addEdge(arguments);
        } else if (command.equals("ListNodes")) {
          listNodes(arguments);
        } else if (command.equals("ListChildren")) {
          listChildren(arguments);
        } else if (command.equals("FindPath")){
          findPath(arguments);
        } else {
          output.println("Unrecognized command: " + command);
        }
      } catch (Exception e) {
        output.println("Exception: " + e.toString());
      }
    }

    private void createGraph(List<String> arguments) {
      if (arguments.size() != 1) {
        throw new pathfinder.specTest.PathfinderTestDriver.CommandException("Bad arguments to CreateGraph: " + arguments);
      }

      String graphName = arguments.get(0);
      createGraph(graphName);
    }

    private void createGraph(String graphName) {
      Graph<String,Double> graph = new Graph<>();
      graphs.put(graphName,graph);
      output.println("created graph "+graphName);
    }

    private void addNode(List<String> arguments) {
      if (arguments.size() != 2) {
        throw new pathfinder.specTest.PathfinderTestDriver.CommandException("Bad arguments to addNode: " + arguments);
      }

      String graphName = arguments.get(0);
      String nodeName = arguments.get(1);

      addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
      Graph<String,Double> graph = graphs.get(graphName);
      graph.addNode(nodeName);
      output.println("added node "+nodeName+" to "+graphName);
    }

    private void addEdge(List<String> arguments) {
      if (arguments.size() != 4) {
        throw new pathfinder.specTest.PathfinderTestDriver.CommandException("Bad arguments to addEdge: " + arguments);
      }

      String graphName = arguments.get(0);
      String parentName = arguments.get(1);
      String childName = arguments.get(2);
      Double edgeLabel = Double.parseDouble(arguments.get(3));

      addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
                         Double edgeLabel) {
      Graph<String,Double> graph  = graphs.get(graphName);
      graph.addEdge(parentName,childName,edgeLabel);
      output.println(String.format("added edge %.3f",edgeLabel)+" from "+parentName+" to "+childName+" in "+graphName);
    }

    private void listNodes(List<String> arguments) {
      if (arguments.size() != 1) {
        throw new pathfinder.specTest.PathfinderTestDriver.CommandException("Bad arguments to listNodes: " + arguments);
      }

      String graphName = arguments.get(0);
      listNodes(graphName);
    }

    private void listNodes(String graphName) {
      Graph<String,Double> graph = graphs.get(graphName);
      output.print(graphName+" contains:");
      Set<String> sortSet = new TreeSet<>(graph.getParentNodeSet());
      for(String node: sortSet){
        output.print(" "+ node);
      }
      output.println("");
    }

    private void listChildren(List<String> arguments) {
      if (arguments.size() != 2) {
        throw new pathfinder.specTest.PathfinderTestDriver.CommandException("Bad arguments to listChildren: " + arguments);
      }

      String graphName = arguments.get(0);
      String parentName = arguments.get(1);
      listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {
      Graph<String,Double> graph = graphs.get(graphName);
      output.print("the children of "+parentName+" in "+graphName+" are:");
      Set<Graph.Edge<String,Double>> edgeSetCopy= graph.getEdgeSet(parentName);
      Set<String> childEdgePairSet = new TreeSet<>();
      for (Graph.Edge<String,Double> edge: edgeSetCopy){
        String childEdgePair = " "+edge.getEndNode()+"("+edge.getLabel()+")";
        childEdgePairSet.add(childEdgePair);
      }
      for(String childEdgePair :childEdgePairSet){
        output.print(childEdgePair);
      }
      output.println("");

    }

    private void findPath(List<String> arguments){
      String graphName = arguments.get(0);
      String starChar = arguments.get(1);
      //starChar=starChar.replace('_',' ');
      String destChar = arguments.get(2);
      //destChar=destChar.replace('_',' ');
      Graph<String,Double> graph = graphs.get(graphName);
      if(!graph.containsNode(starChar)){
        output.println("unknown node "+starChar);
      }else if(!graph.containsNode(destChar)){
        output.println("unknown node "+destChar);
      }else {
        DijkstraFinder<String> newfinder = new DijkstraFinder<>();
        Path<String> leastPath = newfinder.findPath(starChar, destChar, graph);
        output.println("path from " + starChar + " to " + destChar + ":");
        if (leastPath == null) {
          output.println("no path found");
        } else {
          for (Path.Segment segment : leastPath) {
            output.println(segment.getStart() + " to " + segment.getEnd() + String.format(" with weight %.3f", segment.getCost()));
          }
          output.println(String.format("total cost: %.3f" ,leastPath.getCost()));
        }
      }
    }

    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

      public CommandException() {
        super();
      }
      public CommandException(String s) {
        super(s);
      }

      public static final long serialVersionUID = 3495;
    }
}
