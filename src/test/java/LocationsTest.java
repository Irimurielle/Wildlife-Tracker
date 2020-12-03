import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;

public class LocationsTest {
    @Rule
    public DatabaseRule databaseRule=new DatabaseRule();

    @Test
    public void locations_instantiatesCorrectly_true() {
        Locations testLocation= new Locations("near river");
        assertEquals(true,testLocation instanceof Locations);
    }

    @Test
    public void getName_locationInstantiatesWithName_String() {
        Locations testLocation = new Locations("near river");
        assertEquals("near river", testLocation.getName());
    }

    @Test
    public void equals_returnsTrueIfNameAreSame_true() {
        Locations firstLocation = new Locations("near river");
        Locations anotherLocation = new Locations("near river");
        assertTrue(firstLocation.equals(anotherLocation));
    }
    @Test
    public void save_insertsObjectIntoDatabase_Locations() {
        Locations testLocation = new Locations("near river");
        testLocation.save();
        assertTrue(Locations.all().get(0).equals(testLocation));
    }
    @Test
    public void all_returnsAllInstancesOfLocations_true() {
        Locations firstLocation = new Locations("near river");
        firstLocation.save();
        Locations secondLocation = new Locations("zone A");
        secondLocation.save();
        assertEquals(true, Locations.all().get(0).equals(firstLocation));
        assertEquals(true, Locations.all().get(1).equals(secondLocation));
    }
    @Test
    public void save_assignsIdToObject() {
        Locations testLocation = new Locations("near river");
        testLocation.save();
        Locations savedLocation = Locations.all().get(0);
        assertEquals(testLocation.getId(), savedLocation.getId());
    }
    @Test
    public void find_returnsLocationsWithSameId_secondLocation() {
        Locations firstLocation = new Locations("near river");
        firstLocation.save();
        Locations secondLocation = new Locations("zone A");
        secondLocation.save();
        assertEquals(Locations.find(secondLocation.getId()), secondLocation);
    }
    @Test
    public void delete_deletesLocation_true() {
        Locations testLocation = new Locations("near river");
        testLocation.save();
        testLocation.delete();
        assertEquals(0, Locations.all().size());
    }
}
