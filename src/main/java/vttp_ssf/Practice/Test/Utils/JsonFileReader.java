package vttp_ssf.Practice.Test.Utils;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import vttp_ssf.Practice.Test.Repository.TodoRepo;

import java.io.InputStream;

@Component
public class JsonFileReader implements CommandLineRunner {

    private static final String TODO_JSON = "static/todos.txt";

    @Autowired
    private TodoRepo todoRepo;

    //Task 2 - read the Json file provided and load it as List or Map object to Redis
    @Override
    public void run(String... args) {

        try {
            //loads the Json file as a resource from the class path
            //turns it into an InputStream
            InputStream inputS = getClass().getClassLoader().getResourceAsStream(TODO_JSON);

            //creating a JsonReader to read Json data from inputS
            JsonReader jsonReader = Json.createReader(inputS);

            //since our data is an array of JSON objects
            //we read the Array and store it in a JsonArray object
            JsonArray jsonArray = jsonReader.readArray();

            //saving the data to todo repo
            todoRepo.saveTodos(jsonArray);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
