package pathfinder.datastructures;

import java.util.*;

/**
 * This class represent the idea of Path's comparator
 *
 * Abstract Invariant:
 * The path are compared by the cost
 *
 */
public class PathComparator<Node> implements Comparator<Path<Node>>{
    /**
     * This method override the compare method of the Comparator interphase and compare two path by their cost
     *
     * @param path1 one of the path involved in comparation
     * @param path2 the other path involved in comparation
     * @return -1 if the first path's cost is lower, 1 if the second path's cost is higher and 0 if they have the same cost
     */
    @Override
    public int compare(Path<Node> path1, Path<Node> path2){
        if(path1.getCost()<path2.getCost()){
            return -1;
        }else if(path1.getCost()>path2.getCost()){
            return 1;
        }else{
            return 0;
        }
    }
}
