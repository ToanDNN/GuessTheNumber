package com.sg.service;

import com.sg.dao.GameDatabaseDAO;
import com.sg.dao.RoundDAOImpl;
import com.sg.model.Game;
import com.sg.model.Round;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Repository
@Profile("database")
public class GuessTheNumberServiceLayerImpl implements GuessTheNumberServiceLayer{

    private GameDatabaseDAO gameDAO;
    private RoundDAOImpl roundDAO;

    public GuessTheNumberServiceLayerImpl (GameDatabaseDAO gameDAO, RoundDAOImpl roundDAO) {
        this.roundDAO = roundDAO;
        this.gameDAO = gameDAO;
    }

    @Override
    public int start() {
        return gameDAO.add(generateNum()).getGameID();
    }

    @Override
    public List<Game> getAllGames() {
        List<Game> games = gameDAO.getAll();
        games.stream().filter(game -> !game.isStatus()).forEach(game -> game.setAnswer("****"));
        return games;
    }

    @Override
    public Game getGameByID(int gameID) {
        Game game = gameDAO.findByID(gameID);
        if (game!= null && !game.isStatus()) {
            game.setAnswer("****");
        }
        return game;
    }

    @Override
    public List<Round> getRoundList(int gameID) {
        return roundDAO.search(gameID);
    }

    @Override
    public Round checkGuess(int gameID, String guess) {
        Round round = new Round(gameID, guess, Timestamp.valueOf(LocalDateTime.now()));
        Game game = gameDAO.findByID(gameID);
        if(game == null || game.isStatus()) {
            return null;
        }
        String answerChoice = game.getAnswer();
        int partial = 0;
        int exact = 0;

        for(int i = 0; i < guess.length(); i++) {
            if(guess.charAt(i) == answerChoice.charAt(i))
                exact++;
            if(answerChoice.contains("" + guess.charAt(i)))
                partial++;
        }
        round.setResult(String.format("p:%s  e:%s", partial, exact));

        if(exact == 4) {
            gameDAO.update(gameID);
        }
        return roundDAO.addRound(round);
    }

    private String generateNum() {
        Random rand = new Random();
        int num;
        Set<Integer> answers = new HashSet<>();
        while(answers.size()<4) {
            num = rand.nextInt(10);
            answers.add(num);
        }
        int choice = 0;
        for(int x : answers) {
            choice = choice * 10 + x;
        }
        if(choice < 1000) {
            num = 0;
            while(answers.contains(num))
                num = rand.nextInt(9) + 1;
            choice = num * 1000 + choice;
        }
        return Integer.toString(choice);
    }
}
