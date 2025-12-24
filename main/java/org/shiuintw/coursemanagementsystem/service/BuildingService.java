package org.shiuintw.coursemanagementsystem.service;

import org.shiuintw.coursemanagementsystem.model.Building;

import java.util.List;

public interface BuildingService {
    List<Building> getAllBuildings();
    Building getBuildingById(String id);
}
