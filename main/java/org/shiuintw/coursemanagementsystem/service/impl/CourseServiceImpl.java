package org.shiuintw.coursemanagementsystem.service.impl;

import org.shiuintw.coursemanagementsystem.dao.CourseDao;
import org.shiuintw.coursemanagementsystem.model.Course;
import org.shiuintw.coursemanagementsystem.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseServiceImpl implements CourseService {
    final private CourseDao courseDao;

    @Autowired
    public CourseServiceImpl(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @Override
    public List<Course> searchCourse(Course courseRequest) {
        return courseDao.searchCourse(courseRequest);
    }
}
