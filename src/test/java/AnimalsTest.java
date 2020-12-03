import org.junit.Rule;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class AnimalsTest {
    @Rule
    public DatabaseRule databaseRule=new DatabaseRule();

    @Test
    public void animals_instantiatesCorrectly_true() {
        Animals testAnimal = new Animals("Tiger", "non-endangered");
        assertEquals(true, testAnimal instanceof Animals);
    }

    @Test
    public void getName_personInstantiatesWithName_String() {
        Animals testAnimal = new Animals("Tiger", "non-endangered");
        assertEquals("Tiger", testAnimal.getName());
    }

    @Test
    public void getType_animalsInstantiatesWithType_String() {
        Animals testAnimal = new Animals("Tiger", "non-endangered");
        assertEquals("non-endangered", testAnimal.getType());
    }

    @Test
    public void equals_returnsTrueIfNameAndTypeAreSame_true() {
        Animals firstAnimal = new Animals("Tiger", "non-endangered");
        Animals anotherAnimal = new Animals("Tiger", "non-endangered");
        assertTrue(firstAnimal.equals(anotherAnimal));
    }

    @Test
    public void save_insertsObjectIntoDatabase_Animals() {
        Animals testAnimal = new Animals("Tiger", "non-endangered");
        testAnimal.save();
        assertTrue(Animals.all().get(0).equals(testAnimal));
    }

    @Test
    public void all_returnsAllInstancesOfAnimals_true() {
        Animals firstAnimal = new Animals("Tiger", "non-endangered");
        firstAnimal.save();
        Animals secondAnimal = new Animals("Lion", "non-endangered");
        secondAnimal.save();
        assertEquals(true, Animals.all().get(0).equals(firstAnimal));
        assertEquals(true, Animals.all().get(1).equals(secondAnimal));
    }

    @Test
    public void save_assignsIdToObject() {
        Animals testAnimal = new Animals("Tiger", "non-endangered");
        testAnimal.save();
        Animals savedAnimal = Animals.all().get(0);
        assertEquals(testAnimal.getId(), savedAnimal.getId());
    }

    @Test
    public void find_returnsAnimalsWithSameId_secondAnimal() {
        Animals firstAnimal = new Animals("Tiger", "non-endangered");
        firstAnimal.save();
        Animals secondAnimal = new Animals("Lion", "non-endangered");
        secondAnimal.save();
        assertEquals(Animals.find(secondAnimal.getId()), secondAnimal);
    }

    @Test
    public void deleteById() {
        Animals testAnimal=new Animals("Tiger","non-endangered");
        testAnimal.save();
        testAnimal.delete();
        assertEquals(null,Animals.find(testAnimal.getId()));
    }

    @Test
    public void ensureEntryIsUpdatedCorrectlyAndNonCanBeEmpty() {
        Animals testAnimal=new Animals("Tiger","");
        Animals otherAnimal=testAnimal;
        testAnimal.save();
        try {
            testAnimal.update(testAnimal.getId(),"endangered","ill","newborn");
            Animals updatedAnimal=  Animals.find(testAnimal.getId());
            assertEquals(updatedAnimal.getId(),otherAnimal.getId());
            assertNotEquals(updatedAnimal.getHealth(),otherAnimal.getHealth());
        }catch (IllegalArgumentException e){
        }
    }
}
