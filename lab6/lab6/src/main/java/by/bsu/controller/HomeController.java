package by.bsu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController  {

    @GetMapping(value={"/home", "/"}
    )
    public String getStart(Model model) {
        return "home";
    }
}