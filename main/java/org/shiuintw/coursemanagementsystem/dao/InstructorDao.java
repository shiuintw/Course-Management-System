package org.shiuintw.coursemanagementsystem.dao;

import org.shiuintw.coursemanagementsystem.model.Instructor;

public interface InstructorDao {
    Instructor getInstructorById(String id);
    String createInstructor(Instructor instructor);
    void updateInstructor(Instructor instructor);
    void deleteInstructorById(String id);
}
