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
        if (courseRequest == null) return List.of();
        if (courseRequest.getId() != null && courseRequest.getId().isEmpty())
            courseRequest.setId(null);
        if (courseRequest.getName() != null && courseRequest.getName().isEmpty())
            courseRequest.setName(null);
        if (courseRequest.getBuildingId() != null && courseRequest.getBuildingId().isEmpty())
            courseRequest.setBuildingId(null);
        return courseDao.searchCourse(courseRequest);
    }
}
