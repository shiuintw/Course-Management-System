package org.shiuintw.coursemanagementsystem.mapper;

import org.shiuintw.coursemanagementsystem.model.Instructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InstructorRowMapper implements RowMapper<Instructor> {
    @Override
    public Instructor mapRow(ResultSet resultSet, int i) throws SQLException {
        Instructor instructor = new Instructor();
        instructor.setId(resultSet.getString("id"));
        instructor.setDepartmentId(resultSet.getString("department_id"));
        instructor.setChineseName(resultSet.getString("Chinese_name"));
        instructor.setEnglishName(resultSet.getString("English_name"));
        instructor.setImage(resultSet.getString("image"));
        instructor.setTitle(resultSet.getString("title"));
        instructor.setDegree((resultSet.getString("degree")));
        instructor.setResearchField(resultSet.getString("research_field"));
        return instructor;
    }
}
