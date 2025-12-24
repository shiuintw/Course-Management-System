package org.shiuintw.coursemanagementsystem.controller;

import org.shiuintw.coursemanagementsystem.model.Instructor;
import org.shiuintw.coursemanagementsystem.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @GetMapping("/{id}")
    @ResponseBody
    public Instructor getInstructor(@PathVariable String id) {
        return instructorService.getInstructorById(id);
    }
}
