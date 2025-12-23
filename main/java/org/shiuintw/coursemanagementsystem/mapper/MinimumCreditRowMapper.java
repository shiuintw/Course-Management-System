package org.shiuintw.coursemanagementsystem.mapper;

import org.shiuintw.coursemanagementsystem.model.MinimumCredit;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MinimumCreditRowMapper implements RowMapper<MinimumCredit> {
    @Override
    public MinimumCredit mapRow(ResultSet rs, int rowNum) throws SQLException {
        MinimumCredit minimumCredit = new MinimumCredit();
        minimumCredit.setDepartmentId(rs.getString("department_id"));
        minimumCredit.setBasicScience(rs.getInt("basic_science"));
        minimumCredit.setCompulsoryCourse(rs.getInt("compulsory_course"));
        minimumCredit.setElectiveProgramCourse(rs.getInt("elective_program_course"));
        minimumCredit.setElectiveProfessionalCourse(rs.getInt("elective_professional_course"));
        minimumCredit.setFreeElectiveCourse(rs.getInt("free_elective_course"));
        minimumCredit.setCrossDisciplinaryProgram(rs.getInt("cross_disciplinary_program"));
        minimumCredit.setSchoolBasicCoreCurriculumCourse(rs.getInt("school_basic_core_curriculum_course"));
        minimumCredit.setSchoolDomainCoreCurriculumCourse(rs.getInt("school_domain_core_curriculum_course"));
        minimumCredit.setSchoolLanguageCourse(rs.getInt("school_language_course"));
        minimumCredit.setSchoolPeCourse(rs.getInt("school_pe_course"));
        minimumCredit.setSchoolServiceLearningCourse(rs.getInt("school_service_learning_course"));
        minimumCredit.setSchoolStudentAcademicResearchEthicsEducationCourse(rs.getInt("school_student_academic_research_ethics_education_course"));
        minimumCredit.setSchoolOnlineGenderEqualityEducationCourse(rs.getInt("school_online_gender_equality_education_course"));
        return minimumCredit;
    }
}
