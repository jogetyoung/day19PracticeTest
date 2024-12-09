package vttp_ssf.Practice.Test.Service;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vttp_ssf.Practice.Test.Model.Todo;
import vttp_ssf.Practice.Test.Repository.TodoRepo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TodoService {

    @Autowired
    private TodoRepo todoRepo;

    // retrieving all Todo tasks from the repo
    public List<Todo> getTasks() {

        //todoRepo.getAllId() retrieves all the IDs of the Todo items(tasks) and stores them in a Set<String> to ensure uniqueness
        Set<String> ids = todoRepo.getAllId();

        // create a List to hold Todo objects
        List<Todo> todoList = new ArrayList<>();

        // go through each id and retrieve the Todo object assigned to the Id
        for (String id : ids) {
            Todo todo = todoRepo.getInfoById(id); //retrieve the Todo object by its id
            todoList.add(todo); // store each Todo object in the list

        }

        return todoList; // return the list
    }

    public void deleteTask(String id) {

        todoRepo.deleteTodo(id);
    }

    public Todo getTaskById (String id) {

        return todoRepo.getInfoById(id);

    }
}


