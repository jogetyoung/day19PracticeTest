package vttp_ssf.Practice.Test.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vttp_ssf.Practice.Test.Model.Todo;
import vttp_ssf.Practice.Test.Service.AddService;

import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("/add")
public class AddController {

    @Autowired
    private AddService addService;

    @GetMapping
    public String getAdd(Model model) {

        Todo newTodo = new Todo();

        model.addAttribute("todo_id", UUID.randomUUID().toString());
        model.addAttribute("todo", newTodo);

        return "add";
    }

    @PostMapping
    public String postAdd(@Valid Todo todo, BindingResult bindingResult,
                          @RequestBody MultiValueMap<String, String> form,
                          Model model){

        if (bindingResult.hasErrors()) {
            model.addAttribute("todo", todo);
            return "add";
        }

        //auto populates your createdAt and updatedAt when adding new Todo task
        if(todo.getCreatedAt()==null) {
            todo.setCreatedAt(new Date());
        }
        todo.setUpdatedAt(new Date());

        todo.setId(form.getFirst("id"));

        addService.addToRedis(todo);

        Todo newTodo = new Todo();

        model.addAttribute("todo_id", UUID.randomUUID().toString());
        model.addAttribute("todo", newTodo);

        return "add";

    }

}
