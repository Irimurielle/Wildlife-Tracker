import org.junit.rules.ExternalResource;
import org.sql2o.*;
public class DatabaseRule extends ExternalResource {
    @Override
    protected void before() {
        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker_test", "murielle", "murielle12");
    }
    @Override
    protected void after() {
        try(Connection con = DB.sql2o.open()) {
            String deleteAnimalsQuery = "DELETE FROM animals *;";
            String deleteRangersQuery = "DELETE FROM rangers *;";
            String deleteLocationsQuery = "DELETE FROM locations *;";
            String deleteSightingsQuery = "DELETE FROM sightings *;";
            String deleteJoinsRangersQuery = "DELETE FROM rangers_sightings *;";
            String deleteJoinsLocationsQuery = "DELETE FROM locations_sightings *;";
            con.createQuery(deleteAnimalsQuery).executeUpdate();
            con.createQuery(deleteRangersQuery).executeUpdate();
            con.createQuery(deleteLocationsQuery).executeUpdate();
            con.createQuery(deleteSightingsQuery).executeUpdate();
            con.createQuery(deleteJoinsRangersQuery).executeUpdate();
            con.createQuery(deleteJoinsLocationsQuery).executeUpdate();
        }
    }
}

