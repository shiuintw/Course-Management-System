package org.shiuintw.coursemanagementsystem.dao.impl;

import org.shiuintw.coursemanagementsystem.dao.DepartmentDao;
import org.shiuintw.coursemanagementsystem.mapper.DepartmentRowMapper;
import org.shiuintw.coursemanagementsystem.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DepartmentDaoImpl implements DepartmentDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public DepartmentDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Department> getAllDepartments() {
        String sql = "SELECT * FROM department";
        Map<String, Object> map = new HashMap<>();
        return namedParameterJdbcTemplate.query(sql, map, new DepartmentRowMapper());
    }
}
