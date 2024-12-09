package vttp_ssf.Practice.Test.Repository;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import vttp_ssf.Practice.Test.Model.Todo;

import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import static vttp_ssf.Practice.Test.Utils.DateConverter.*;

@Repository
public class TodoRepo {

    @Autowired
    @Qualifier("redis-0")
    private RedisTemplate<String, String> redisTemplate;

    //hset id "field" "value"
    public void saveTodos (JsonArray jsonArr) {

        // Redis Hash is data structure that stores a collection of key-value pairs, similar to a Java Map.
        //Hashoperations allows you to manage this data structure,
        //such as adding, finding or removing
        //<String, String, String> are the <key:, field:, value: >
        //think of Redis Hash as a shelf in a library that holds a collection of books
        //each shelf has a unique title (key), for example, Fiction
        //the books in the shelf would be your fields, e.g. The Great Gatsby
        //any info of the book would be your value, it could be the author's name, or publication date etc
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();


        //looping through a Json Array
        for (int i = 0; i < jsonArr.size(); i++) {

            JsonObject jsonObj = jsonArr.getJsonObject(i); //represents the JsonObj in the JsonArr

            String id = jsonObj.getString("id"); //retrieves the id of the current JsonObj
            //the id is then used as a "key" for the Redis Hash (title for the shelf)


            //hashops.put stores the data in the Redis hash
            //id is used as the key in this case
            //using id as the key for each put. function but getting and storing diff attributes
            hashOps.put(id, "name", jsonObj.getString("name"));
            hashOps.put(id, "description", jsonObj.getString("description"));
            hashOps.put(id, "due_date", String.valueOf(dateToEpochMilliseconds(strToDate(jsonObj.getString("due_date")))));
            hashOps.put(id, "priority_level", jsonObj.getString("priority_level"));
            hashOps.put(id, "status", jsonObj.getString("status"));
            hashOps.put(id, "created_at", String.valueOf(dateToEpochMilliseconds(strToDate(jsonObj.getString("created_at")))));
            hashOps.put(id, "updated_at", String.valueOf(dateToEpochMilliseconds(strToDate(jsonObj.getString("updated_at")))));

        }
    }

    // keys *
    public Set<String> getAllId(){ //Set<String> used because it does not allow duplicates

        //retrieves all keys in Redis database
        return redisTemplate.keys("*");
    }

    //hgetall id
    public Todo getInfoById(String id) { //returns a Todo object, using id as the unique identifier

        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();

        //retrieves all fields and associated values stored at the key 'id'
        //returns all the info in a map
        Map<String, String> info = hashOps.entries(id);

        //constructing a new Todo object using the data retrieved from Redis
        //getting the info from the Map
        return new Todo(id,
                info.get("name"),
                info.get("description"),
                epochMillisecondsToDate(Long.parseLong(info.get("due_date"))),
                info.get("priority_level"),
                info.get("status"),
                epochMillisecondsToDate(Long.parseLong(info.get("created_at"))),
                epochMillisecondsToDate(Long.parseLong(info.get("updated_at")))
                );
    }

    //hset id field value
    public void addTodo (Todo todo){ //adding Todo object to Redis database using HashOperations

        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();

        //retrieves the unique identified ('id')
        //this id is used as a key in the redis hash
        String id = todo.getId();

        hashOps.put(id, "name", todo.getName());
        hashOps.put(id, "description", todo.getDescription());
        hashOps.put(id, "due_date", String.valueOf(dateToEpochMilliseconds(todo.getDueDate())));
        hashOps.put(id, "priority_level", todo.getPriority());
        hashOps.put(id, "status", todo.getStatus());
        hashOps.put(id, "created_at", String.valueOf(dateToEpochMilliseconds(todo.getCreatedAt())));
        hashOps.put(id, "updated_at", String.valueOf(dateToEpochMilliseconds(todo.getUpdatedAt())));


    }

    //del id
    public void deleteTodo (String id) {

        redisTemplate.delete(id);
    }
}
