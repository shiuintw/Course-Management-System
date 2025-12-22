package org.shiuintw.coursemanagementsystem.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.server.PathParam;
import org.shiuintw.coursemanagementsystem.model.Course;
import org.shiuintw.coursemanagementsystem.model.Take;
import org.shiuintw.coursemanagementsystem.model.User;
import org.shiuintw.coursemanagementsystem.service.CourseService;
import org.shiuintw.coursemanagementsystem.service.TakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private final TakeService takeService;

    @Autowired
    public CourseController(CourseService courseService, TakeService takeService) {
        this.courseService = courseService;
        this.takeService = takeService;
    }

    // --- util
    private static final List<String> timeList = List.of(
            "M1", "M2", "M3", "M4", "M5", "M6", "M7", "M8", "M9", "My", "Mz", "Ma", "Mb", "Mc", "Md", "Mn",
            "T1", "T2", "T3", "T4", "T5", "T6", "T7", "T8", "T9", "Ty", "Tz", "Ta", "Tb", "Tc", "Td", "Tn",
            "W1", "W2", "W3", "W4", "W5", "W6", "W7", "W8", "W9", "Wy", "Wz", "Wa", "Wb", "Wc", "Wd", "Wn",
            "R1", "R2", "R3", "R4", "R5", "R6", "R7", "R8", "R9", "Ry", "Rz", "Ra", "Rb", "Rc", "Rd", "Rn",
            "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "Fy", "Fz", "Fa", "Fb", "Fc", "Fd", "Fn",
            "S1", "S2", "S3", "S4", "S5", "S6", "S7", "S8", "S9", "Sy", "Sz", "Sa", "Sb", "Sc", "Sd", "Sn",
            "U1", "U2", "U3", "U4", "U5", "U6", "U7", "U8", "U9", "Uy", "Uz", "Ua", "Ub", "Uc", "Ud", "Un"
    );
    private static final List<String> categoryList = List.of(
            "basic_science",
            "compulsory_course",
            "elective_program_course",
            "elective_professional_course",
            "free_elective_course",
            "cross-disciplinary_Program",
            "school_basic_core_curriculum_course",
            "school_domain_core_curriculum_course",
            "school_language_course",
            "school_PE_course",
            "school_service-learning_course",
            "school_student_academic_research_ethics_education_course",
            "school_online_gender_equality_education_course"
    );
    // --- end of util

    // --- search
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
            @RequestParam(required = false) String classroomStr,
            @RequestParam(required = false) String instructorIdStr,
            @RequestParam(required = false) List<String> category,

            // order
            // todo

            // session
            HttpSession session,

            // model
            Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        Course courseRequest = new Course();
        courseRequest.setId(id);
        courseRequest.setName(name);
        courseRequest.setCredit(credit);
        courseRequest.setHours(hours);
        courseRequest.setMaxStudentNumber(maxStudentNumber);
        courseRequest.setTime(time);
        courseRequest.setBuildingId(buildingId);
        if (classroomStr != null && !classroomStr.isEmpty())
            courseRequest.setClassroom(Arrays.stream(classroomStr.split(",")).collect(Collectors.toList()));
        if (instructorIdStr != null && !instructorIdStr.isEmpty())
            courseRequest.setInstructorId(Arrays.stream(instructorIdStr.split(",")).collect(Collectors.toList()));
        courseRequest.setCategory(category);

        List<Course> courseList = courseService.searchCourse(courseRequest);
        model.addAttribute("courseList", courseList);

        model.addAttribute("timeList", timeList);

        model.addAttribute("categoryList", categoryList);

        return "course";
    }
    // --- end of search

    // --- taking course
    @PostMapping("/take/{courseId}")
    public ResponseEntity<Take> takeCourse(@PathVariable String courseId,
                                           HttpSession session) {
        User user = (User) session.getAttribute("user");
        Take take = new Take();
        take.setCourseId(courseId);
        take.setUserId(user.getId());
        if (!takeService.createTake(take))
            return ResponseEntity.status(HttpStatus.OK).body(null);

        return ResponseEntity.status(HttpStatus.CREATED).body(take);
    }

    @GetMapping("/take/{courseId}")
    @ResponseBody
    public Map<String, Boolean> isCourseTaken(@PathVariable String courseId,
                                              HttpSession session) {
        User user = (User) session.getAttribute("user");
        boolean taken = takeService.getTakeById(user.getId(), courseId) != null;
        return Collections.singletonMap("taken", taken);
    }
    // --- end of taking course
    // todo rud my course
    // --- my course
    // --- end of my course
}
