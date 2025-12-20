package org.shiuintw.coursemanagementsystem.dao.impl;

import org.shiuintw.coursemanagementsystem.dao.InstructorDao;
import org.shiuintw.coursemanagementsystem.model.Instructor;
import org.shiuintw.coursemanagementsystem.mapper.InstructorRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InstructorDaoImpl implements InstructorDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public InstructorDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    // --- util
    private Map<String, Object> parameterMap(Instructor instructor) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", instructor.getId());
        map.put("departmentId", instructor.getDepartmentId());
        map.put("ChineseName", instructor.getChineseName());
        map.put("EnglishName", instructor.getEnglishName());
        map.put("image", instructor.getImage());
        map.put("title", instructor.getTitle());
        map.put("degree", instructor.getDegree());
        map.put("researchField", instructor.getResearchField());
        return map;
    }
    // --- end of util

    @Override
    public Instructor getInstructorById(String id) {
        String sql = "SELECT * FROM instructor WHERE id = :id";
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);

        List<Instructor> instructorList = namedParameterJdbcTemplate.query(sql, map, new InstructorRowMapper());

        return !instructorList.isEmpty() ? instructorList.get(0) : null;
    }

    @Override
    public String createInstructor(Instructor instructor) {
        String sql = "INSERT INTO instructor(id, department_id, Chinese_name, English_name, image," +
                "title, degree, research_field) " +
                "VALUES(:id, :departmentId, :ChineseName, :EnglishName, :image," +
                ":title, :degree, :researchField)";

        Map<String, Object> map = parameterMap(instructor);

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map));

        return instructor.getId();
    }

    @Override
    public void updateInstructor(Instructor instructor) {
        String sql = "UPDATE instructor SET department_id = :departmentId, Chinese_name = :ChineseName," +
                "English_name = :EnglishName, image = :image, title = :title, degree = :degree," +
                "research_field = :researchField WHERE id = :id";

        Map<String, Object> map = parameterMap(instructor);

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map));
    }

    @Override
    public void deleteInstructorById(String id) {
        String sql = "DELETE FROM instructor WHERE id = :id";

        Map<String, Object> map = new HashMap<>();
        map.put("id", id);

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map));
    }
}
