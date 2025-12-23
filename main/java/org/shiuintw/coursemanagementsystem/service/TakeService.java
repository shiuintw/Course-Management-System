package org.shiuintw.coursemanagementsystem.service;

import org.shiuintw.coursemanagementsystem.model.MinimumCredit;
import org.shiuintw.coursemanagementsystem.model.Take;

import java.util.List;

public interface TakeService {
    Take getTakeById(String userId, String courseId);
    boolean createTake(Take take);
    void updateTake(Take take);
    void deleteTakeById(String userId, String courseId);
    List<Take> getTakesByUserId(String userId);
    void deleteTakesByUserId(String userId);
    MinimumCredit getCredit(String userId, MinimumCredit minimumCredit);
}
