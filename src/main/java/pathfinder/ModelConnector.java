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

package pathfinder;

import pathfinder.datastructures.DijkstraFinder;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import pathfinder.parser.*;
import java.util.*;
import graph.Graph;

import java.util.Map;

/*
In the pathfinder homework, the text user interface calls these methods to talk
to your model. In the campuspaths homework, your graphical user interface
will ultimately make class to these methods (through a web server) to
talk to your model the same way.

This is the power of the Model-View-Controller pattern, two completely different
user interfaces can use the same model to display and interact with data in
different ways, without requiring a lot of work to change things over.
*/

/**
 * This class represents the connection between the view and controller and the model
 * for the pathfinder and campus paths applications.
 */
public class ModelConnector {
  /**
   * The Graph that represent the campus map
   */
  private Graph<Point,Double> ModelGraph = new Graph<>();




  /**
   * Creates a new {@link ModelConnector} and initializes it to contain data about
   * pathways and buildings or locations of interest on the campus of the University
   * of Washington, Seattle. When this constructor completes, the dataset is loaded
   * and prepared, and any method may be called on this object to query the data.
   */
  public ModelConnector() {
    // Remember the tenets of design that you've learned. You shouldn't necessarily do everything
    // you need for the model in this one constructor, factor code out to helper methods or
    // classes to work with your design best. The only thing that needs to remain the
    // same is the name of this class and the four method signatures below, because the
    // Pathfinder application calls these methods in order to talk to your model.
    // Change and add anything else as you'd like.
    List<CampusPath> PathList = CampusPathsParser.parseCampusPaths();

          for(CampusPath path: PathList){
            Point segmentParent = new Point(path.getX1(),path.getY1());
            Point segmentchild = new Point(path.getX2(),path.getY2());
            if(!ModelGraph.containsNode(segmentParent)){
              ModelGraph.addNode(segmentParent);
            }
            ModelGraph.addEdge(segmentParent,segmentchild,path.getDistance());
          }


  }

  /**
   * @param shortName The short name of a building to query.
   * @return {@literal true} iff the short name provided exists in this campus map.
   */
  public boolean shortNameExists(String shortName) {
    List<CampusBuilding> buildingList = CampusPathsParser.parseCampusBuildings();
    for(CampusBuilding building: buildingList){
      if(building.getShortName().equals(shortName)){
        return true;
      }
    }
    return false;
  }

  /**
   * @param shortName The short name of a building to look up.
   * @return The long name of the building corresponding to the provided short name.
   * @throws IllegalArgumentException if the short name provided does not exist.
   */
  public String longNameForShort(String shortName) {
    if(!shortNameExists(shortName)){
      throw new IllegalArgumentException("shortName do not exist");
    }else {
      String longName = "";
      List<CampusBuilding> buildingList = CampusPathsParser.parseCampusBuildings();
      for (CampusBuilding building : buildingList) {
        if (shortName.equals(building.getShortName())) {
          longName = building.getLongName();
          break;
        }
      }
      return longName;
    }
  }

  /**
   * @return The mapping from all the buildings' short names to their long names in this campus map.
   */
  public Map<String, String> buildingNames() {
    Map<String,String> shortVsLong = new HashMap<>();
    List<CampusBuilding> buildingList = CampusPathsParser.parseCampusBuildings();
    for(CampusBuilding parentBuilding: buildingList){
      shortVsLong.put(parentBuilding.getShortName(),parentBuilding.getLongName());
    }
    return shortVsLong;
  }
  /**
   * (Helper Method)Get X,Y of the Building given short name
   *
   * @param shortName the short name of the building
   * @return A point with XY coordinate of the building
   * @throws IllegalArgumentException if shortName is null or not valid for this campus map
   */
  public Point getXYofBuilding(String shortName){
    if(shortName==null||!shortNameExists((shortName))){
      throw new IllegalArgumentException("shortName do not exist");
    }else {
      Point location = new Point(0.0, 0.0);
      List<CampusBuilding> buildingList = CampusPathsParser.parseCampusBuildings();
      for (CampusBuilding building : buildingList) {
        if (building.getShortName().equals(shortName)) {
          location = new Point(building.getX(), building.getY());
        }
      }
      return location;
    }
  }

  public Set<Point> pointSet(){
    return ModelGraph.getParentNodeSet();
  }

  public Set<Graph.Edge<Point,Double>> edgeList(Point parentNode){
    return ModelGraph.getEdgeSet(parentNode);
  }





  /**
   * Finds the shortest path, by distance, between the two provided buildings.
   *
   * @param startShortName The short name of the building at the beginning of this path.
   * @param endShortName   The short name of the building at the end of this path.
   * @return A path between {@code startBuilding} and {@code endBuilding}, or {@literal null}
   * if none exists.
   * @throws IllegalArgumentException if {@code startBuilding} or {@code endBuilding} are
   *                                  {@literal null}, or not valid short names of buildings in
   *                                  this campus map.
   */
  public Path<Point> findShortestPath(String startShortName, String endShortName) {
    if(startShortName==null||endShortName==null||!shortNameExists(startShortName)||!shortNameExists(endShortName)){
      throw new IllegalArgumentException("shortName not exist");
    }else{
      DijkstraFinder<Point> newfinder = new DijkstraFinder<>();
      Point startBuilding = getXYofBuilding(startShortName);
      Point endBuilding = getXYofBuilding(endShortName);
      return newfinder.findPath(startBuilding,endBuilding,ModelGraph);

    }
  }

//  public static void main(String[] args) {
//    ModelConnector newConnector = new ModelConnector();
//    System.out.println(newConnector.findShortestPath("HUB","KNE"));
//
//  }
}
