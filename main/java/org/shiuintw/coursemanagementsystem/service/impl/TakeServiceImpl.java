package org.shiuintw.coursemanagementsystem.service.impl;

import org.shiuintw.coursemanagementsystem.dao.CourseDao;
import org.shiuintw.coursemanagementsystem.dao.TakeDao;
import org.shiuintw.coursemanagementsystem.model.Course;
import org.shiuintw.coursemanagementsystem.model.MinimumCredit;
import org.shiuintw.coursemanagementsystem.model.Take;
import org.shiuintw.coursemanagementsystem.service.TakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TakeServiceImpl implements TakeService {
    private final TakeDao takeDao;
    private final CourseDao courseDao;

    @Autowired
    public TakeServiceImpl(TakeDao takeDao, CourseDao courseDao) {
        this.takeDao = takeDao;
        this.courseDao = courseDao;
    }

    @Override
    public Take getTakeById(String userId, String courseId) {
        return takeDao.getTakeById(userId, courseId);
    }

    @Override
    public boolean createTake(Take take) {
        Take cTake = takeDao.getTakeById(take.getUserId(), take.getCourseId());
        if (cTake != null) return false;
        takeDao.createTake(take);
        return true;
    }

    @Override
    public void updateTake(Take take) {
        takeDao.updateTake(take);
    }

    @Override
    public void deleteTakeById(String userId, String courseId) {
        takeDao.deleteTakeById(userId, courseId);
    }

    @Override
    public List<Take> getTakesByUserId(String userId) {
        return takeDao.getTakesByUserId(userId);
    }

    @Override
    public void deleteTakesByUserId(String userId) {
        takeDao.deleteTakesByUserId(userId);
    }

    // credit
    // --- util
    private static class EmbedCourse {
        Course course;
        boolean valid;
        EmbedCourse(Course course) {
            this.course = course;
            this.valid = true;
        }
    }
    // --- end of util
    @Override
    public MinimumCredit getCredit(String userId, MinimumCredit minimumCredit) {
        final List<String> programOrder = List.of(
            "basic_science",
            "compulsory_course",
            "elective_program_course",
            "elective_professional_course",
            "free_elective_course",
            "cross_disciplinary_program",
            "school_basic_core_curriculum_course",
            "school_domain_core_curriculum_course",
            "school_language_course",
            "school_pe_course",
            "school_service_learning_course",
            "school_student_academic_research_ethics_education_course",
            "school_online_gender_equality_education_course"
        );
        List<Take> takeList = takeDao.getTakesByUserId(userId);
        Map<String, List<EmbedCourse>> map = new HashMap<>();
        // default val
        for (String s : programOrder) {
            map.put(s, new ArrayList<>());
        }

        for (Take take : takeList) {
            EmbedCourse embedCourse = new EmbedCourse(courseDao.getCourseById(take.getCourseId()));
            for (String category : embedCourse.course.getCategory()) {
                if (category != null && !category.isEmpty())
                map.get(category).add(embedCourse);
            }
        }

        // sort
        for (List<EmbedCourse> embedCourseList : map.values()) {
            embedCourseList.sort((o1, o2) ->
                    o1.course.getCredit() - o2.course.getCredit());
        }

        // calculate
        MinimumCredit userCredit = new MinimumCredit();
        for (String po : programOrder) {
            for (EmbedCourse embedCourse : map.get(po)) {
                if (!embedCourse.valid) continue;
                boolean reachMinimum = switch (po) {
                    case "basic_science" ->
                            userCredit.getBasicScience() >= minimumCredit.getBasicScience();

                    case "compulsory_course" ->
                            userCredit.getCompulsoryCourse() >= minimumCredit.getCompulsoryCourse();

                    case "elective_program_course" ->
                            userCredit.getElectiveProgramCourse() >= minimumCredit.getElectiveProgramCourse();

                    case "elective_professional_course" ->
                            userCredit.getElectiveProfessionalCourse() >= minimumCredit.getElectiveProfessionalCourse();

                    case "free_elective_course" ->
                            userCredit.getFreeElectiveCourse() >= minimumCredit.getFreeElectiveCourse();

                    case "cross_disciplinary_program" ->
                            userCredit.getCrossDisciplinaryProgram() >= minimumCredit.getCrossDisciplinaryProgram();

                    case "school_basic_core_curriculum_course" ->
                            userCredit.getSchoolBasicCoreCurriculumCourse()
                                    >= minimumCredit.getSchoolBasicCoreCurriculumCourse();

                    case "school_domain_core_curriculum_course" ->
                            userCredit.getSchoolDomainCoreCurriculumCourse()
                                    >= minimumCredit.getSchoolDomainCoreCurriculumCourse();

                    case "school_language_course" ->
                            userCredit.getSchoolLanguageCourse() >= minimumCredit.getSchoolLanguageCourse();

                    case "school_pe_course" ->
                            userCredit.getSchoolPeCourse() >= minimumCredit.getSchoolPeCourse();

                    case "school_service_learning_course" ->
                            userCredit.getSchoolServiceLearningCourse()
                                    >= minimumCredit.getSchoolServiceLearningCourse();

                    case "school_student_academic_research_ethics_education_course" ->
                            userCredit.getSchoolStudentAcademicResearchEthicsEducationCourse()
                                    >= minimumCredit.getSchoolStudentAcademicResearchEthicsEducationCourse();

                    case "school_online_gender_equality_education_course" ->
                            userCredit.getSchoolOnlineGenderEqualityEducationCourse()
                                    >= minimumCredit.getSchoolOnlineGenderEqualityEducationCourse();

                    default -> false;
                };
                if (reachMinimum) break;
                embedCourse.valid = false;
                switch (po) {
                    case "basic_science" ->
                            userCredit.setBasicScience(
                                    userCredit.getBasicScience() + embedCourse.course.getCredit()
                            );
                    case "compulsory_course" ->
                            userCredit.setCompulsoryCourse(
                                    userCredit.getCompulsoryCourse() + embedCourse.course.getCredit()
                            );
                    case "elective_program_course" ->
                            userCredit.setElectiveProgramCourse(
                                    userCredit.getElectiveProgramCourse() + embedCourse.course.getCredit()
                            );
                    case "elective_professional_course" ->
                            userCredit.setElectiveProfessionalCourse(
                                    userCredit.getElectiveProfessionalCourse() + embedCourse.course.getCredit()
                            );
                    case "free_elective_course" ->
                            userCredit.setFreeElectiveCourse(
                                    userCredit.getFreeElectiveCourse() + embedCourse.course.getCredit()
                            );
                    case "cross_disciplinary_program" ->
                            userCredit.setCrossDisciplinaryProgram(
                                    userCredit.getCrossDisciplinaryProgram() + embedCourse.course.getCredit()
                            );
                    case "school_basic_core_curriculum_course" ->
                            userCredit.setSchoolBasicCoreCurriculumCourse(
                                    userCredit.getSchoolBasicCoreCurriculumCourse() + embedCourse.course.getCredit()
                            );
                    case "school_domain_core_curriculum_course" ->
                            userCredit.setSchoolDomainCoreCurriculumCourse(
                                    userCredit.getSchoolDomainCoreCurriculumCourse() + embedCourse.course.getCredit()
                            );
                    case "school_language_course" ->
                            userCredit.setSchoolLanguageCourse(
                                    userCredit.getSchoolLanguageCourse() + embedCourse.course.getCredit()
                            );
                    case "school_pe_course" ->
                            userCredit.setSchoolPeCourse(
                                    userCredit.getSchoolPeCourse() + embedCourse.course.getCredit()
                            );
                    case "school_service_learning_course" ->
                            userCredit.setSchoolServiceLearningCourse(
                                    userCredit.getSchoolServiceLearningCourse() + embedCourse.course.getCredit()
                            );
                    case "school_student_academic_research_ethics_education_course" ->
                            userCredit.setSchoolStudentAcademicResearchEthicsEducationCourse(
                                    userCredit.getSchoolStudentAcademicResearchEthicsEducationCourse()
                                            + embedCourse.course.getCredit()
                            );
                    case "school_online_gender_equality_education_course" ->
                            userCredit.setSchoolOnlineGenderEqualityEducationCourse(
                                    userCredit.getSchoolOnlineGenderEqualityEducationCourse()
                                            + embedCourse.course.getCredit()
                            );
                }
            }
        }
        for (String po : programOrder) {
            for (EmbedCourse embedCourse : map.get(po)) {
                if (!embedCourse.valid) continue;
                embedCourse.valid = false;
                switch (po) {
                    case "basic_science" ->
                            userCredit.setBasicScience(
                                    userCredit.getBasicScience() + embedCourse.course.getCredit()
                            );

                    case "compulsory_course" ->
                            userCredit.setCompulsoryCourse(
                                    userCredit.getCompulsoryCourse() + embedCourse.course.getCredit()
                            );

                    case "elective_program_course" ->
                            userCredit.setElectiveProgramCourse(
                                    userCredit.getElectiveProgramCourse() + embedCourse.course.getCredit()
                            );

                    case "elective_professional_course" ->
                            userCredit.setElectiveProfessionalCourse(
                                    userCredit.getElectiveProfessionalCourse() + embedCourse.course.getCredit()
                            );

                    case "free_elective_course" ->
                            userCredit.setFreeElectiveCourse(
                                    userCredit.getFreeElectiveCourse() + embedCourse.course.getCredit()
                            );

                    case "cross_disciplinary_program" ->
                            userCredit.setCrossDisciplinaryProgram(
                                    userCredit.getCrossDisciplinaryProgram() + embedCourse.course.getCredit()
                            );

                    case "school_basic_core_curriculum_course" ->
                            userCredit.setSchoolBasicCoreCurriculumCourse(
                                    userCredit.getSchoolBasicCoreCurriculumCourse() + embedCourse.course.getCredit()
                            );

                    case "school_domain_core_curriculum_course" ->
                            userCredit.setSchoolDomainCoreCurriculumCourse(
                                    userCredit.getSchoolDomainCoreCurriculumCourse() + embedCourse.course.getCredit()
                            );

                    case "school_language_course" ->
                            userCredit.setSchoolLanguageCourse(
                                    userCredit.getSchoolLanguageCourse() + embedCourse.course.getCredit()
                            );

                    case "school_pe_course" ->
                            userCredit.setSchoolPeCourse(
                                    userCredit.getSchoolPeCourse() + embedCourse.course.getCredit()
                            );

                    case "school_service_learning_course" ->
                            userCredit.setSchoolServiceLearningCourse(
                                    userCredit.getSchoolServiceLearningCourse() + embedCourse.course.getCredit()
                            );

                    case "school_student_academic_research_ethics_education_course" ->
                            userCredit.setSchoolStudentAcademicResearchEthicsEducationCourse(
                                    userCredit.getSchoolStudentAcademicResearchEthicsEducationCourse()
                                            + embedCourse.course.getCredit()
                            );

                    case "school_online_gender_equality_education_course" ->
                            userCredit.setSchoolOnlineGenderEqualityEducationCourse(
                                    userCredit.getSchoolOnlineGenderEqualityEducationCourse()
                                            + embedCourse.course.getCredit()
                            );
                }
            }
        }

        return userCredit;
    }
}
