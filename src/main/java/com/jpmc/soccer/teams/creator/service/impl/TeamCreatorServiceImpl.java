package com.jpmc.soccer.teams.creator.service.impl;

import com.jpmc.soccer.teams.creator.enums.SoccerPositionCategoryEnum;
import com.jpmc.soccer.teams.creator.enums.SoccerPositionEnum;
import com.jpmc.soccer.teams.creator.model.Player;
import com.jpmc.soccer.teams.creator.service.TeamCreatorService;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TeamCreatorServiceImpl implements TeamCreatorService {

    private static final Integer DEFAULT_TEAM_SHUFFLE_ATTEMPTS_MAX_NUMBER = 50;

    @Override
    public void distributePlayers(List<Player> players, Integer attempts) {
        int maxAttempts = attempts != null ? attempts : DEFAULT_TEAM_SHUFFLE_ATTEMPTS_MAX_NUMBER;
        List<Player> team1 = new ArrayList<>();
        List<Player> team2 = new ArrayList<>();

        List<List<Player>> playerListsPerCategory = new ArrayList<>();
        List<Player> goalkeepers = getPlayersByPositionCategory(players, SoccerPositionCategoryEnum.GOALKEEPER);
        List<Player> defenders = getPlayersByPositionCategory(players, SoccerPositionCategoryEnum.DEFENDER);
        List<Player> midfielders = getPlayersByPositionCategory(players, SoccerPositionCategoryEnum.MIDFIELDER);
        List<Player> forwards = getPlayersByPositionCategory(players, SoccerPositionCategoryEnum.FORWARD);
        List<Player> unknownPositions = getPlayersByPositionCategory(players, SoccerPositionCategoryEnum.UNKNOWN);

        playerListsPerCategory.add(goalkeepers);
        playerListsPerCategory.add(defenders);
        playerListsPerCategory.add(midfielders);
        playerListsPerCategory.add(forwards);
        playerListsPerCategory.add(unknownPositions);

        int attempt = 0;
        int team1Level = Integer.MIN_VALUE;
        int team2Level = Integer.MAX_VALUE;

        while (attempt > maxAttempts || team1Level - team2Level != 0) {
            List<Player> inProgressTeam1 = new ArrayList<>();
            List<Player> inProgressTeam2 = new ArrayList<>();
            playerListsPerCategory.forEach(playersList -> {
                if (playersList.size() % 2 != 0) {
                    Player player = playersList.get(playersList.size() - 1);
                    unknownPositions.add(player);
                    playersList.remove(playersList.size() - 1);
                }
                Collections.shuffle(playersList);
                Iterator<Player> iterator = playersList.iterator();
                while (iterator.hasNext()) {
                    inProgressTeam1.add(iterator.next());
                    inProgressTeam2.add(iterator.next());
                }
            });

            team1Level = !CollectionUtils.isEmpty(team1) ?
                    team1.stream().map(Player::getSkillLevel).mapToInt(value -> value).sum()
                    : Integer.MAX_VALUE;
            team2Level = !CollectionUtils.isEmpty(team2) ?
                    team1.stream().map(Player::getSkillLevel).mapToInt(value -> value).sum()
                    : Integer.MIN_VALUE;
            int inProgressTeam1Level = inProgressTeam1.stream().map(Player::getSkillLevel).mapToInt(value -> value).sum();
            int inProgressTeam2Level = inProgressTeam2.stream().map(Player::getSkillLevel).mapToInt(value -> value).sum();
            attempt++;
            if (Math.abs(inProgressTeam1Level - inProgressTeam2Level) < Math.abs(team1Level - team2Level)) {
                team1 = inProgressTeam1;
                team2 = inProgressTeam2;
            }
        }

        System.out.println("Soccer Team Creator output on attempt: " + attempt);
        System.out.println(team1);
        System.out.println(team1Level);
        System.out.println(team2);
        System.out.println(team2Level);

    }

    private static List<Player> getPlayersByPositionCategory(
            List<Player> players, SoccerPositionCategoryEnum positionCategoryEnum) {
        return Optional.ofNullable(players).stream().flatMap(Collection::stream)
                .filter(player ->
                        positionCategoryEnum == Optional.ofNullable(SoccerPositionEnum.resolveByPositionName(player.getPreferredPosition()))
                                .map(SoccerPositionEnum::getPositionCategory)
                                .orElse(SoccerPositionCategoryEnum.UNKNOWN))
                .collect(Collectors.toList());
    }
}
