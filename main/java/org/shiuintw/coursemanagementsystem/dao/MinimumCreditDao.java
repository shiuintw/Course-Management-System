package org.shiuintw.coursemanagementsystem.dao;

import org.shiuintw.coursemanagementsystem.model.MinimumCredit;

public interface MinimumCreditDao {
    MinimumCredit getMinimumCreditById(String departmentId);
}
