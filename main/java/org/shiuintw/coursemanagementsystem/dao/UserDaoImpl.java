package org.shiuintw.coursemanagementsystem.dao;

import org.shiuintw.coursemanagementsystem.dto.UserRequest;
import org.shiuintw.coursemanagementsystem.model.User;
import org.shiuintw.coursemanagementsystem.rowmapper.UserRowMapper;
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

        Map<String, Object> map = new HashMap<>();
        map.put("id", userRequest.getId());
        map.put("name", userRequest.getName());
        map.put("departmentId", userRequest.getDepartmentId());
        map.put("password", userRequest.getPassword());

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map));

        return userRequest.getId();
    }
}
