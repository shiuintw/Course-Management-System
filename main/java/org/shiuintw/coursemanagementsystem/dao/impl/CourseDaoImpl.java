package org.shiuintw.coursemanagementsystem.dao.impl;

import org.shiuintw.coursemanagementsystem.dao.CourseDao;
import org.shiuintw.coursemanagementsystem.mapper.CourseResultSetExtractor;
import org.shiuintw.coursemanagementsystem.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
                "LEFT JOIN course_time USING(id) " +
                "LEFT JOIN course_classroom USING(id) " +
                "LEFT JOIN course_instructor_id USING(id) " +
                "LEFT JOIN course_category USING(id) " +
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
    public List<Course> searchCourse(Course courseRequest) {
        String sql = "SELECT * FROM course " +
                "LEFT JOIN course_time USING(id) " +
                "LEFT JOIN course_classroom USING(id) " +
                "LEFT JOIN course_instructor_id USING(id) " +
                "LEFT JOIN course_category USING(id) WHERE 1 = 1";
        Map<String, Object> map = new HashMap<>();

        // search param
        if (courseRequest.getId() != null) {
            sql += " AND id = :id";
            map.put("id", courseRequest.getId());
        }

        if (courseRequest.getName() != null) {
            sql += " AND name LIKE :name";
            map.put("name", "%" + courseRequest.getName() + "%");
        }

        if (courseRequest.getCredit() != null) {
            sql += " AND credit = :credit";
            map.put("credit", courseRequest.getCredit());
        }

        if (courseRequest.getHours() != null) {
            sql += " AND hours = :hours";
            map.put("hours", courseRequest.getHours());
        }

        if (courseRequest.getMaxStudentNumber() != null) {
            sql += " AND max_student_number = :maxStudentNumber";
            map.put("maxStudentNumber", courseRequest.getMaxStudentNumber());
        }

        if (courseRequest.getTime() != null && !courseRequest.getTime().isEmpty()) {
            sql += " AND `time` IN (:time)";
            map.put("time", courseRequest.getTime());
        }

        if (courseRequest.getBuildingId() != null) {
            sql += " AND building_id = :buildingId";
            map.put("buildingId", courseRequest.getBuildingId());
        }

        if (courseRequest.getClassroom() != null && !courseRequest.getClassroom().isEmpty()) {
            sql += " AND classroom IN (:classroom)";
            map.put("classroom", courseRequest.getClassroom());
        }

        if (courseRequest.getInstructorId() != null && !courseRequest.getInstructorId().isEmpty()) {
            sql += " AND instructor_id IN (:instructorId)";
            map.put("instructorId", courseRequest.getInstructorId());
        }

        if (courseRequest.getCategory() != null && !courseRequest.getCategory().isEmpty()) {
            sql += " AND category IN (:category)";
            map.put("category", courseRequest.getCategory());
        }

        List<Course> tempCourse = namedParameterJdbcTemplate.query(sql, map, new CourseResultSetExtractor());

        List<Course> completeCourseList = new ArrayList<>();
        if (tempCourse != null && !tempCourse.isEmpty()) {
            for (Course c : tempCourse) {
                completeCourseList.add(getCourseById((c.getId())));
            }
        }
        return completeCourseList;
    }
}
