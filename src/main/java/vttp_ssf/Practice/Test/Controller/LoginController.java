package vttp_ssf.Practice.Test.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class LoginController {

    @GetMapping(path = {"/login", "/", "/index.html"})
    public String getLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("fullName") String fullname,
                        @RequestParam(value = "age", required = false) Integer age,
                        HttpSession session) {
        if (fullname == null || fullname.isEmpty() || age == null  || age < 0) {
            return "refused";
        }

        session.setAttribute("fullName", fullname);
        session.setAttribute("age", age);

        if (age < 10){
            return "underage";
        }

        return "redirect:/listing";
    }
}
