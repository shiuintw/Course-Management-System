package org.shiuintw.coursemanagementsystem.dao;

import org.shiuintw.coursemanagementsystem.dto.UserRequest;
import org.shiuintw.coursemanagementsystem.model.User;

public interface UserDao {
    User getUserById(String id);
    String createUser(UserRequest userRequest);
    void updateUser(UserRequest userRequest);
    void deleteUserById(String id);
}
