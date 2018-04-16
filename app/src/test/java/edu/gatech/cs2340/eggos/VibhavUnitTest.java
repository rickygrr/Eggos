package edu.gatech.cs2340.eggos;

import org.junit.Before;
import org.junit.Test;
import edu.gatech.cs2340.eggos.Model.Shelter.Shelter;
import static org.junit.Assert.*;

/**
 * This JUnit Test is for the method haveRoomFor() in Shelter. It makes sure that there is enough
 * room in a shelter to house a given number of people.
 *
 * Author: Vibhav Bhat
 * GTID: 903312953
 *
 */

public class VibhavUnitTest {

    Shelter testShelter = new Shelter(0, "test", 100, 0,
            0, 0, "", 0, 0, "", "");

    @Test
    public void hasRoomForOverCap() {
        int capacity = 20;
        int cap = 30;
        testShelter._Capacity_current = capacity;
        assertEquals(false, testShelter.haveRoomFor(cap));
    }

    @Test
    public void hasRoomForUnderCap() throws Exception {
        int capacity = 20;
        int cap = 10 ;
        testShelter._Capacity_current = capacity;
        assertEquals(true, testShelter.haveRoomFor(cap));
    }

    @Test
    public void hasRoomForNegative() {
        int capacity = 20;
        int cap = -5;
        testShelter._Capacity_current = capacity;
        assertEquals(false, testShelter.haveRoomFor(cap));
    }


}