package com.sg.dao;

import com.sg.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
@Profile("database")
public class GameDatabaseDAO implements GameDAO{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Game add(String input) {
        String sqlINSERT = "INSERT INTO Game(answer) VALUES(?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement preparedStatement = conn.prepareStatement(sqlINSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, input);
            return preparedStatement;
        }, keyHolder);
        Game game = new Game();
        game.setGameID(keyHolder.getKey().intValue());
        game.setAnswer(input);
        game.setStatus(false);

        return game;
    }

    @Override
    public List<Game> getAll() {
        String sqlSELECT = "SELECT * FROM Game;";
        return jdbcTemplate.query(sqlSELECT, new GameMapper());
    }

    @Override
    public Game findByID(int gameID) {
        String sqlSELECT = "SELECT * FROM Game WHERE gameID = ?;";
        try {
            return jdbcTemplate.queryForObject(sqlSELECT, new GameMapper(), gameID);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public boolean update(int gameID) {
        String sqlUPDATE = "UPDATE Game SET gameStatus = ? WHERE gameID = ?;";
        try {
            return jdbcTemplate.update(sqlUPDATE, true, gameID) > 0;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean remove(int gameID) {
        String sqlDELETE = "DELETE FROM Game WHERE gameID = ?;";
        try {
            return jdbcTemplate.update(sqlDELETE, gameID) > 0;
        } catch (DataAccessException e) {
            return false;
        }
    }

    private static final class GameMapper implements RowMapper<Game> {
        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game game = new Game();
            game.setGameID(rs.getInt("gameID"));
            game.setAnswer(rs.getString("answer"));
            game.setStatus(rs.getBoolean("gameStatus"));

            return game;
        }
    }
}
