package com.jpmc.soccer.teams.creator.service;

import com.jpmc.soccer.teams.creator.model.Player;

import java.util.List;

/**
 * The interface Team creator service.
 */
public interface TeamCreatorService {
    /**
     * Create balanced teams.
     *
     * @param players  the players
     * @param attempts the attempts
     */
    void createBalancedTeams(List<Player> players, Integer attempts);
}
