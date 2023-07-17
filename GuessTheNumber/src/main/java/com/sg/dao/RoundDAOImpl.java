package com.sg.dao;

import com.sg.model.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Comparator;
import java.util.List;

@Repository
@Profile("database")
public class RoundDAOImpl implements RoundDAO{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RoundDAOImpl (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Round addRound(Round round) {
        String sqlINSERT = "INSERT INTO Round(gameID, timeOfGuess, guess, guessResult) VALUES(?,?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement preparedStatement = conn.prepareStatement(sqlINSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, round.getGameID());
            preparedStatement.setTimestamp(2, round.getTimeOfGuess());
            preparedStatement.setString(3, round.getGuess());
            preparedStatement.setString(4, round.getResult());
            return preparedStatement;
        }, keyHolder);
        round.setRoundID(keyHolder.getKey().intValue());
        return round;
    }

    @Override
    public List<Round> getAll() {
        String sqlSELECT = "SELECT * FROM Round;";
        return jdbcTemplate.query(sqlSELECT, new RoundMapper());
    }

    @Override
    public List<Round> search(int gameID) {
        String sqlSELECT = "SELECT * FROM Round Where gameID = ?;";
        List<Round> rounds = jdbcTemplate.query(sqlSELECT, new RoundMapper(), gameID);
        rounds.sort(Comparator.comparing(Round::getTimeOfGuess));
        return rounds;

    }

    @Override
    public boolean update(Round round) {
        return false;
    }

    @Override
    public boolean remove(int roundID) {
        String sqlDELETE = "DELETE FROM Round WHERE roundID = ?;";
        try {
            return jdbcTemplate.update(sqlDELETE, roundID) > 0;
        } catch (DataAccessException e) {
            return false;
        }
    }

    private static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round round = new Round();
            round.setRoundID(rs.getInt("roundID"));
            round.setGameID(rs.getInt("gameID"));
            round.setTimeOfGuess(rs.getTimestamp("timeOfGuess"));
            round.setGuess(rs.getString("guess"));
            round.setResult(rs.getString("guessResult"));

            return round;
        }
    }
}
