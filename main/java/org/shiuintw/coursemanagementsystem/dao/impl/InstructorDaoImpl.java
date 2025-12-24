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

    @Override
    public List<Instructor> getAllInstructors() {
        String sql = "SELECT * FROM instructor";
        List<Instructor> instructorList = namedParameterJdbcTemplate.query(sql, new InstructorRowMapper());
        return instructorList;
    }

    @Override
    public Instructor getInstructorById(String id) {
        String sql = "SELECT * FROM instructor WHERE id = :id";
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        List<Instructor> instructorList = namedParameterJdbcTemplate.query(sql, map, new InstructorRowMapper());
        return instructorList.isEmpty() ? null : instructorList.get(0);
    }
}
