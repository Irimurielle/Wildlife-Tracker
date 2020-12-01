import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class RangersTest {

    @Rule
    public DatabaseRule databaseRule=new DatabaseRule();

    @Test
    public void createInstanceOfRangersClass_true(){
        Rangers ranger= setNewRanger();
        assertEquals(true,ranger instanceof Rangers);
    }

    @Test
    public void allEntriesAreSaved() {
        Rangers ranger= setNewRanger();
        ranger.save();
        assertTrue(Rangers.all().get(0).equals(ranger));

    }

    @Test
    public void emptynotsaved() {
        Rangers ranger=new Rangers("","","marie@gmail.com");
        try{
            ranger.save();
            assertTrue(Rangers.all().get(0).equals(ranger));
        }catch (IllegalArgumentException e){
            System.out.println(e);
        }
    }

    @Test
    public void findById() {
        Rangers ranger= setNewRanger();
        Rangers otherRanger=new Rangers("marie","1","marie@gmail.com");
        ranger.save();
        otherRanger.save();
        Rangers foundRanger=Rangers.find(ranger.getId());
        assertTrue(foundRanger.equals(ranger));

    }

    @Test
    public void UpdatedEntry() {
        Rangers ranger= setNewRanger();
        Rangers otherRanger=ranger;
        ranger.save();
        try {
            ranger.update(ranger.getId(),"Ruth ira","ruth@gmail.com");
            Rangers foundRanger=Rangers.find(ranger.getId());
            assertNotEquals(foundRanger,otherRanger);
            assertEquals(foundRanger.getId(),otherRanger.getId());

        }catch (IllegalArgumentException e){
            System.out.println(e);
        }
    }

    @Test
    public void entriesAreDeleted() {
        Rangers ranger= setNewRanger();
        Rangers otherRanger=new Rangers("marie","1","marie@gmail.com");
        ranger.save();
        otherRanger.save();
        ranger.delete();
        assertEquals(null,Rangers.find(ranger.getId()));

    }

    @Test
    public void ReturningslightsForRanger() {
        Rangers ranger=setNewRanger();
        try {
            Locations location=new Locations("Zone A");
            ranger.save();
            location.save();
            Sightings sighting=new Sightings(location.getId(),ranger.getId(),1);
            Sightings otherSighting=new Sightings(1,ranger.getId(),1);
            sighting.save();
            otherSighting.save();
            assertEquals(ranger.getRangerSightings().get(0),sighting);
            assertEquals(ranger.getRangerSightings().get(1),otherSighting);
        }catch (IllegalArgumentException e){
            System.out.println(e);
        }

    }

    private Rangers setNewRanger() {
        return new Rangers("Marie","1","marie@gmail.com");
    }

}
