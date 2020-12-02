import org.sql2o.*;
import java.util.List;

public class Animals implements DatabaseManagement{

    public int id;
    public String name;
    public String type;
    private String health;
    private String age;
    public static final String ANIMAL_TYPE="non-endangered";

    public Animals(String name, String type) {
        this.name= name;
        this.type=ANIMAL_TYPE;
        this.health="";
        this.age="";
    }
    public String getHealth() {
        return health;
    }

    public String getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object otherAnimal){
        if (!(otherAnimal instanceof Animals)) {
            return false;
        } else {
            Animals newAnimal = (Animals) otherAnimal;
            return this.getName().equals(newAnimal.getName()) &&
                    this.getType().equals(newAnimal.getType());
        }
    }

    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO animals (name, type) VALUES (:name, :type)";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("name", this.name)
                    .addParameter("type", this.type)
                    .executeUpdate()
                    .getKey();
        }
    }

    public static List<Animals> all() {
        String sql = "SELECT * FROM animals";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Animals.class);
        }
    }

    public static Animals find(int id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM animals where id=:id";
            Animals animal = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Animals.class);
            return animal;
        }
    }

    public void delete(){
        try (Connection con= DB.sql2o.open()){
            String sql = "DELETE FROM animals WHERE id=:id";
            con.createQuery(sql)
                    .addParameter("id",this.id)
                    .executeUpdate();

        }
    }
    public void update(int id,String type,String health,String age) {
        try (Connection con = DB.sql2o.open()) {
            if (type.equals("")) {
                throw new IllegalArgumentException("All fields must be filled");
            }
            if (type == "endangered") {
                if (health.equals("") || age.equals("")) {
                    throw new IllegalArgumentException("All fields must be filled");
                }
                String sql = "UPDATE animals SET type=:type,health=:health,age=:age WHERE id=:id";
                con.createQuery(sql)
                        .addParameter("type", type)
                        .addParameter("health", health)
                        .addParameter("age", age)
                        .addParameter("id", this.id)
                        .executeUpdate();
            } else {
                String sql = "UPDATE animals SET type=:type,health=:health,age=:age WHERE id=:id";
                con.createQuery(sql)
                        .addParameter("type", type)
                        .addParameter("health", "")
                        .addParameter("age", "")
                        .addParameter("id", this.id)
                        .executeUpdate();
            }

        }
    }
}

