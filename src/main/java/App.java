import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static spark.Spark.*;

public class App {
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }
    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        staticFileLocation("/public");

        get("/",(request, response) -> {
            Map<String,Object> model=new HashMap<>();
            return new ModelAndView(model,"index.hbs");
        },new HandlebarsTemplateEngine());

        get("/animals/new",(request, response) -> {
            Map<String,Object> model=new HashMap<>();
            return new ModelAndView(model,"newAnimal.hbs");
        },new HandlebarsTemplateEngine());

        post("/animals",(request, response) -> {
            Map<String,Object> model=new HashMap<>();
            String type=request.queryParams("type");
            System.out.println(type);
            String health=request.queryParams("health");
            System.out.println(health);
            String age=request.queryParams("age");
            System.out.println(age);
            String name=request.queryParams("name");
            System.out.println(name);
            if(type.equals(EndangeredAnimals.ANIMAL_TYPE)){
                EndangeredAnimals endangered=new EndangeredAnimals(name,EndangeredAnimals.ANIMAL_TYPE,health,age);
                endangered.save();
            }
            else {
                Animals animal=new Animals(name,Animals.ANIMAL_TYPE);
                animal.save();
            }

            return new ModelAndView(model,"newAnimal.hbs");
        },new HandlebarsTemplateEngine());

        get("/animals/new/endangered",(request, response) -> {
            Map<String,Object> model=new HashMap<>();
            List<String> health= new ArrayList<>();
            health.add(EndangeredAnimals.HEALTH_HEALTHY);
            health.add(EndangeredAnimals.HEALTH_ILL);
            health.add(EndangeredAnimals.HEALTH_OKAY);
            List<String> age= new ArrayList<>();
            age.add(EndangeredAnimals.AGE_ADULT);
            age.add(EndangeredAnimals.AGE_NEWBORN);
            age.add(EndangeredAnimals.AGE_YOUNG);
            model.put("health",health);
            model.put("age",age);
            String typeChosen="endangered";
            model.put("endangered",typeChosen);
            return new ModelAndView(model,"newAnimal.hbs");
        },new HandlebarsTemplateEngine());

        get("/view/animals",(request, response) -> {
            Map<String,Object> model=new HashMap<>();
            model.put("animals",Animals.all());
            return new ModelAndView(model,"animals.hbs");
        },new HandlebarsTemplateEngine());

        get("/sightings/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("rangers",Rangers.all());
            model.put("locations",Locations.all());
            model.put("animals",Animals.all());
            return new ModelAndView(model, "newSighting.hbs");
        }, new HandlebarsTemplateEngine());

        post("/sightings", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int location_id= Integer.parseInt(request.queryParams("locations"));
            int ranger_id= Integer.parseInt(request.queryParams("rangers"));
            int animal_id= Integer.parseInt(request.queryParams("animals"));
            Sightings sighting=new Sightings(location_id,ranger_id,animal_id);
            sighting.save();
            return new ModelAndView(model,"newSighting.hbs");
        }, new HandlebarsTemplateEngine());

        get("view/sightings", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Sightings> sightings=Sightings.all();
            ArrayList<String> animals=new ArrayList<>();
            ArrayList<String> types=new ArrayList<>();
            for (Sightings sighting : sightings){
                String animal_name=Animals.find(sighting.getAnimal_id()).getName();
                String animal_type=Animals.find(sighting.getAnimal_id()).getType();
                animals.add(animal_name);
                types.add(animal_type);
            }
            model.put("sightings",sightings);
            model.put("animals",animals);
            model.put("types",types);
            return new ModelAndView(model, "sightings.hbs");
        }, new HandlebarsTemplateEngine());

        get("/rangers/new",(request, response) -> {
            Map<String,Object> model=new HashMap<>();
            return new ModelAndView(model,"newRanger.hbs");
        },new HandlebarsTemplateEngine());

        post("/rangers",(request, response) -> {
            Map<String,Object> model=new HashMap<>();
            String name=request.queryParams("name");
            String badge_number=request.queryParams("badge");
            String email=request.queryParams("email");
            Rangers ranger=new Rangers(name,badge_number,email);
            ranger.save();
            return new ModelAndView(model,"newRanger.hbs");
        },new HandlebarsTemplateEngine());

        get("/view/rangers",(request, response) -> {
            Map<String,Object> model=new HashMap<>();
            model.put("rangers",Rangers.all());
            return new ModelAndView(model,"rangers.hbs");
        },new HandlebarsTemplateEngine());

        get("/view/rangers/sightings/:id",(request, response) -> {
            Map<String,Object> model=new HashMap<>();
            int idOfRanger= Integer.parseInt(request.params(":id"));
            Rangers foundRanger=Rangers.find(idOfRanger);
            List<Sightings> sightings=foundRanger.getRanger();
            ArrayList<String> animals=new ArrayList<>();
            ArrayList<String> types=new ArrayList<>();
            for (Sightings sighting : sightings){
                String animal_name=Animals.find(sighting.getAnimal_id()).getName();
                String animal_type=Animals.find(sighting.getAnimal_id()).getType();
                animals.add(animal_name);
                types.add(animal_type);
            }
            model.put("sightings",sightings);
            model.put("animals",animals);
            model.put("types",types);
            model.put("rangers",Rangers.all());
            return new ModelAndView(model,"rangers.hbs");
        },new HandlebarsTemplateEngine());

        get("/locations/new",(request, response) -> {
            Map<String,Object> model=new HashMap<>();
            return new ModelAndView(model,"newLocation.hbs");
        },new HandlebarsTemplateEngine());

        post("/locations",(request, response) -> {
            Map<String,Object> model=new HashMap<>();
            String name=request.queryParams("name");
            Locations location=new Locations(name);
            location.save();
            return new ModelAndView(model,"newLocation.hbs");
        },new HandlebarsTemplateEngine());

        get("/view/locations",(request, response) -> {
            Map<String,Object> model=new HashMap<>();
            model.put("locations",Locations.all());
            return new ModelAndView(model,"location.hbs");
        },new HandlebarsTemplateEngine());

        get("/view/locations/sightings/:id",(request, response) -> {
            Map<String,Object> model=new HashMap<>();
            int idOfLocation= Integer.parseInt(request.params(":id"));
            Locations foundLocation=Locations.find(idOfLocation);
            List<Sightings> sightings=foundLocation.getLocation();
            ArrayList<String> animals=new ArrayList<>();
            ArrayList<String> types=new ArrayList<>();
            for (Sightings sighting : sightings){
                String animal_name=Animals.find(sighting.getAnimal_id()).getName();
                String animal_type=Animals.find(sighting.getAnimal_id()).getType();
                animals.add(animal_name);
                types.add(animal_type);
            }
            model.put("sightings",sightings);
            model.put("animals",animals);
            model.put("types",types);
            model.put("locations",Locations.all());
            return new ModelAndView(model,"locations.hbs");
        },new HandlebarsTemplateEngine());
    }
}

