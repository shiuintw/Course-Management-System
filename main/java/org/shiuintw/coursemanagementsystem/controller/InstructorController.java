package org.shiuintw.coursemanagementsystem.controller;

import jakarta.servlet.http.HttpSession;
import org.shiuintw.coursemanagementsystem.model.Building;
import org.shiuintw.coursemanagementsystem.model.Instructor;
import org.shiuintw.coursemanagementsystem.model.User;
import org.shiuintw.coursemanagementsystem.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class InstructorController {
    InstructorService instructorService;
    @Autowired
    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping("instructor/{instructorId}")
    public String getBuilding(@PathVariable String instructorId, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        if (instructorId == null || instructorId.isEmpty() || instructorId.equals("Unknown")) {
            return "redirect:/course/search";
        }
        Instructor instructor = instructorService.getInstructorById(instructorId);
        model.addAttribute("instructor", instructor);
        return "instructor";
    }
}
