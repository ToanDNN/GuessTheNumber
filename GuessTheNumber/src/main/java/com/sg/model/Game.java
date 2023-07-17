package com.sg.model;

import java.util.Objects;

public class Game {
    private int gameID;
    private String answer;
    private boolean status;


    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
    public int getGameID() {
        return gameID;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public String getAnswer() {
        return answer;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    public boolean isStatus() {
        return status;
    }
}
