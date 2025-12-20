package org.shiuintw.coursemanagementsystem.dao.impl;

import org.shiuintw.coursemanagementsystem.dao.CourseDao;
import org.shiuintw.coursemanagementsystem.mapper.CourseResultSetExtractor;
import org.shiuintw.coursemanagementsystem.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CourseDaoImpl implements CourseDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public CourseDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    // --- util
    private Map<String, Object> parameterMap(Course courseRequest) {
        Map<String, Object> courseMap = new HashMap<>();
        courseMap.put("courseId", courseRequest.getId());
        courseMap.put("name", courseRequest.getName());
        courseMap.put("credit", courseRequest.getCredit());
        courseMap.put("hours", courseRequest.getHours());
        courseMap.put("maxStudentNumber", courseRequest.getMaxStudentNumber());
        // time need the manual config
        courseMap.put("buildingId", courseRequest.getBuildingId());
        // classroom need the manual config
        // instructorId need the manual config
        // category need the manual config
        // sql += "category0...

        return courseMap;
    }
    // --- end of util

    @Override
    public Course getCourseById(String id) {
        String sql = "SELECT * FROM course " +
                "LEFT JOIN course_time USING(:id) " +
                "LEFT JOIN course_classroom USING(:id) " +
                "LEFT JOIN course_instructor_id USING(:id) " +
                "LEFT JOIN course_category USING(:id) " +
                "WHERE id = :id";
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);

        List<Course> courseList = namedParameterJdbcTemplate.query(sql, map, new CourseResultSetExtractor());

        return (courseList != null && !courseList.isEmpty()) ? courseList.get(0) : null;
    }

    @Override
    public String addCourse(Course course) {
        String sql;
        Map<String, Object> map;
        // course
        sql = "INSERT INTO course(id, name, credit, hours, max_student_number, building_id) " +
                "VALUES(:id, :name, :credit, :hours, :max_student_number, :building_id)";
        map = parameterMap(course);
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map));

        // course_time
        sql = "INSERT INTO course_time(id, `time`) " +
                "VALUES(:id, :time)";
        for (String time : course.getTime()) {
            map = new HashMap<>();
            map.put("id", course.getId());
            map.put("time", time);
            namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map));
        }

        // course_classroom
        sql = "INSERT INTO course_classroom(id, classroom) " +
                "VALUES(:id, :classroom)";
        for (String classroom : course.getClassroom()) {
            map = new HashMap<>();
            map.put("id", course.getId());
            map.put("classroom", classroom);
            namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map));
        }

        // course_instructor_id
        sql = "INSERT INTO course_instructor_id(id, instructor_id) " +
                "VALUES(:id, :instructorId)";
        for (String instructorId : course.getInstructorId()) {
            map = new HashMap<>();
            map.put("id", course.getId());
            map.put("instructorId", instructorId);
            namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map));
        }

        // course_category
        sql = "INSERT INTO course_category(id, category) " +
                "VALUES(:id, :category)";
        for (String category : course.getCategory()) {
            map = new HashMap<>();
            map.put("id", course.getId());
            map.put("category", category);
            namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map));
        }

        return course.getId();
    }

    @Override
    public void updateCourse(Course course) {
        // todo
    }

    @Override
    public void deleteCourse(String id) {
        // todo
    }

    @Override
    public List<Course> getCourse(Course courseRequest) {
        // todo
        return List.of();
    }
}
