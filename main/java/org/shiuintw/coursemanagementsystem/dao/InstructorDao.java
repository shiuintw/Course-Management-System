package org.shiuintw.coursemanagementsystem.dao;

import org.shiuintw.coursemanagementsystem.model.Instructor;

import java.util.List;

public interface InstructorDao {
    List<Instructor> getAllInstructors();
    Instructor getInstructorById(String id);
}
