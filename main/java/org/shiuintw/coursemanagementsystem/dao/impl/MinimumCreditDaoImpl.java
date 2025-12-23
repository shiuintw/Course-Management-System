package org.shiuintw.coursemanagementsystem.dao.impl;

import org.shiuintw.coursemanagementsystem.dao.MinimumCreditDao;
import org.shiuintw.coursemanagementsystem.mapper.MinimumCreditRowMapper;
import org.shiuintw.coursemanagementsystem.model.MinimumCredit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MinimumCreditDaoImpl implements MinimumCreditDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public MinimumCreditDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public MinimumCredit getMinimumCreditById(String departmentId) {
        String sql = "SELECT * FROM minimum_credit WHERE department_id = :departmentId";
        Map<String, Object> map = new HashMap<>();
        map.put("departmentId", departmentId);
        List<MinimumCredit> minimumCreditList =
                namedParameterJdbcTemplate.query(sql, map, new MinimumCreditRowMapper());

        return minimumCreditList.isEmpty() ? null : minimumCreditList.get(0);
    }
}
