package com.sg.dao;

import com.sg.model.Game;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameDatabaseDAOTest {
    @Autowired
    GameDAO gameDAO;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Before
    public void clean() {
        String sqlDELETE_Game = "DELETE FROM Game";
        String sqlDELETE_Round = "DELETE FROM Round";

        jdbcTemplate.update(sqlDELETE_Game);
        jdbcTemplate.update(sqlDELETE_Round);
    }

    @Test
    public void testListGames() {
        String test1 = "9999";
        String test2 = "2222";
        String test3 = "7777";

        gameDAO.add(test1);
        List<Game> games = gameDAO.getAll();
        assertEquals(1, games.size());
        assertEquals(false, games.get(0).isStatus());
        gameDAO.add(test2);
        games = gameDAO.getAll();
        assertEquals(2, games.size());
        gameDAO.add(test3);
        games = gameDAO.getAll();
        assertEquals(3, games.size());
        games.get(2).setStatus(true);
        assertEquals(true, games.get(2).isStatus());
    }

    @Test
    public void testAddGame() {
        String test = "9999";
        Game game = gameDAO.add(test);
        assertEquals(game.getAnswer(), test);
    }

    @Test
    public void testFindByID() {
        String test = "9999";
        Game game1 = gameDAO.add(test);
        Game game2 = gameDAO.findByID(game1.getGameID());

        assertEquals(game1.getAnswer(), game2.getAnswer());
        assertEquals(game1.isStatus(), game2.isStatus());
    }

    @Test
    public void testIfStatusIsTrue() {
        String test = "9999";
        Game testGame = gameDAO.add(test);
        gameDAO.update(testGame.getGameID());
        Game testGame2 = gameDAO.findByID(testGame.getGameID());
        assertFalse(testGame.isStatus());
        assertTrue(testGame2.isStatus());
    }
}
