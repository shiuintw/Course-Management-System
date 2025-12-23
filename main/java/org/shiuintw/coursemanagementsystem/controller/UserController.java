package org.shiuintw.coursemanagementsystem.controller;

import jakarta.servlet.http.HttpSession;
import org.shiuintw.coursemanagementsystem.dto.UserRequest;
import org.shiuintw.coursemanagementsystem.model.User;
import org.shiuintw.coursemanagementsystem.service.DepartmentService;
import org.shiuintw.coursemanagementsystem.service.TakeService;
import org.shiuintw.coursemanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final TakeService takeService;
    private final DepartmentService departmentService;

    @Autowired
    public UserController(final UserService userService,
                          TakeService takeService,
                          DepartmentService departmentService) {
        this.userService = userService;
        this.takeService = takeService;
        this.departmentService = departmentService;
    }
    // --- redirect
    @GetMapping("/")
    public String index() {
        return "redirect:home";
    }
    // ---

    // --- account management
    // register
    @GetMapping("/register")
    public String showRegisterPage(HttpSession session,
                                   Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) return "redirect:/home";

        model.addAttribute("departmentIdList", departmentService.getAllDepartments());
        model.addAttribute("user", new UserRequest());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") UserRequest userRequest,
                           Model model) {
        try {
            userService.register(userRequest);
            return "redirect:/login";
        } catch (ResponseStatusException e) {
            model.addAttribute("error", e.getReason());
            return "register";
        }
    }

    // login
    @GetMapping("/login")
    String showLoginPage(HttpSession session,
                         Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) return "redirect:/home";
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
        } catch (ResponseStatusException  e) {
            model.addAttribute("error", e.getReason());
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
    // --- end of account management

    // --- user profile
    // profile
    @GetMapping("profile")
    public String showProfile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "profile";
    }

    // update
    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute UserRequest userRequest,
                          HttpSession session,
                          Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        userRequest.setId(user.getId());
        try {
            userService.updateUser(userRequest);
        } catch(ResponseStatusException e) {
            model.addAttribute("error", e.getReason());
            model.addAttribute("user", user);
            return "profile";
        }
        session.setAttribute("user", userService.getUserById(user.getId()));
        return "redirect:/profile";
    }

    // delete
    @GetMapping("/profile/delete")
    public String deleteAccount(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        userService.deleteUserById(user.getId());
        takeService.deleteTakesByUserId(user.getId());
        session.invalidate();
        return "redirect:/login";
    }
    // --- end of user profile
}
