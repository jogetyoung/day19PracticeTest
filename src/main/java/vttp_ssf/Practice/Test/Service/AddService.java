package vttp_ssf.Practice.Test.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vttp_ssf.Practice.Test.Model.Todo;
import vttp_ssf.Practice.Test.Repository.TodoRepo;

@Service
public class AddService {

    @Autowired
    private TodoRepo todoRepo;

    public void addToRedis(Todo newTodo) {

        todoRepo.addTodo(newTodo);

        System.out.println("working?");
    }
}
