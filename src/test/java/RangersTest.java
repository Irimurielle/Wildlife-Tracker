import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class RangersTest {

    @Rule
    public DatabaseRule databaseRule=new DatabaseRule();

    @Test
    public void rangers_instantiatesCorrectly_true() {
        Rangers testRanger = new Rangers("marie","M23","marie@gmail.com");
        assertEquals(true, testRanger instanceof Rangers);
    }

    @Test
    public void getName_rangerInstantiatesWithName_String() {
        Rangers testRanger = new Rangers("marie","M23","marie@gmail.com");
        assertEquals("Marie", testRanger.getName());
    }

    @Test
    public void getBadge_rangerInstantiatesWithBadge_String() {
        Rangers testRanger = new Rangers("marie","M23","marie@gmail.com");
        assertEquals("M23", testRanger.getBadge_number());
    }

    @Test
    public void getEmail_rangerInstantiatesWithEmail_String() {
        Rangers testRanger = new Rangers("marie","M23","marie@gmail.com");
        assertEquals("marie@gmail.com", testRanger.getEmail());
    }

    @Test
    public void equals_returnsTrueIfNameAndEmailAreSame_true() {
        Rangers firstRanger = new Rangers("marie","M23","marie@gmail.com");
        Rangers anotherRanger = new Rangers("marie","M23","marie@gmail.com");
        assertTrue(firstRanger.equals(anotherRanger));
    }

    @Test
    public void save_insertsObjectIntoDatabase_Rangers() {
        Rangers testRanger = new Rangers("marie","M23","marie@gmail.com");
        testRanger.save();
        assertTrue(Rangers.all().get(0).equals(testRanger));
    }

    @Test
    public void all_returnsAllInstancesOfRangers_true() {
        Rangers firstRanger = new Rangers("marie","M23","marie@gmail.com");
        firstRanger.save();
        Rangers secondRanger = new Rangers("clarie","R56","claire@yahoo.com");
        secondRanger.save();
        assertEquals(true, Rangers.all().get(0).equals(firstRanger));
        assertEquals(true, Rangers.all().get(1).equals(secondRanger));
    }

    @Test
    public void save_assignsIdToObject() {
        Rangers testRanger = new Rangers("marie","M23","marie@gmail.com");
        testRanger.save();
        Rangers savedRanger = Rangers.all().get(0);
        assertEquals(testRanger.getId(), savedRanger.getId());
    }

    @Test
    public void find_returnsRangersWithSameId_secondRanger() {
        Rangers firstRanger = new Rangers("marie","M23","marie@gmail.com");
        firstRanger.save();
        Rangers secondRanger = new Rangers("clarie","R56","claire@yahoo.com");
        secondRanger.save();
        assertEquals(Rangers.find(secondRanger.getId()), secondRanger);
    }

}
