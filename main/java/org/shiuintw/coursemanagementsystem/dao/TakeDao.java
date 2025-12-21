package org.shiuintw.coursemanagementsystem.dao;

import org.shiuintw.coursemanagementsystem.model.Take;

public interface TakeDao {
    Take getTakeById(String userId, String courseId);
    void createTake(Take take);
    void updateTake(Take take);
    void deleteTakeById(String userId, String courseId);
}
