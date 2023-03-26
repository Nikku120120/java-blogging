package com.example.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    CoronaService coronaService;

    @GetMapping("/")
    public String root(Model model) {
        coronaService.populate();
        model.addAttribute("test", "Hello Nithish");
        return "MainTemplate";
    }
}
