package com.sg.dao;


import com.sg.model.Round;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoundDAOTest {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    RoundDAO roundDAO;
    @Autowired
    GameDAO gameDAO;

    @Before
    public void clean() {
        String sqlDELETE = "DELETE FROM Round";
        jdbcTemplate.update(sqlDELETE);
    }

    @Test
    public void testAddRound() {
        int testGameID = gameDAO.add("9999").getGameID();
        Round testRound = new Round();
        testRound.setGuess("9999");
        testRound.setTimeOfGuess(Timestamp.valueOf(LocalDateTime.now().withNano(0)));
        testRound.setGameID(testGameID);
        testRound.setResult("p:0  e:4");
        Round addedRound = roundDAO.addRound(testRound);
        List<Round> rounds = new ArrayList<>();
        rounds.add(addedRound);

        assertEquals(1, rounds.size());
    }

    @Test
    public void testGetList() {
        int testGameID1 = gameDAO.add("9999").getGameID();

        Round testRound1 = new Round();

        testRound1.setGuess("9999");
        testRound1.setTimeOfGuess(Timestamp.valueOf(LocalDateTime.now().withNano(0)));
        testRound1.setGameID(testGameID1);
        testRound1.setResult("p:0  e:4");

        Round addedRound1 = roundDAO.addRound(testRound1);

        List<Round> rounds = roundDAO.search(testGameID1);
        assertEquals(1, rounds.size());
        Round roundFromList = rounds.get(0);
        assertEquals(addedRound1.getRoundID(), roundFromList.getRoundID());
        assertEquals(testRound1.getGuess(), roundFromList.getGuess());
        assertEquals(testRound1.getTimeOfGuess(), roundFromList.getTimeOfGuess());
        assertEquals(testRound1.getResult(), roundFromList.getResult());
        assertEquals(testRound1.getGameID(), roundFromList.getGameID());

        int testGameID2 = gameDAO.add("4444").getGameID();
        List<Round> roundList = roundDAO.search(testGameID2);
        assertEquals(0, roundList.size());
    }
}
