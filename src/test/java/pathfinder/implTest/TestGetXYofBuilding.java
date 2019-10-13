package pathfinder.implTest;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import pathfinder.ModelConnector;
import pathfinder.datastructures.Point;


public class TestGetXYofBuilding {

    @Test(expected = IllegalArgumentException.class)
    public void testNotExistBuilding(){
        ModelConnector connector = new ModelConnector();
        connector.getXYofBuilding("RS");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullBuilding(){
        ModelConnector connector = new ModelConnector();
        connector.getXYofBuilding(null);
    }

    @Test
    public void testNormalBuilding(){
        ModelConnector connector = new ModelConnector();
        Point SUZpoint = new Point(1895.8038,1325.861);
        assertEquals(SUZpoint, connector.getXYofBuilding("SUZ"));
        Point CMUpoint = new Point(2344.8512, 1114.6251);
        assertEquals(CMUpoint, connector.getXYofBuilding("CMU"));
        Point KNEpoint = new Point(1876.6109,1165.2467);
        assertEquals(KNEpoint, connector.getXYofBuilding("KNE"));
        Point HUBpoint = new Point(2269.7856,1364.3777);
        assertEquals(HUBpoint,connector.getXYofBuilding("HUB"));
    }
}
