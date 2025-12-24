package org.shiuintw.coursemanagementsystem.dao;

import org.shiuintw.coursemanagementsystem.model.Building;

import java.util.List;

public interface BuildingDao {
    List<Building> getAllBuildings();
    Building getBuildingById(String id);
}
