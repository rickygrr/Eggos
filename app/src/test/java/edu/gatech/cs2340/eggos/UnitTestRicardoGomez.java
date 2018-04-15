package edu.gatech.cs2340.eggos;

import org.junit.Test;

import dalvik.annotation.TestTarget;
import edu.gatech.cs2340.eggos.Model.User.User;
import edu.gatech.cs2340.eggos.Model.User.UserTypeEnum;

import static org.junit.Assert.*;

public class UnitTestRicardoGomez {

    @Test
    public void checkGetUsername() throws Exception {
        String username = "rgomez98";
        User testUser = new User(username, "", "", 0, 0);
        assertEquals(username, testUser.getUsername());
    }

    @Test
    public void checkToString() throws Exception {
        String toString = "User name: rgomez98" + "\nType: ADMIN";
        User testUser = new User("rgomez98", "", "ADMIN", 0, 0);
        assertEquals(toString, testUser.toString());
    }




}
