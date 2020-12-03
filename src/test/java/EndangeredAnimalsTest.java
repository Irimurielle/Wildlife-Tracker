import org.junit.Rule;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class EndangeredAnimalsTest {

    @Rule
    public DatabaseRule databaseRule=new DatabaseRule();

    @Test
    public void allEndangeredAnimalsClass_true(){
        EndangeredAnimals testAnimal= new EndangeredAnimals("Lion","endangered","okay","newborn");
        assertEquals(true,testAnimal instanceof EndangeredAnimals);
    }

    @Test
    public void allInstancesAreSaved(){
        EndangeredAnimals testAnimal=new EndangeredAnimals("Lion","endangered","okay","newborn");
        testAnimal.save();
        assertTrue(EndangeredAnimals.all().get(0).getHealth().equals(testAnimal.getHealth()));
    }

    @Test
    public void findById(){
        EndangeredAnimals testAnimal=new EndangeredAnimals("Lion","endangered","okay","newborn");
        testAnimal.save();
        Animals foundAnimal= Animals.find(testAnimal.getId());
        assertTrue(foundAnimal.getHealth().equals(testAnimal.getHealth()));

    }
    @Test
    public void deleteByID(){
        EndangeredAnimals testAnimal=new EndangeredAnimals("Lion","endangered","okay","newborn");
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
}