package ru.job4j.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.service.AccidentService;

@Controller
public class IndexController {
    private final AccidentService accidentService;

    public IndexController(AccidentService accidentService) {
        this.accidentService = accidentService;
    }

    @GetMapping({"/", "", "/index"})
    public String index(Model model) {
        model.addAttribute("user", "Aleksey Grishko");
        model.addAttribute("accidents", accidentService.findAll());
        System.out.println(accidentService.findAll());
        return "index";
    }
}
