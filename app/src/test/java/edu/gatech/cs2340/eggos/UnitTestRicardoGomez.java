package edu.gatech.cs2340.eggos;

import org.junit.Test;

import dalvik.annotation.TestTarget;
import edu.gatech.cs2340.eggos.Model.User.User;
import edu.gatech.cs2340.eggos.Model.Shelter.Shelter;

import static org.junit.Assert.*;

public class UnitTestRicardoGomez {

    // Trivial unit test for checking user.getUsername() - just to understand how Junits work
    @Test
    public void checkGetUsername() throws Exception {
        String username = "rgomez98";
        User testUser = new User(username, "", "", 0, 0);
        assertEquals(username, testUser.getUsername());
    }

    // Trivial unit test for checking user.toString() - just to understand how Junits work
    @Test
    public void checkToString() throws Exception {
        String toString = "User name: rgomez98" + "\nType: ADMIN";
        User testUser = new User("rgomez98", "", "ADMIN", 0, 0);
        assertEquals(toString, testUser.toString());
    }

    // Non-Trivial unit test for checking negative capacity in shelter.requestRoom() - M10 Unit Testing
    @Test
    public void negativeRequestRoom() throws Exception {
        int capacityMax = 100;
        int capacityCurrent = 50;
        int capacityRequested = -1;
        Shelter testShelter = new Shelter(0, "test", capacityMax, capacityCurrent, 0, 0, "", 0, 0, "", "");
        testShelter.requestRoom(capacityRequested);
        assertEquals(capacityCurrent, testShelter._Capacity_current);
    }

    // Non-Trivial unit test for checking normal capacity in shelter.requestRoom() - M10 Unit Testing
    @Test
    public void normalRequestRoom() throws Exception {
        int capacityMax = 100;
        int capacityCurrent = 50;
        int capacityRequested = 2;
        Shelter testShelter = new Shelter(0, "test", capacityMax, capacityCurrent, 0, 0, "", 0, 0, "", "");
        testShelter.requestRoom(capacityRequested);
        int capacity = capacityCurrent - capacityRequested;
        assertEquals(capacity, testShelter._Capacity_current);
    }

    // Non-Trivial unit test for checking exceeding capacity in shelter.requestRoom() - M10 Unit Testing
    @Test
    public void exceedingRequestRoom() throws Exception {
        int capacityMax = 100;
        int capacityCurrent = 50;
        int capacityRequested = 51;
        Shelter testShelter = new Shelter(0, "test", capacityMax, capacityCurrent, 0, 0, "", 0, 0, "", "");
        testShelter.requestRoom(capacityRequested);
        assertEquals(capacityCurrent, testShelter._Capacity_current);
    }


}
