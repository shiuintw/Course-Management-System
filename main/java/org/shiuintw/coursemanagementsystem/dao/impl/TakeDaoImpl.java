package org.shiuintw.coursemanagementsystem.dao.impl;

import org.shiuintw.coursemanagementsystem.dao.TakeDao;
import org.shiuintw.coursemanagementsystem.mapper.TakeRowMapper;
import org.shiuintw.coursemanagementsystem.model.Take;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TakeDaoImpl implements TakeDao {
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public TakeDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Take getTakeById(String userId, String courseId) {
        String sql = "SELECT * FROM take WHERE user_id = :userId AND course_id = :courseId";
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("courseId", courseId);
        List<Take> takeList = namedParameterJdbcTemplate.query(sql, map, new TakeRowMapper());
        return takeList.isEmpty() ? null : takeList.get(0);
    }

    @Override
    public void createTake(Take take) {
        String sql = "INSERT INTO take(user_id, course_id) VALUES (:userId, :courseId)";
        Map<String, Object> map = new HashMap<>();
        map.put("userId", take.getUserId());
        map.put("courseId", take.getCourseId());
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map));
    }

    @Override
    public void updateTake(Take take) {
        String sql = "UPDATE take SET time = :time, score = :score " +
                "WHERE user_id = :userId AND course_id = :courseId";
        Map<String, Object> map = new HashMap<>();
        map.put("courseId", take.getCourseId());
        map.put("userId", take.getUserId());
        map.put("time", take.getTime());
        map.put("score", take.getScore());
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map));
    }

    @Override
    public void deleteTakeById(String userId, String courseId) {
        String sql = "DELETE FROM take WHERE user_id = :userId AND course_id = :courseId";
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("courseId", courseId);
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map));
    }

    @Override
    public List<Take> getTakesByUserId(String userId) {
        String sql = "SELECT * FROM take WHERE user_id = :userId";
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        return namedParameterJdbcTemplate.query(sql, map, new TakeRowMapper());
    }

    @Override
    public void deleteTakesByUserId(String userId) {
        String sql = "DELETE FROM take WHERE user_id = :userId";
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map));
    }
}
