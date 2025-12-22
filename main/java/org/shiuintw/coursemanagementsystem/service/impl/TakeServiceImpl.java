package org.shiuintw.coursemanagementsystem.service.impl;

import org.shiuintw.coursemanagementsystem.dao.TakeDao;
import org.shiuintw.coursemanagementsystem.model.Take;
import org.shiuintw.coursemanagementsystem.service.TakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TakeServiceImpl implements TakeService {
    private final TakeDao takeDao;

    @Autowired
    public TakeServiceImpl(TakeDao takeDao) {
        this.takeDao = takeDao;
    }

    @Override
    public Take getTakeById(String userId, String courseId) {
        return takeDao.getTakeById(userId, courseId);
    }

    @Override
    public boolean createTake(Take take) {
        Take cTake = takeDao.getTakeById(take.getUserId(), take.getCourseId());
        if (cTake != null) return false;
        takeDao.createTake(take);
        return true;
    }

    @Override
    public void updateTake(Take take) {
        takeDao.updateTake(take);
    }

    @Override
    public void deleteTakeById(String userId, String courseId) {
        takeDao.deleteTakeById(userId, courseId);
    }

    @Override
    public List<Take> getTakesByUserId(String userId) {
        return takeDao.getTakesByUserId(userId);
    }

    @Override
    public void deleteTakesByUserId(String userId) {
        takeDao.deleteTakesByUserId(userId);
    }
}
