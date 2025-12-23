package org.shiuintw.coursemanagementsystem.controller;

import jakarta.servlet.http.HttpSession;
import org.shiuintw.coursemanagementsystem.model.Course;
import org.shiuintw.coursemanagementsystem.model.MinimumCredit;
import org.shiuintw.coursemanagementsystem.model.Take;
import org.shiuintw.coursemanagementsystem.model.User;
import org.shiuintw.coursemanagementsystem.service.CourseService;
import org.shiuintw.coursemanagementsystem.service.MinimumCreditService;
import org.shiuintw.coursemanagementsystem.service.TakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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
    private final MinimumCreditService minimumCreditService;

    @Autowired
    public CourseController(CourseService courseService,
                            TakeService takeService,
                            MinimumCreditService minimumCreditService) {
        this.courseService = courseService;
        this.takeService = takeService;
        this.minimumCreditService = minimumCreditService;
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
    private static final List<String> orderChoices = List.of(
            "id",
            "name",
            "credit",
            "hours",
            "max_student_number",
            "building_id"
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
            @RequestParam(defaultValue = "id") String orderBy,

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

        List<Course> courseList = courseService.searchCourse(courseRequest, orderBy);
        model.addAttribute("courseList", courseList);
        model.addAttribute("timeList", timeList);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("orderChoices", orderChoices);

        return "course";
    }
    // --- end of search
    // --- customized search
    @GetMapping("/customizedSearch")
    public String customizedSearch(HttpSession session,
                                   Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        // credit
        MinimumCredit minimumCredit = minimumCreditService.getMinimumCreditById(user.getDepartmentId());
        MinimumCredit userCredit = takeService.getCredit(user.getId(), minimumCredit);
        Course courseRequest = new Course();
        courseRequest.setCategory(new ArrayList<>());
        if (minimumCredit.getBasicScience() > userCredit.getBasicScience())
            courseRequest.getCategory().add("basic_science");

        if (minimumCredit.getCompulsoryCourse() > userCredit.getCompulsoryCourse())
            courseRequest.getCategory().add("compulsory_course");

        if (minimumCredit.getElectiveProgramCourse() > userCredit.getElectiveProgramCourse())
            courseRequest.getCategory().add("elective_program_course");

        if (minimumCredit.getElectiveProfessionalCourse() > userCredit.getElectiveProfessionalCourse())
            courseRequest.getCategory().add("elective_professional_course");

        if (minimumCredit.getFreeElectiveCourse() > userCredit.getFreeElectiveCourse())
            courseRequest.getCategory().add("free_elective_course");

        if (minimumCredit.getCrossDisciplinaryProgram() > userCredit.getCrossDisciplinaryProgram())
            courseRequest.getCategory().add("cross_disciplinary_program");

        if (minimumCredit.getSchoolBasicCoreCurriculumCourse() > userCredit.getSchoolBasicCoreCurriculumCourse())
            courseRequest.getCategory().add("school_basic_core_curriculum_course");

        if (minimumCredit.getSchoolDomainCoreCurriculumCourse() > userCredit.getSchoolDomainCoreCurriculumCourse())
            courseRequest.getCategory().add("school_domain_core_curriculum_course");

        if (minimumCredit.getSchoolLanguageCourse() > userCredit.getSchoolLanguageCourse())
            courseRequest.getCategory().add("school_language_course");

        if (minimumCredit.getSchoolPeCourse() > userCredit.getSchoolPeCourse())
            courseRequest.getCategory().add("school_pe_course");

        if (minimumCredit.getSchoolServiceLearningCourse() > userCredit.getSchoolServiceLearningCourse())
            courseRequest.getCategory().add("school_service_learning_course");

        if (minimumCredit.getSchoolStudentAcademicResearchEthicsEducationCourse()
                > userCredit.getSchoolStudentAcademicResearchEthicsEducationCourse())
            courseRequest.getCategory().add("school_student_academic_research_ethics_education_course");

        if (minimumCredit.getSchoolOnlineGenderEqualityEducationCourse()
                > userCredit.getSchoolOnlineGenderEqualityEducationCourse())
            courseRequest.getCategory().add("school_online_gender_equality_education_course");

        // model
        List<Course> courseList = courseService.searchCourse(courseRequest, "id");
        model.addAttribute("courseList", courseList);
        model.addAttribute("timeList", timeList);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("orderChoices", orderChoices);

        return "course";
    }
    // --- end of customized search

    // --- recommending courses
    // todo call searchCourse with param
    // todo return param with path
    // --- end of recommending courses

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

    // --- my course
    @GetMapping("/myCourse")
    public String myCourse(HttpSession session,
                           Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        // take list
        List<Take> takeList = takeService.getTakesByUserId(user.getId());
        model.addAttribute("takeList", takeList);

        // credit calculation
        MinimumCredit minimumCredit = minimumCreditService.getMinimumCreditById(user.getDepartmentId());
        MinimumCredit userCredit = takeService.getCredit(user.getId(), minimumCredit);
        model.addAttribute("minimumCredit", minimumCredit);
        model.addAttribute("userCredit", userCredit);

        return "myCourse";
    }

    @PostMapping("/myCourse/update/{courseId}")
    public String updateTake(@PathVariable String courseId,
                             @RequestParam String time,
                             @RequestParam String score,
                             HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Take take = new Take();
        take.setCourseId(courseId);
        take.setTime(time);
        take.setScore(score);
        take.setUserId(user.getId());

        takeService.updateTake(take);
        return "redirect:/course/myCourse";
    }

    @PostMapping("/myCourse/delete/{courseId}")
    public String deleteTake(@PathVariable String courseId,
                             HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        takeService.deleteTakeById(user.getId(), courseId);
        return "redirect:/course/myCourse";
    }
    // --- end of my course
}
