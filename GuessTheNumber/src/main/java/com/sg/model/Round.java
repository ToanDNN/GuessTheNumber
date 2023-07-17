package com.sg.model;

import java.sql.Timestamp;
import java.util.Objects;

public class Round {
    private int roundID;
    int gameID;
    Timestamp timeOfGuess;
    String guess;
    String result;

    public Round() {
    }
    public Round(int gameID, String guess, Timestamp timeOfGuess) {
        this.gameID = gameID;
        this.guess = guess;
        this.timeOfGuess = timeOfGuess;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
    public int getGameID() {
        return gameID;
    }

    public void setRoundID(int roundID) {
        this.roundID = roundID;
    }
    public int getRoundID() {
        return roundID;
    }

    public void setTimeOfGuess(Timestamp timeOfGuess) {
        this.timeOfGuess = timeOfGuess;
    }
    public Timestamp getTimeOfGuess() {
        return timeOfGuess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }
    public String getGuess() {
        return guess;
    }

    public void setResult(String result) {
        this.result = result;
    }
    public String getResult() {
        return result;
    }
}
