package com.sg.dao;

import com.sg.model.Round;

import java.util.List;

public interface RoundDAO {
    Round addRound(Round round);
    List<Round> getAll();
    List<Round> search(int gameID);
    boolean update(Round round);
    boolean remove(int id);
}
