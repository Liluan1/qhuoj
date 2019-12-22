package edu.qhu.qhuoj.controller;

import edu.qhu.qhuoj.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller()
public class PageController {
    @GetMapping(value = "/")
    public String homePage(User user, Model model) {
        model.addAttribute("student", user);
        return "index";
    }

    @GetMapping(value = "/login")
    public String login(){
        return "studentLogin";
    }

}
