package org.shiuintw.coursemanagementsystem.service;

import org.shiuintw.coursemanagementsystem.model.Instructor;

import java.util.List;

public interface InstructorService {
    List<Instructor> getAllInstructors();
    Instructor getInstructorById(String id);
}
