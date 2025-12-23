package org.shiuintw.coursemanagementsystem.service.impl;

import org.shiuintw.coursemanagementsystem.dao.InstructorDao;
import org.shiuintw.coursemanagementsystem.model.Instructor;
import org.shiuintw.coursemanagementsystem.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InstructorServiceImpl implements InstructorService {
    private final InstructorDao instructorDao;

    @Autowired
    public InstructorServiceImpl(InstructorDao instructorDao) {
        this.instructorDao = instructorDao;
    }

    @Override
    public List<Instructor> getAllInstructors() {
        return instructorDao.getAllInstructors();
    }
}
