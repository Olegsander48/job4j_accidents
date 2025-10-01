package ru.job4j.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.models.User;
import ru.job4j.service.authority.AuthorityService;
import ru.job4j.service.user.UserService;

@Controller
public class RegistrationController {
    private final UserService userService;
    private final AuthorityService authorityService;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UserService userService, AuthorityService authorityService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authorityService = authorityService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user, Model model) {
        try {
            user.setEnabled(true);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setAuthority(authorityService.findByAuthority("ROLE_USER"));
            userService.save(user);
            return "redirect:/login";
        } catch (DataIntegrityViolationException exception) {
            model.addAttribute("errorMessage", "Username or password is incorrect");
            return "register";
        }
    }

    @GetMapping("/register")
    public String registrationPage() {
        return "register";
    }
}
