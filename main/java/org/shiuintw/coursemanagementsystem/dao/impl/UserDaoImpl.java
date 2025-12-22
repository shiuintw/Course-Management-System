package org.shiuintw.coursemanagementsystem.dao.impl;

import org.shiuintw.coursemanagementsystem.dao.UserDao;
import org.shiuintw.coursemanagementsystem.dto.UserRequest;
import org.shiuintw.coursemanagementsystem.mapper.UserRowMapper;
import org.shiuintw.coursemanagementsystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDaoImpl implements UserDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public UserDaoImpl(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    // --- util
    private Map<String, Object> parameterMap(UserRequest userRequest) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", userRequest.getId());
        map.put("name", userRequest.getName());
        map.put("departmentId", userRequest.getDepartmentId());
        map.put("password", userRequest.getPassword());
        return map;
    }
    // --- end of util

    @Override
    public User getUserById(String id) {
        String sql = "SELECT * FROM users WHERE id = :id";
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);

        List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

        return !userList.isEmpty() ? userList.get(0) : null;
    }

    @Override
    public String createUser(UserRequest userRequest) {
        String sql = "INSERT INTO users(id, name, department_id,  password) " +
                "VALUES(:id, :name, :departmentId, :password)";

        Map<String, Object> map = parameterMap(userRequest);

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map));

        return userRequest.getId();
    }

    @Override
    public void updateUser(UserRequest userRequest) {
        String sql = "UPDATE users SET name = :name," +
                "department_id = :departmentId, password = :password" +
                " WHERE id = :id";

        Map<String, Object> map = parameterMap(userRequest);

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map));
    }

    @Override
    public void deleteUserById(String id) {
        String sql = "DELETE FROM users WHERE id = :id";

        Map<String, Object> map = new HashMap<>();
        map.put("id", id);

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map));
    }
}
