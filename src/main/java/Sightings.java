import org.sql2o.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class Sightings implements DatabaseManagement {

    private int id;
    private int location_id;
    private int ranger_id;
    private int animal_id;
    private Timestamp timestamp;



    public Sightings(int location_id, int ranger_id, int animal_id) {
        this.location_id = location_id;
        this.ranger_id = ranger_id;
        this.animal_id = animal_id;

    }

    public int getId() {
        return id;
    }

    public int getLocation_id() {
        return location_id;
    }

    public int getRanger_id() {
        return ranger_id;
    }

    public int getAnimal_id() {
        return animal_id;
    }

    public String getTime() {
        return String.format("%1$TD %1$TR", timestamp);
    }

    public void save(){
        try (Connection con= DB.sql2o.open()){
            String sql= "INSERT INTO sightings (animal_id,ranger_id,location_id,timestamp) VALUES (:animal_id,:ranger_id,:location_id,now())";
            String joinRanger="INSERT INTO rangers_sightings (ranger_id,sighting_id) VALUES (:ranger_id,:sighting_id)";
            String joinLocation="INSERT INTO locations_sightings (location_id,sighting_id) VALUES (:location_id,:sighting_id)";
            this.id=(int) con.createQuery(sql,true)
                    .addParameter("animal_id",this.animal_id)
                    .addParameter("ranger_id",this.ranger_id)
                    .addParameter("location_id",this.location_id)
                    .executeUpdate()
                    .getKey();

            con.createQuery(joinRanger).addParameter("ranger_id",this.getRanger_id()).addParameter("sighting_id",
                    this.getId()).executeUpdate();
            con.createQuery(joinLocation).addParameter("location_id",this.getLocation_id()).addParameter("sighting_id",
                    this.id).executeUpdate();

        }

    }
    public static List<Sightings> all() {
        String sql = "SELECT * FROM sightings";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Sightings.class);
        }
    }

    public static Sightings find(int id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM sightings where id=:id";
            Sightings sighting = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Sightings.class);
            return sighting;
        }
    }
    public void delete(){
        try (Connection con= DB.sql2o.open()){
            String sql="DELETE FROM sightings WHERE id=:id";
            con.createQuery(sql)
                    .addParameter("id",this.id)
                    .executeUpdate();
        }

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sightings sightings = (Sightings) o;
        return id == sightings.id &&
                location_id == sightings.location_id &&
                ranger_id == sightings.ranger_id &&
                animal_id == sightings.animal_id;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, location_id, ranger_id, animal_id);
    }
}