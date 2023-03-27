package com.example.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.time.LocalDate;

@Controller
public class MainController {

    @Autowired
    CoronaService coronaService;

    @GetMapping("/")
    public String LoadData(Model model) {
        try {
            coronaService.populate();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "DataLoaded";
    }

    @GetMapping("/findBetween")
    public String filterHome(Model model) {

        model.addAttribute("coronaData", coronaService.findByLastUpdate(LocalDate.now().minusYears(2)));
        return "MainTemplate";
    }

    @GetMapping("/home")
    public String homePage(Model model) {

        model.addAttribute("coronaData", coronaService.findAll());
        return "MainTemplate";
    }
}
