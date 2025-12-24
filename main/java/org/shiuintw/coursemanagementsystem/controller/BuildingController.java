package org.shiuintw.coursemanagementsystem.controller;

import jakarta.servlet.http.HttpSession;
import org.shiuintw.coursemanagementsystem.model.Building;
import org.shiuintw.coursemanagementsystem.model.User;
import org.shiuintw.coursemanagementsystem.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BuildingController {
    private final BuildingService buildingService;

    @Autowired
    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @GetMapping("building/{buildingId}")
    public String getBuilding(@PathVariable String buildingId, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        if (buildingId == null || buildingId.isEmpty() || buildingId.equals("Unknown")) {
            return "redirect:/course/search";
        }
        Building building = buildingService.getBuildingById(buildingId);
        model.addAttribute("building", building);
        return "building";
    }
}
