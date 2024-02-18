package com.jpmc.soccer.teams.creator.service.impl;

import com.jpmc.soccer.teams.creator.enums.SoccerPositionCategoryEnum;
import com.jpmc.soccer.teams.creator.enums.SoccerPositionEnum;
import com.jpmc.soccer.teams.creator.model.Player;
import com.jpmc.soccer.teams.creator.service.TeamCreatorService;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type Team creator service.
 */
public class TeamCreatorServiceImpl implements TeamCreatorService {

    private static final String TEAM_PLAYERS_DELIMITER = ", ";

    @Override
    public void createBalancedTeams(List<Player> players, Integer attempts) {
        List<Player> team1 = new ArrayList<>();
        List<Player> team2 = new ArrayList<>();

        List<List<Player>> playerListsPerCategory = getPlayerListsPerPositionCategory(players);

        int attempt = 0;

        while (shouldCalculateNewTeams(attempt, attempts, team1, team2)) {
            List<Player> inProgressTeam1 = new ArrayList<>();
            List<Player> inProgressTeam2 = new ArrayList<>();

            calculateNewTeams(playerListsPerCategory, inProgressTeam1, inProgressTeam2);

            if (betterTeamsFound(team1, team2, inProgressTeam1, inProgressTeam2)) {
                team1 = inProgressTeam1;
                team2 = inProgressTeam2;
            }

            attempt++;
        }

        System.out.printf("Balanced Soccer Teams calculated on attempt: %d with Team1 level: %d and Team2 level: %d%n",
                attempt, calculateTeamLevel(team1), calculateTeamLevel(team2));
        System.out.println("Team 1: " + printTeam(team1));
        System.out.println("Team 2: " + printTeam(team2));
    }

    /**
     * Returns all players distributed in lists per position category {@link SoccerPositionCategoryEnum}
     *
     * @param players the provided players
     * @return the list of players per position category
     */
    private static List<List<Player>> getPlayerListsPerPositionCategory(List<Player> players) {
        return Arrays.stream(SoccerPositionCategoryEnum.values())
                .map(categoryEnum -> getPlayersByPositionCategory(players, categoryEnum))
                .collect(Collectors.toList());
    }

    /**
     * Calculates new teams: inProgressTeam1, inProgressTeam2
     *
     * @param playerListsPerCategory players per category
     * @param inProgressTeam1        the in progress team1
     * @param inProgressTeam2        the in progress team2
     */
    private void calculateNewTeams(List<List<Player>> playerListsPerCategory,
                                   List<Player> inProgressTeam1, List<Player> inProgressTeam2) {

        List<Player> leftovers = new ArrayList<>();
        playerListsPerCategory.add(leftovers);

        playerListsPerCategory.forEach(playersList -> {
            Collections.shuffle(playersList);
            if (playersList.size() % 2 != 0) {
                Player leftoverPlayer = playersList.get(playersList.size() - 1);
                leftovers.add(leftoverPlayer);
                playersList.remove(playersList.size() - 1);
            }
            Iterator<Player> iterator = playersList.iterator();
            while (iterator.hasNext()) {
                inProgressTeam1.add(iterator.next());
                inProgressTeam2.add(iterator.next());
            }
        });
    }

    /**
     * Given a team returns a string with the players info comma separated.
     *
     * @param team the team
     * @return the team in string
     */
    private static String printTeam(List<Player> team) {
        return team.stream().map(Player::toString).collect(Collectors.joining(TEAM_PLAYERS_DELIMITER));
    }

    /**
     * Returns true if max attempts are not met or if the teams are not balanced.
     *
     * @param attempt     the calculation attempt
     * @param maxAttempts the max allowed calculations
     * @param team1       the team1
     * @param team2       the team2
     * @return the boolean
     */
    private static boolean shouldCalculateNewTeams(int attempt, int maxAttempts, List<Player> team1, List<Player> team2) {
        return attempt < maxAttempts
                && (CollectionUtils.isEmpty(team1) && CollectionUtils.isEmpty(team2) || calculateTeamsLevelDifference(team1, team2) != 0);
    }

    /**
     * Calculates the difference between team's 1 and team's 2 levels.
     *
     * @param team1 the team1
     * @param team2 tha team2
     * @return the level's difference
     */
    private static int calculateTeamsLevelDifference(List<Player> team1, List<Player> team2) {
        return Math.abs(calculateTeamLevel(team1) - calculateTeamLevel(team2));
    }

    /**
     * Returns true if the in progress teams are better balanced.
     *
     * @param team1           the team1
     * @param team2           the team2
     * @param inProgressTeam1 the in progress team1
     * @param inProgressTeam2 the in progress team2
     * @return the boolean
     */
    private static boolean betterTeamsFound(List<Player> team1, List<Player> team2, List<Player> inProgressTeam1, List<Player> inProgressTeam2) {
        return CollectionUtils.isEmpty(team1) && CollectionUtils.isEmpty(team2)
                || (calculateTeamsLevelDifference(inProgressTeam1, inProgressTeam2) < calculateTeamsLevelDifference(team1, team2));
    }

    /**
     * Calculate team's level
     *
     * @param team the team
     * @return the team level
     */
    private static int calculateTeamLevel(List<Player> team) {
        return team.stream().map(Player::getSkillLevel).mapToInt(value -> value).sum();
    }

    /**
     * Returns a list of players filtered by preferred position {@link SoccerPositionEnum}
     * is at given position category {@link SoccerPositionCategoryEnum}.
     *
     * @param players              the players list
     * @param positionCategoryEnum the position category
     * @return the list of players
     */
    private static List<Player> getPlayersByPositionCategory(
            List<Player> players, SoccerPositionCategoryEnum positionCategoryEnum) {

        return Optional.ofNullable(players).stream().flatMap(Collection::stream)
                .filter(player ->
                        positionCategoryEnum ==
                                Optional.ofNullable(SoccerPositionEnum.resolveByPositionName(player.getPreferredPosition()))
                                        .map(SoccerPositionEnum::getPositionCategory)
                                        .orElse(SoccerPositionCategoryEnum.UNKNOWN))
                .collect(Collectors.toList());
    }
}
