package com.jpmc.soccer.teams.creator;

import com.beust.jcommander.JCommander;
import com.jpmc.soccer.teams.creator.model.Arguments;
import com.jpmc.soccer.teams.creator.model.Player;
import com.jpmc.soccer.teams.creator.service.TeamCreatorService;
import com.jpmc.soccer.teams.creator.service.impl.TeamCreatorServiceImpl;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.collections.CollectionUtils;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SoccerTeamCreatorApplication {

    public static void main(String[] args) {
        Arguments arguments = new Arguments();

        JCommander jCommander = JCommander.newBuilder()
                .programName(SoccerTeamCreatorApplication.class.getSimpleName())
                .addObject(arguments)
                .build();

        jCommander.parse(args);

        String filePath = arguments.getFilePath();
        Path path = Paths.get(filePath);

        List<Player> players;
        try (Reader reader = Files.newBufferedReader(path)) {
            CsvToBean<Player> playersBean = new CsvToBeanBuilder<Player>(reader)
                    .withType(Player.class)
                    .build();
            players = playersBean.parse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (CollectionUtils.isEmpty(players) || players.size() % 2 != 0) {
            throw new RuntimeException("Please provide the correct number of players.");
        }

        TeamCreatorService teamCreatorService = new TeamCreatorServiceImpl();

        teamCreatorService.distributePlayers(players, arguments.getAttempts());

    }
}