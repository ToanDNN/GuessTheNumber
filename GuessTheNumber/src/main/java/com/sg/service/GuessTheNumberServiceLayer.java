package com.sg.service;

import com.sg.model.Game;
import com.sg.model.Round;

import java.util.List;

public interface GuessTheNumberServiceLayer {

    int start();
    List<Game> getAllGames();
    Game getGameByID(int gameID);
    List<Round> getRoundList(int gameID);
    //Game getGames(Game game);
    //String createNum();
    //Round makeGuess(Round round);
    Round checkGuess(int gameID, String guess);
    //String getResult(String guess, String answer);

}
