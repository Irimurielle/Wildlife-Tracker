import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;

public class Rangers {

    private int id;
    private String name;
    private String badge_number;
    private String email;

    public Rangers(String name, String badge_number, String email) {
        if(name.equals("")||badge_number.equals("")||email.equals("")){
            throw new IllegalArgumentException("All fields must be filled");
        }
        this.name = name;
        this.badge_number = badge_number;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBadge_number() {
        return badge_number;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object otherRanger){
        if (!(otherRanger instanceof Rangers)) {
            return false;
        } else {
            Rangers newRanger = (Rangers) otherRanger;
            return this.getName().equals(newRanger.getName()) &&
                    this.getBadge_number().equals(newRanger.getBadge_number()) &&
                    this.getEmail().equals(newRanger.getEmail());
        }
    }

    public void save(){
        try (Connection con= DB.sql2o.open()){
            String sql="INSERT INTO rangers (name,badge_number,email) VALUES (:name,:badge_number,:email)";
            this.id=(int) con.createQuery(sql,true)
                    .addParameter("name",this.name)
                    .addParameter("badge_number",this.badge_number)
                    .addParameter("email",this.email)
                    .executeUpdate()
                    .getKey();
        }
    }

    public static List<Rangers> all(){
        try (Connection con= DB.sql2o.open()){
            String sql="SELECT * FROM rangers";
            return con.createQuery(sql).executeAndFetch(Rangers.class);

        }
    }

    public static Rangers find(int id){
        try (Connection con= DB.sql2o.open()){
            String sql="SELECT * FROM rangers WHERE id=:id";
            Rangers ranger= con.createQuery(sql)
                    .addParameter("id",id)
                    .executeAndFetchFirst(Rangers.class);
            return ranger;
        }
    }

    public void update(int id,String name,String email){
        try (Connection con= DB.sql2o.open()){
            String sql="UPDATE rangers SET name=:name,email=:email WHERE id=:id";
            if(name.equals("")||email.equals("")){
                throw new IllegalArgumentException("All fields must be filled");
            }
            con.createQuery(sql)
                    .addParameter("id",this.id)
                    .addParameter("name",name)
                    .addParameter("email",email)
                    .executeUpdate();
        }
    }
    public void delete(){
        try (Connection con= DB.sql2o.open()){
            String sql="DELETE FROM rangers WHERE id=:id";
            con.createQuery(sql)
                    .addParameter("id",this.id)
                    .executeUpdate();
        }
    }
    public List<Sightings> getRanger(){
        try (Connection con= DB.sql2o.open()){
            String sql="SELECT sighting_id FROM rangers_sightings WHERE ranger_id=:ranger_id";
            List<Integer> sightings_ids=con.createQuery(sql)
                    .addParameter("ranger_id",this.getId())
                    .executeAndFetch(Integer.class);
            List<Sightings> sightings=new ArrayList<Sightings>();
            for(Integer sighting_id:sightings_ids){
                String sightingsQuery="SELECT * FROM sightings WHERE id=:sighting_id";
                Sightings sighting=con.createQuery(sightingsQuery)
                        .addParameter("sighting_id",sighting_id)
                        .executeAndFetchFirst(Sightings.class);
                sightings.add(sighting);
            }
            if(sightings.size()==0){
                throw new IllegalArgumentException("Ranger has no sighting");
            }
            else {return sightings;}
        }
    }
}
