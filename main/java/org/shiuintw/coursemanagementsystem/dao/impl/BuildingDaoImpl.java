package org.shiuintw.coursemanagementsystem.dao.impl;

import org.shiuintw.coursemanagementsystem.dao.BuildingDao;
import org.shiuintw.coursemanagementsystem.mapper.BuildingRowMapper;
import org.shiuintw.coursemanagementsystem.model.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuildingDaoImpl implements BuildingDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public BuildingDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Building> getAllBuildings() {
        String sql = "SELECT * FROM building";
        return namedParameterJdbcTemplate.query(sql, new BuildingRowMapper());
    }
}
