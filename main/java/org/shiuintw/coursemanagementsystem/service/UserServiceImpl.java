package org.shiuintw.coursemanagementsystem.service;

import org.shiuintw.coursemanagementsystem.dao.UserDao;
import org.shiuintw.coursemanagementsystem.dto.UserRequest;
import org.shiuintw.coursemanagementsystem.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserDao userDao;

    private void checkId(String id) {
        if (!id.matches("\\d{9}")) {
            log.warn("wrong id format: {}", id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "wrong id format");
        }
    }

    @Autowired
    public UserServiceImpl(final UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getUserById(String id) {
        return userDao.getUserById(id);
    }

    @Override
    public String register(UserRequest userRequest) { // register = check + create
        // check id format
        checkId(userRequest.getId());

        // check the existence of the email
        User user = userDao.getUserById(userRequest.getId());
        if (user != null) {
            log.warn("this id {} has already been registered", userRequest.getId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "account has already been registered");
        }

        // MD5 encrypt(hash)
        String hashedPassword = DigestUtils.md5DigestAsHex(userRequest.getPassword().getBytes());
        userRequest.setPassword(hashedPassword);

        // create account
        return userDao.createUser(userRequest);
    }

    @Override
    public User login(UserRequest userRequest) {
        // check id format
        checkId(userRequest.getId());

        // check the existence of user
        User user = userDao.getUserById(userRequest.getId());
        if (user == null) {
            log.warn("this id {} has not been registered", userRequest.getId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "account has not been registered");
        }

        // MD5 encrypt(hash)
        String hashedPassword = DigestUtils.md5DigestAsHex(userRequest.getPassword().getBytes());

        if (user.getPassword().equals(hashedPassword)) {
            return user;
        } else {
            log.warn("id {} does not match password", userRequest.getId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "wrong password");
        }
    }
}
