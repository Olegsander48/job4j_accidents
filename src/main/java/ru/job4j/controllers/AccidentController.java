package ru.job4j.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.models.Accident;
import ru.job4j.service.AccidentService;

import java.util.Optional;

@Controller
public class AccidentController {
    private final AccidentService accidentService;

    public AccidentController(AccidentService accidentService) {
        this.accidentService = accidentService;
    }

    @GetMapping("/createAccident")
    public String getCreationView() {
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String saveAccident(@ModelAttribute("accident") Accident accident) {
        accidentService.save(accident);
        return "redirect:/";
    }

    @GetMapping("/updateAccident/{id}")
    public String getCreationView(@PathVariable int id, Model model) {
        Optional<Accident> accident = accidentService.findById(id);
        if (accident.isEmpty()) {
            model.addAttribute("message", "Accident not found");
            return "fragments/errors/404";
        }
        model.addAttribute("accident", accident.get());
        return "editAccident";
    }

    @PostMapping("/updateAccident")
    public String updateAccident(@ModelAttribute("accident") Accident accident) {
        accidentService.update(accident);
        return "redirect:/";
    }
}
