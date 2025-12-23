package org.shiuintw.coursemanagementsystem.service.impl;

import org.shiuintw.coursemanagementsystem.dao.BuildingDao;
import org.shiuintw.coursemanagementsystem.model.Building;
import org.shiuintw.coursemanagementsystem.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuildingServiceImpl implements BuildingService {
    private final BuildingDao buildingDao;
    @Autowired
    public BuildingServiceImpl(BuildingDao buildingDao) {
        this.buildingDao = buildingDao;
    }

    @Override
    public List<Building> getAllBuildings() {
        return buildingDao.getAllBuildings();
    }
}
