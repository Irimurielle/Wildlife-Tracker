import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;

public class SightingsTest {

    @Rule
    public DatabaseRule databaseRule=new DatabaseRule();

    @Test
    public void sightings_instantiatesCorrectly_true() {
        Sightings testSighting = new Sightings(1,1,1);
        assertEquals(true, testSighting instanceof Sightings);
    }

    @Test
    public void Sightings_instantiatesWithLocationId_int() {
        Sightings testSighting = new Sightings(1,1,1);
        assertEquals(1, testSighting.getLocation_id());
    }

    @Test
    public void Sightings_instantiatesWithRangerId_int() {
        Sightings testSighting = new Sightings(1,1,1);
        assertEquals(1, testSighting.getRanger_id());
    }

    @Test
    public void Sightings_instantiatesWithAnimalId_int() {
        Sightings testSighting = new Sightings(1,1,1);
        assertEquals(1, testSighting.getAnimal_id());
    }

    @Test
    public void equals_returnsTrueIfIdsAreSame_true() {
        Sightings firstSighting = new Sightings(1,1,1);
        Sightings anotherSighting = new Sightings(1,1,1);
        assertTrue(firstSighting.equals(anotherSighting));
    }

    @Test
    public void save_insertsObjectIntoDatabase_Sightings() {
        Sightings testSighting = new Sightings(1,1,1);
        testSighting.save();
        assertTrue(Animals.all().get(0).equals(testSighting));
    }

    @Test
    public void save_assignsIdToObject() {
        Sightings testSighting = new Sightings(1,1,1);
        testSighting.save();
        Sightings savedSighting = Sightings.all().get(0);
        assertEquals(testSighting.getId(), savedSighting.getId());
    }

    @Test
    public void all_returnsAllInstancesOfSighting_true() {
        Sightings firstSighting = new Sightings(1,1,1);
        firstSighting.save();
        Sightings secondSighting = new Sightings(4,2,3);
        secondSighting.save();
        assertEquals(true, Sightings.all().get(0).equals(firstSighting));
        assertEquals(true, Sightings.all().get(1).equals(secondSighting));
    }

    @Test
    public void find_returnsSightingsWithSameId_secondSighting() {
        Sightings firstSighting = new Sightings(1,1,1);
        firstSighting.save();
        Sightings secondSighting = new Sightings(4,2,3);
        secondSighting.save();
        assertEquals(Sightings.find(secondSighting.getId()), secondSighting);
    }
}
