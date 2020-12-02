import org.junit.Rule;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class AnimalsTest {
    @Rule
    public DatabaseRule databaseRule=new DatabaseRule();

    @Test
    public void allAnimalsClass_true (){
        Animals testAnimal=setNewAnimal ();
        assertEquals (true, testAnimal instanceof Animals);
    }

    private Animals setNewAnimal (){
        return new Animals ("zebra", "health");
    }

    @Test
    public void allInstances (){
        Animals testAnimal=setNewAnimal ();
        testAnimal.save ();
        assertTrue (Animals.all ().get (0).equals (testAnimal));
    }

    @Test
    public void updatingeEntry (){
        Animals testAnimal=setNewAnimal ();
        Animals otherAnimal=testAnimal;
        testAnimal.save ();
        try {
            testAnimal.update (testAnimal.getId (), "endangered", "ill", "newborn");
            Animals updatedAnimal=Animals.find (testAnimal.getId ());
            assertEquals (updatedAnimal.getId (), otherAnimal.getId ());
            assertNotEquals (updatedAnimal.getHealth (), otherAnimal.getHealth ());
        } catch (IllegalArgumentException e) {

        }
    }
    @Test
    public void findById() {
        Animals testAnimal=setNewAnimal();
        testAnimal.save();
        Animals foundAnimal= Animals.find(testAnimal.getId());
        assertTrue(foundAnimal.equals(testAnimal));
    }
    @Test
    public void deleteById() {
        Animals testAnimal=setNewAnimal();
        testAnimal.save();
        testAnimal.delete();
        assertEquals(null,Animals.find(testAnimal.getId()));
    }

    @Test
    public void noEntryForEmptyNames(){
        Animals testAnimal=new Animals("","normal");
        try {
            testAnimal.save();
        }catch (IllegalArgumentException e) {
        }
}
}
