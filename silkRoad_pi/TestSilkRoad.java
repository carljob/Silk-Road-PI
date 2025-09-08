import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestSilkRoad {
    private SilkRoad road;

    @Before
    public void setUp() {
        road = new SilkRoad(1000);
    }

    @Test
    public void testPlaceStore() {
        road.placeStore(10, 200);
        assertTrue(road.ok());
        assertEquals("[10, 200]", road.stores().get(0));
    }

    @Test
    public void testPlaceRobotAndMove() {
        road.placeRobot(5);
        assertTrue(road.ok());

        road.moveRobot(5, 10); // mueve el robot
        assertTrue(road.ok());
        assertEquals("[15, 0]", road.robots().get(0)); // [posicion, tenges]
    }

    @Test
    public void testProfitAndEmptyStore() {
        road.placeStore(20, 100);
        road.placeRobot(10);

        road.moveRobot(10, 10); // llega a la tienda
        assertTrue(road.ok());
        assertTrue(road.profit() > 0);
    }

    @Test
    public void testReboot() {
        road.placeStore(30, 300);
        road.resupplyStores();
        road.reboot();

        assertEquals(0, road.profit());
        assertTrue(road.ok());
    }
}
