package campuspaths;

import campuspaths.utils.CORSFilter;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import pathfinder.datastructures.*;
import pathfinder.ModelConnector;
import pathfinder.datastructures.Path;
import java.util.*;

public class SparkServer {

  public static void main(String[] args) {
    CORSFilter corsFilter = new CORSFilter();
    corsFilter.apply();
    // The above two lines help set up some settings that allow the
    // React application to make requests to the Spark server, even though it
    // comes from a different server.
    // You should leave these two lines at the very beginning of main().
    Spark.get("/findPath", new Route(){
      @Override
      public Object handle(Request request,Response response) throws Exception{
        String startShort = request.queryParams("startShort");
        String endShort = request.queryParams("endShort");
        if(startShort==null||endShort==null){
          Spark.halt(400,"must have both start building and end building");
        }
        ModelConnector modelConnector = new ModelConnector();
        Path<Point> resultPath = modelConnector.findShortestPath(startShort, endShort);
        double[][][] pathArray = new double[resultPath.getSize()][2][2];
        int i=0;
        for(Path<Point>.Segment segment: resultPath){
          double[] startCoordinate = new double[2];
          startCoordinate[0] = segment.getStart().getX();
          startCoordinate[1] = segment.getStart().getY();
          double[] endCoordinate = new double[2];
          endCoordinate[0] = segment.getEnd().getX();
          endCoordinate[1] = segment.getEnd().getY();
          double[][] startANDend = new double[][]{startCoordinate,endCoordinate};
          pathArray[i]=startANDend;
          i++;
        }
        Gson gson = new Gson();
        return gson.toJson(pathArray);
      }
    });

    Spark.get("/BuildingList", new Route(){
      @Override
      public Object handle(Request request, Response response) throws Exception{
        ModelConnector modelConnector = new ModelConnector();
        Map<String,String> shortVSLong = modelConnector.buildingNames();
        String[][] shortVSLongArray = new String[shortVSLong.size()][2];
        int i=0;
        for(String shortName: shortVSLong.keySet()){
          shortVSLongArray[i][0] = shortName;
          shortVSLongArray[i][1] = shortVSLong.get(shortName);
          i++;
        }
        Gson gson = new Gson();
        return gson.toJson(shortVSLongArray);
      }
    });
  }

}
