package org.shiuintw.coursemanagementsystem.service;

import org.shiuintw.coursemanagementsystem.model.Take;

public interface TakeService {
    Take getTakeById(String userId, String courseId);
    boolean createTake(Take take);
    void updateTake(Take take);
    void deleteTakeById(String userId, String courseId);
}
