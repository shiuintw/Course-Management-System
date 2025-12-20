package org.shiuintw.coursemanagementsystem.dao;

import org.shiuintw.coursemanagementsystem.model.Course;

import java.util.List;

public interface CourseDao {
    // basic
    Course getCourseById(String id);
    String addCourse(Course course);
    void updateCourse(Course course);
    void deleteCourse(String id);

    // search
    List<Course> searchCourse(Course courseRequest);
}
