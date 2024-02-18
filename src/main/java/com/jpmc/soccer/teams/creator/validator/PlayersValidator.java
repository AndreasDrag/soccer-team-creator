package com.jpmc.soccer.teams.creator.validator;

import com.jpmc.soccer.teams.creator.exception.SoccerTeamCreatorException;
import com.jpmc.soccer.teams.creator.model.Player;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * The type Players validator.
 */
public class PlayersValidator {

    /**
     * Validate the players input.
     * Players number should be even, otherwise it returns error
     *
     * @param players the players
     */
    public void validate(List<Player> players) {
        if (CollectionUtils.isEmpty(players) || players.size() % 2 != 0) {
            throw new SoccerTeamCreatorException("Please provide an even number of players.");
        }
    }
}
