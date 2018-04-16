package edu.gatech.cs2340.eggos;

import org.junit.Test;
import edu.gatech.cs2340.eggos.Model.Shelter.Shelter;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DylanCoordTest {

    @Test

    public void setCoord_LengthGreaterThan2() throws Exception {
        double[] coord = {98.2, 69.2, 12.3};
        double[] current = {0, 0};
        Shelter testing = new Shelter(0, "test", 0, 0, 0, 0, "", 0, 0, "", "");
        testing.setCoord(coord);
        assertEquals(current, testing.getCoord());
    }

    public void setCoord_LengthLessThan2() throws Exception {
        double[] coord = {98.2};
        double[] current = {0, 0};
        Shelter testing = new Shelter(0, "test", 0, 0, 0, 0, "", 0, 0, "", "");
        testing.setCoord(coord);
        assertEquals(current, testing.getCoord());
    }

    public void setCoord_LengthEqualTo2() throws Exception {
        double[] coord = {98.2, 69.2};
        Shelter testing = new Shelter(0, "test", 0, 0, 0, 0, "", 0, 0, "", "");
        testing.setCoord(coord);
        assertEquals(coord, testing.getCoord());
    }
}