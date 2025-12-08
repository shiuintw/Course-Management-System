package org.shiuintw.coursemanagementsystem.controller;

import jakarta.servlet.http.HttpSession;
import org.shiuintw.coursemanagementsystem.dto.UserRequest;
import org.shiuintw.coursemanagementsystem.model.User;
import org.shiuintw.coursemanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    // register
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new UserRequest());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") UserRequest userRequest,
                           Model model) {
        try {
            userService.register(userRequest);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    // login
    @GetMapping("/login")
    String showLoginPage(Model model) {
        model.addAttribute("user", new UserRequest());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") UserRequest userRequest,
                        HttpSession session,
                        Model model) {
        try {
            User user = userService.login(userRequest);
            session.setAttribute("user", user);
            return "redirect:/home";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
