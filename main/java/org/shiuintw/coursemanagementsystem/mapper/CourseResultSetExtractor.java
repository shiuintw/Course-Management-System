package org.shiuintw.coursemanagementsystem.mapper;

import org.shiuintw.coursemanagementsystem.model.Course;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseResultSetExtractor implements ResultSetExtractor<List<Course>> {
    @Override
    public List<Course> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<String, Course> courseMap = new HashMap<>();

        while (rs.next()) {
            String id = rs.getString("id");
            if (courseMap.containsKey(id)) {
                Course course = courseMap.get(id);
                course.getTime().add(rs.getString("time"));
                course.getClassroom().add(rs.getString("classroom"));
                course.getInstructorId().add(rs.getString("instructor_id"));
                course.getCategory().add(rs.getString("category"));
            } else {
                Course course = new Course();
                course.setId(id);
                course.setName(rs.getString("name"));
                course.setCredit(rs.getInt("credit"));
                course.setHours(rs.getInt("hours"));
                course.setMaxStudentNumber(rs.getInt("max_student_number"));
                course.setTime(new ArrayList<>());
                course.getTime().add(rs.getString("time"));
                course.setBuildingId(rs.getString("building_id"));
                course.setClassroom(new ArrayList<>());
                course.getClassroom().add(rs.getString("classroom"));
                course.setInstructorId(new ArrayList<>());
                course.getInstructorId().add(rs.getString("instructor_id"));
                course.setCategory(new ArrayList<>());
                course.getCategory().add(rs.getString("category"));
                courseMap.put(id, course);
            }
        }
        return new ArrayList<>(courseMap.values());
    }
}
