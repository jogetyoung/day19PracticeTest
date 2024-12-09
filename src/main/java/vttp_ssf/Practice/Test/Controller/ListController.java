package vttp_ssf.Practice.Test.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vttp_ssf.Practice.Test.Model.Todo;
import vttp_ssf.Practice.Test.Service.TodoService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class ListController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/listing")
    public String getListing(Model model, HttpSession session) {

        String fullName = (String) session.getAttribute("fullName");

        if (fullName == null) {
            return "refused";
        }

        List<Todo> todos = todoService.getTasks();

        model.addAttribute("todos", todos);

        return "listing";

    }

    @GetMapping("/listing/filter")
    public String getListingFilter(@RequestParam(required = false) String status, Model model) {

        List<Todo> todos = todoService.getTasks();

        List<Todo> data = new ArrayList<>();

        if (status != null && !status.isEmpty()) {
            for (Todo todo : todos) {
                if (todo.getStatus().equalsIgnoreCase(status)) {
                    data.add(todo);
                }
            }
            model.addAttribute("todos", data); // Add the filtered list to the model
        } else { // this allows me to filter back to ALL
            data = todoService.getTasks();
            model.addAttribute("todos", data); // If no filter, return all todos
        }

        return "listing";
    }
}
