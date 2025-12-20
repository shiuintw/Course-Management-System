package org.shiuintw.coursemanagementsystem.service;

import org.shiuintw.coursemanagementsystem.model.Course;

import java.util.List;

public interface CourseService {
    List<Course> searchCourse(Course courseRequest);
}
