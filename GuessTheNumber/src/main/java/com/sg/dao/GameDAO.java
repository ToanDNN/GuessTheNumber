package com.sg.dao;

import com.sg.model.Game;

import java.util.List;

public interface GameDAO {
    Game add(String input);
    List<Game> getAll();
    Game findByID(int gameID);
    boolean update(int gameID);
    boolean remove(int gameID);
}
