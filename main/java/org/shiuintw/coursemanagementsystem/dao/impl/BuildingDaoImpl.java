package org.shiuintw.coursemanagementsystem.dao.impl;

import org.shiuintw.coursemanagementsystem.dao.BuildingDao;
import org.shiuintw.coursemanagementsystem.mapper.BuildingRowMapper;
import org.shiuintw.coursemanagementsystem.model.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Building getBuildingById(String id) {
        String sql = "SELECT * FROM building WHERE id = :id";
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        List<Building> buildingList = namedParameterJdbcTemplate.query(sql, map, new BuildingRowMapper());
        return buildingList.isEmpty() ? null : buildingList.get(0);
    }
}
