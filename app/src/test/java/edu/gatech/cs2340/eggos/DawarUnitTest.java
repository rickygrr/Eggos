package edu.gatech.cs2340.eggos;

import org.junit.Test;
import edu.gatech.cs2340.eggos.Model.Shelter.Shelter;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@SuppressWarnings("MagicNumber")
public class DawarUnitTest {

    @Test
    public void freeRoom_NegativeCap() throws Exception {
        int capmax = 100;
        int capacity = 20;
        int cap = -5;
        Shelter testShelter = new Shelter(0, "test", 0, 0, 0, 0, "", 0, 0, "", "");
        testShelter._Capacity_max = capmax;
        testShelter._Capacity_current = capacity;
        testShelter.freeRoom(cap);
        assertEquals(capacity, testShelter._Capacity_current);
    }

    @Test
    public void freeRoom_BigCap() throws Exception {
        int capmax = 100;
        int capacity = 20;
        int cap = 100;
        Shelter testShelter = new Shelter(0, "test", 0, 0, 0, 0, "", 0, 0, "", "");
        testShelter._Capacity_current = capacity;
        testShelter._Capacity_max = capmax;
        testShelter.freeRoom(cap);
        assertEquals(capacity, testShelter._Capacity_current);
    }

    @Test
    public void freeRoom_Normal() throws Exception {
        int capmax = 100;
        int capacity = 20;
        int cap = 50;
        Shelter testShelter = new Shelter(0, "test", 0, 0, 0, 0, "", 0, 0, "", "");
        testShelter._Capacity_max = capmax;
        testShelter._Capacity_current = capacity;
        testShelter.freeRoom(cap);
        assertEquals(capacity + cap, testShelter._Capacity_current);
    }


}