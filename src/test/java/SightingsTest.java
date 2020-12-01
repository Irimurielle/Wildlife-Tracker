import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;

public class SightingsTest {

    @Rule
    public DatabaseRule databaseRule=new DatabaseRule();

    @Test
    public void InstanceOfSightingsClass_true() {

        Sightings sighting= setNewSighting();
        assertEquals(true,sighting instanceof Sightings);
    }

    @Test
    public void allSaved() {
        Sightings sightings=setNewSighting();
        Sightings otherSighting=new Sightings(-1,1,1);
        try {
            sightings.save();
            otherSighting.save();
            assertTrue(Sightings.find(sightings.getId()).equals(sightings));
        }catch (IllegalArgumentException e){
            System.out.println(e);
        }
    }
    //
    @Test
    public void ByID() {
        Sightings sighting=setNewSighting();
        sighting.save();
        Sightings foundSighting=Sightings.find(sighting.getId());
        assertTrue(foundSighting.equals(sighting));

    }
    @Test
    public void deleteSightingByID() {
        Sightings sighting=setNewSighting();
        sighting.save();
        sighting.delete();
        assertEquals(null,Sightings.find(sighting.getId()));

    }
    @Test
    public void deleteAll() {
        Sightings sighting=setNewSighting();
        Sightings otherSightings=setNewSighting();
        sighting.save();
        otherSightings.save();
        Sightings.deleteAll();

        assertEquals(0,Sightings.all().size());

    }

    //helper
    private Sightings setNewSighting() {
        return new Sightings(1,1,1);
    }
}
