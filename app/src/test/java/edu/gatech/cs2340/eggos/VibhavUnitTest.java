package edu.gatech.cs2340.eggos;

import org.junit.Test;
import edu.gatech.cs2340.eggos.Model.Shelter.Shelter;
import edu.gatech.cs2340.eggos.Model.User.User;
import edu.gatech.cs2340.eggos.Model.User.UserHolder;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class VibhavUnitTest {

    @Test
    public void loginUserNotThere() {
        UserHolder userHolder = UserHolder.getInstance();
        boolean test = userHolder.login("shelterguy", "gimmeshelter");
        assertEquals(true, test);
    }

    @Test
    public void loginExistingUser() {
        UserHolder userHolder = UserHolder.getInstance();
        boolean test = userHolder.login("fake", "credentials");
        assertEquals(false, test);
    }


}