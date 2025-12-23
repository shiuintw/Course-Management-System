package org.shiuintw.coursemanagementsystem.service.impl;

import org.shiuintw.coursemanagementsystem.dao.MinimumCreditDao;
import org.shiuintw.coursemanagementsystem.model.MinimumCredit;
import org.shiuintw.coursemanagementsystem.service.MinimumCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MinimumCreditServiceImpl implements MinimumCreditService {
    private final MinimumCreditDao minimumCreditDao;

    @Autowired
    public MinimumCreditServiceImpl(MinimumCreditDao minimumCreditDao) {
        this.minimumCreditDao = minimumCreditDao;
    }

    @Override
    public MinimumCredit getMinimumCreditById(String departmentId) {
        return minimumCreditDao.getMinimumCreditById(departmentId);
    }
}
