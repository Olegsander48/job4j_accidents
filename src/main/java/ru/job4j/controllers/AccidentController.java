package ru.job4j.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.models.Accident;
import ru.job4j.service.accident.AccidentService;
import ru.job4j.service.accidenttype.AccidentTypeService;
import ru.job4j.service.rule.RuleService;
import java.util.List;
import java.util.Optional;

@Controller
public class AccidentController {
    private final AccidentService accidentService;
    private final AccidentTypeService accidentTypeService;
    private final RuleService ruleService;

    public AccidentController(AccidentService accidentService, AccidentTypeService accidentTypeService, RuleService ruleService) {
        this.accidentService = accidentService;
        this.accidentTypeService = accidentTypeService;
        this.ruleService = ruleService;
    }

    @GetMapping("/createAccident")
    public String getCreationView(Model model) {
        model.addAttribute("types", accidentTypeService.findAll());
        model.addAttribute("rules", ruleService.findAll());
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String saveAccident(@ModelAttribute("accident") Accident accident,
                               @RequestParam("type.id") int id, @RequestParam("rIds") List<Integer> ids) {
        accident.setAccidentType(accidentTypeService.findById(id).get());
        accident.setRules(ruleService.findRulesByIds(ids));
        accidentService.save(accident);
        return "redirect:/";
    }

    @GetMapping("/updateAccident")
    public String getCreationView(@RequestParam int id, Model model) {
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
