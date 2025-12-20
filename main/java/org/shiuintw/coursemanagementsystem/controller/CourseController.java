package org.shiuintw.coursemanagementsystem.controller;

import org.shiuintw.coursemanagementsystem.model.Course;
import org.shiuintw.coursemanagementsystem.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/// Course Controller
/// * Manage takes & course
///
/// --- user functions
/// Search Course | by param
/// Add Course to Take record | update
/// Recommended Course
///
/// --- admin functions
/// batch load data
/// delete data

@Controller
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/search")
    public String searchCourse(
            // filter
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer credit,
            @RequestParam(required = false) Integer hours,
            @RequestParam(required = false) Integer maxStudentNumber,
            @RequestParam(required = false) List<String> time,
            @RequestParam(required = false) String buildingId,
            @RequestParam(required = false) List<String> classroom,
            @RequestParam(required = false) List<String> instructorId,
            @RequestParam(required = false) List<String> category,

            // model
            Model model) {

        Course courseRequest = new Course();
        courseRequest.setId(id);
        courseRequest.setName(name);
        courseRequest.setCredit(credit);
        courseRequest.setHours(hours);
        courseRequest.setMaxStudentNumber(maxStudentNumber);
        courseRequest.setTime(time);
        courseRequest.setBuildingId(buildingId);
        courseRequest.setClassroom(classroom);
        courseRequest.setInstructorId(instructorId);
        courseRequest.setCategory(category);

        List<Course> courseList = courseService.searchCourse(courseRequest);
        model.addAttribute("courseList", courseList);

        return "course";
    }
}
