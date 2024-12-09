package vttp_ssf.Practice.Test.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vttp_ssf.Practice.Test.Service.TodoService;

@Controller
@RequestMapping
public class DeleteUpdateController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/delete/{orderId}")
    public String getDelete(@PathVariable("orderId") String orderId, Model model) {

        todoService.deleteTask(orderId);

        return "redirect:/listing";
    }

    @GetMapping("/update/{orderId}")
    public String getUpdate(@PathVariable("orderId") String orderId, Model model) {

        model.addAttribute("todo", todoService.getTaskById(orderId));
        model.addAttribute("todo_id", orderId);

        return "add";
    }
}
