import org.junit.Rule;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class EndangeredAnimalsTest {

    @Rule
    public DatabaseRule databaseRule=new DatabaseRule();

    @Test
    public void allEndangeredAnimalsClass_true(){
        EndangeredAnimals testAnimal= setNewAnimal();
        assertEquals(true,testAnimal instanceof EndangeredAnimals);
    }

    @Test
    public void allInstancesAreSaved(){
        EndangeredAnimals testAnimal=setNewAnimal();
        testAnimal.save();
        assertTrue(EndangeredAnimals.all().get(0).getHealth().equals(testAnimal.getHealth()));
    }

    @Test
    public void findByID(){
        EndangeredAnimals testAnimal=setNewAnimal();
        testAnimal.save();
        Animals foundAnimal= Animals.find(testAnimal.getId());
        assertTrue(foundAnimal.getHealth().equals(testAnimal.getHealth()));

    }
    @Test
    public void deleteByID(){
        EndangeredAnimals testAnimal=setNewAnimal();
        testAnimal.save();
        testAnimal.delete();
        assertEquals(null,Animals.find(testAnimal.getId()));

    }
    @Test
    public void ensureNameFieldCannotBeEmpty(){
        EndangeredAnimals testAnimal=new EndangeredAnimals("","endangered","","");
        try {
            testAnimal.save();
        }catch (IllegalArgumentException e){

        }
    }

    @Test
    public void deleteAllEntries(){
        EndangeredAnimals testAnimal=setNewAnimal();
        EndangeredAnimals otherAnimal=setNewAnimal();
        testAnimal.save();
        otherAnimal.save();
        Animals.deleteAll();
        List<Animals> animals=Animals.all();
        assertEquals(0,animals.size());


    }

    @Test
    public void updatingEntry(){
        EndangeredAnimals testAnimal=setNewAnimal();
        EndangeredAnimals otherAnimal=testAnimal;
        testAnimal.save();
        try {
            testAnimal.update(testAnimal.getId(),"endangered","okay","newborn");
            Animals updatedAnimal=  Animals.find(testAnimal.getId());
            assertEquals(updatedAnimal.getId(),otherAnimal.getId());
            assertNotEquals(updatedAnimal.getHealth(),otherAnimal.getHealth());
        }catch (IllegalArgumentException e){

        }

    }

    private EndangeredAnimals setNewAnimal() {
        return new EndangeredAnimals(" Tiger","endangered","ill","adult");
    }


}