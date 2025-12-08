package org.shiuintw.coursemanagementsystem.service;

import org.shiuintw.coursemanagementsystem.dto.UserRequest;
import org.shiuintw.coursemanagementsystem.model.User;

public interface UserService {
    User getUserById(String userId);
    String register(UserRequest userRegisterRequest);
    User login(UserRequest userLoginRequest);
}
