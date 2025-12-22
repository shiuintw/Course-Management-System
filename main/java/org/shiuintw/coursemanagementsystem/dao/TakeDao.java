package org.shiuintw.coursemanagementsystem.dao;

import org.shiuintw.coursemanagementsystem.model.Take;

import java.util.List;

public interface TakeDao {
    Take getTakeById(String userId, String courseId);
    void createTake(Take take);
    void updateTake(Take take);
    void deleteTakeById(String userId, String courseId);
    List<Take> getTakesByUserId(String userId);
    void deleteTakesByUserId(String userId);
}
