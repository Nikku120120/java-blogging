package com.example.app;

import com.opencsv.CSVReader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.FileNotFoundException;
import java.io.FileReader;

@Controller
public class MainController {
    @GetMapping("/")
    public String root(Model model) throws FileNotFoundException {
        var csvFile = "C:\\Users\\nithi\\Downloads\\03-01-2023.csv";
        CSVReader reader;
        reader = new CSVReader(new FileReader(csvFile));
        model.addAttribute("test", "Hello Nithish");
        return "MainTemplate";
    }
}
