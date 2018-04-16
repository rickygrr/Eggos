package edu.gatech.cs2340.eggos;
//Woradorn K.
/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
import android.support.annotation.Nullable;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.gatech.cs2340.eggos.Model.Shelter.AgeEnum;
import edu.gatech.cs2340.eggos.Model.Shelter.GenderEnum;
import edu.gatech.cs2340.eggos.Model.Shelter.Shelter;
import edu.gatech.cs2340.eggos.Model.Shelter.ShelterDatabaseDAO;
import edu.gatech.cs2340.eggos.Model.Shelter.ShelterDatabaseInterface;
import edu.gatech.cs2340.eggos.Model.Shelter.ShelterDatabase_room;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

@SuppressWarnings("ALL")
public class ShelterDatabaseRoom_getFilteredShelterList_test {
    //Woradorn K.
    ShelterDatabase_MockDAO mockDAO;
    ShelterDatabaseInterface testDB;
    @Before
    public void setUp() {
        mockDAO = new ShelterDatabase_MockDAO();
        testDB = ShelterDatabase_room.getFirstInstance(mockDAO);
    }

    @Test
    public void testNoFilter() {
        testDB.getFilteredShelterList("", null, null);
        assertEquals(null, mockDAO.checkFilterName());
        assertEquals(0, mockDAO.checkGenderMask());
        assertEquals(0, mockDAO.checkAgeMask());
    }

    @Test
    public void testNameOnlyFilter() {
        testDB.getFilteredShelterList("test shelter", null, null);
        assertEquals("%test shelter%", mockDAO.checkFilterName());
        assertEquals(0, mockDAO.checkGenderMask());
        assertEquals(0, mockDAO.checkAgeMask());
    }

    @Test
    public void testGenderOnlyFilter() {
        ArrayList<String> gender_mask = new ArrayList<String>(Arrays.asList("Men"));
        testDB.getFilteredShelterList("", gender_mask, null);
        assertEquals(null, mockDAO.checkFilterName());
        assertEquals(GenderEnum.enum2Mask(GenderEnum.list2Enums(gender_mask)), mockDAO.checkGenderMask());
        assertEquals(0, mockDAO.checkAgeMask());

        gender_mask = new ArrayList<String>(Arrays.asList("Men","Women"));
        testDB.getFilteredShelterList("", gender_mask, null);
        assertEquals(null, mockDAO.checkFilterName());
        assertEquals(GenderEnum.enum2Mask(GenderEnum.list2Enums(gender_mask)), mockDAO.checkGenderMask());
        assertEquals(0, mockDAO.checkAgeMask());
    }

    @Test
    public void testAgeOnlyFilter() {
        ArrayList<String> age_mask = new ArrayList<String>(Arrays.asList("Family with newborns"));
        testDB.getFilteredShelterList("", null, age_mask);
        assertEquals(null, mockDAO.checkFilterName());
        assertEquals(0, mockDAO.checkGenderMask());
        assertEquals(AgeEnum.enum2Mask(AgeEnum.list2Enums(age_mask)), mockDAO.checkAgeMask());

        age_mask = new ArrayList<String>(Arrays.asList("Family with newborns", "Young adult"));
        testDB.getFilteredShelterList("", null, age_mask);
        assertEquals(null, mockDAO.checkFilterName());
        assertEquals(0, mockDAO.checkGenderMask());
        assertEquals(AgeEnum.enum2Mask(AgeEnum.list2Enums(age_mask)), mockDAO.checkAgeMask());
    }

    @Test
    public void testCombinationFilter() {
        ArrayList<String> gender_mask = new ArrayList<String>(Arrays.asList("Men","Women"));
        ArrayList<String> age_mask = new ArrayList<String>(Arrays.asList("Family with newborns"));
        testDB.getFilteredShelterList("", gender_mask, age_mask);
        assertEquals(null, mockDAO.checkFilterName());
        assertEquals(GenderEnum.enum2Mask(GenderEnum.list2Enums(gender_mask)), mockDAO.checkGenderMask());
        assertEquals(AgeEnum.enum2Mask(AgeEnum.list2Enums(age_mask)), mockDAO.checkAgeMask());

        testDB.getFilteredShelterList("test string", gender_mask, age_mask);
        assertEquals("%test string%", mockDAO.checkFilterName());
        assertEquals(GenderEnum.enum2Mask(GenderEnum.list2Enums(gender_mask)), mockDAO.checkGenderMask());
        assertEquals(AgeEnum.enum2Mask(AgeEnum.list2Enums(age_mask)), mockDAO.checkAgeMask());
    }

}

@SuppressWarnings("ALL")
//This is just a temporary mock class.
class ShelterDatabase_MockDAO implements ShelterDatabaseDAO{
    //Haunted mock DAO that records your method calls and calls you gay.
    //100% Astolfo plushie free

    @Nullable
    String filter_name;
    int genderMask;
    int ageMask;

    public ShelterDatabase_MockDAO(){
        this.reset();
    }

    public void reset(){
        filter_name = null;
        genderMask = -1;
        ageMask = -1;
    }

    public String checkFilterName(){
        return filter_name;
    }
    public int checkGenderMask(){
        return genderMask;
    }
    public int checkAgeMask(){
        return ageMask;
    }

    @Override
    public List<Shelter> getShelter(int UID) {
        return null;
    }

    @Override
    public List<Shelter> getFilteredShelterList(String name, int genderMask, int ageMask) {
        this.filter_name = name;
        this.ageMask = ageMask;
        this.genderMask = genderMask;
        return null;
    }

    @Override
    public List<Shelter> getFilteredShelterList(int genderMask, int ageMask) {
        this.filter_name = null;
        this.ageMask = ageMask;
        this.genderMask = genderMask;
        return null;
    }

    @Override
    public void insertAll(Shelter... shelter) {
    }

    @Override
    public int update(Shelter... shelters) {
        return 0;
    }

    @Override
    public void delete(Shelter shelter) {
    }

    @Override
    public int getRowCount() {
        return 10;
    }

    @Override
    public void clearDatabase() {
    }
}

