package org.shiuintw.coursemanagementsystem.model;

import java.util.List;

public class Course {
    private String id;
    private String name;
    private Integer credit;
    private Integer hours;
    private Integer maxStudentNumber;
    private List<String> time;
    private String buildingId;
    private List<String> classroom;
    private List<String> instructorId;
    private List<String> category;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Integer getMaxStudentNumber() {
        return maxStudentNumber;
    }

    public void setMaxStudentNumber(Integer maxStudentNumber) {
        this.maxStudentNumber = maxStudentNumber;
    }

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public List<String> getClassroom() {
        return classroom;
    }

    public void setClassroom(List<String> classroom) {
        this.classroom = classroom;
    }

    public List<String> getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(List<String> instructorId) {
        this.instructorId = instructorId;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }
}
