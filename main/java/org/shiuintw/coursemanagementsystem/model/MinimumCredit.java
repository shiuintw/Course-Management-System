package org.shiuintw.coursemanagementsystem.model;

import jakarta.validation.constraints.NotNull;

public class MinimumCredit {
    public MinimumCredit() {
        departmentId = null;
        basicScience = 0;
        compulsoryCourse = 0;
        electiveProgramCourse = 0;
        electiveProfessionalCourse = 0;
        freeElectiveCourse = 0;
        crossDisciplinaryProgram = 0;
        schoolBasicCoreCurriculumCourse = 0;
        schoolDomainCoreCurriculumCourse = 0;
        schoolLanguageCourse = 0;
        schoolPeCourse = 0;
        schoolServiceLearningCourse = 0;
        schoolStudentAcademicResearchEthicsEducationCourse = 0;
        schoolOnlineGenderEqualityEducationCourse = 0;
    }
    private String departmentId;
    @NotNull
    private Integer basicScience;
    @NotNull
    private Integer compulsoryCourse;
    @NotNull
    private Integer electiveProgramCourse;
    @NotNull
    private Integer electiveProfessionalCourse;
    @NotNull
    private Integer freeElectiveCourse;
    @NotNull
    private Integer crossDisciplinaryProgram;
    @NotNull
    private Integer schoolBasicCoreCurriculumCourse;
    @NotNull
    private Integer schoolDomainCoreCurriculumCourse;
    @NotNull
    private Integer schoolLanguageCourse;
    @NotNull
    private Integer schoolPeCourse;
    @NotNull
    private Integer schoolServiceLearningCourse;
    @NotNull
    private Integer schoolStudentAcademicResearchEthicsEducationCourse;
    @NotNull
    private Integer schoolOnlineGenderEqualityEducationCourse;

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getBasicScience() {
        return basicScience;
    }

    public void setBasicScience(Integer basicScience) {
        this.basicScience = basicScience;
    }

    public Integer getCompulsoryCourse() {
        return compulsoryCourse;
    }

    public void setCompulsoryCourse(Integer compulsoryCourse) {
        this.compulsoryCourse = compulsoryCourse;
    }

    public Integer getElectiveProgramCourse() {
        return electiveProgramCourse;
    }

    public void setElectiveProgramCourse(Integer electiveProgramCourse) {
        this.electiveProgramCourse = electiveProgramCourse;
    }

    public Integer getElectiveProfessionalCourse() {
        return electiveProfessionalCourse;
    }

    public void setElectiveProfessionalCourse(Integer electiveProfessionalCourse) {
        this.electiveProfessionalCourse = electiveProfessionalCourse;
    }

    public Integer getFreeElectiveCourse() {
        return freeElectiveCourse;
    }

    public void setFreeElectiveCourse(Integer freeElectiveCourse) {
        this.freeElectiveCourse = freeElectiveCourse;
    }

    public Integer getCrossDisciplinaryProgram() {
        return crossDisciplinaryProgram;
    }

    public void setCrossDisciplinaryProgram(Integer crossDisciplinaryProgram) {
        this.crossDisciplinaryProgram = crossDisciplinaryProgram;
    }

    public Integer getSchoolBasicCoreCurriculumCourse() {
        return schoolBasicCoreCurriculumCourse;
    }

    public void setSchoolBasicCoreCurriculumCourse(Integer schoolBasicCoreCurriculumCourse) {
        this.schoolBasicCoreCurriculumCourse = schoolBasicCoreCurriculumCourse;
    }

    public Integer getSchoolDomainCoreCurriculumCourse() {
        return schoolDomainCoreCurriculumCourse;
    }

    public void setSchoolDomainCoreCurriculumCourse(Integer schoolDomainCoreCurriculumCourse) {
        this.schoolDomainCoreCurriculumCourse = schoolDomainCoreCurriculumCourse;
    }

    public Integer getSchoolLanguageCourse() {
        return schoolLanguageCourse;
    }

    public void setSchoolLanguageCourse(Integer schoolLanguageCourse) {
        this.schoolLanguageCourse = schoolLanguageCourse;
    }

    public Integer getSchoolPeCourse() {
        return schoolPeCourse;
    }

    public void setSchoolPeCourse(Integer schoolPeCourse) {
        this.schoolPeCourse = schoolPeCourse;
    }

    public Integer getSchoolServiceLearningCourse() {
        return schoolServiceLearningCourse;
    }

    public void setSchoolServiceLearningCourse(Integer schoolServiceLearningCourse) {
        this.schoolServiceLearningCourse = schoolServiceLearningCourse;
    }

    public Integer getSchoolStudentAcademicResearchEthicsEducationCourse() {
        return schoolStudentAcademicResearchEthicsEducationCourse;
    }

    public void setSchoolStudentAcademicResearchEthicsEducationCourse(Integer schoolStudentAcademicResearchEthicsEducationCourse) {
        this.schoolStudentAcademicResearchEthicsEducationCourse = schoolStudentAcademicResearchEthicsEducationCourse;
    }

    public Integer getSchoolOnlineGenderEqualityEducationCourse() {
        return schoolOnlineGenderEqualityEducationCourse;
    }

    public void setSchoolOnlineGenderEqualityEducationCourse(Integer schoolOnlineGenderEqualityEducationCourse) {
        this.schoolOnlineGenderEqualityEducationCourse = schoolOnlineGenderEqualityEducationCourse;
    }
}
