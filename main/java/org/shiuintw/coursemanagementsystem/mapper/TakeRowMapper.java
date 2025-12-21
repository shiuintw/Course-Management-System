package org.shiuintw.coursemanagementsystem.mapper;

import org.shiuintw.coursemanagementsystem.model.Take;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TakeRowMapper implements RowMapper<Take> {
    @Override
    public Take mapRow(ResultSet rs, int rowNum) throws SQLException {
        Take take = new Take();
        take.setUserId(rs.getString("user_id"));
        take.setCourseId(rs.getString("course_id"));
        take.setTime(rs.getString("time"));
        take.setScore(rs.getString("score"));
        return take;
    }
}
