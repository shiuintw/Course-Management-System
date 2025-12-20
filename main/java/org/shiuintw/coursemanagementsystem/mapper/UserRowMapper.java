package org.shiuintw.coursemanagementsystem.mapper;

import org.shiuintw.coursemanagementsystem.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setId(resultSet.getString("id"));
        user.setName(resultSet.getString("name"));
        user.setDepartmentId(resultSet.getString("department_id"));
        user.setPassword(resultSet.getString("password"));

        return user;
    }
}

