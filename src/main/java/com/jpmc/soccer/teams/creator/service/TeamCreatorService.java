package com.jpmc.soccer.teams.creator.service;

import com.jpmc.soccer.teams.creator.model.Player;

import java.util.List;

public interface TeamCreatorService {
    void distributePlayers(List<Player> players, Integer attempts);
}
