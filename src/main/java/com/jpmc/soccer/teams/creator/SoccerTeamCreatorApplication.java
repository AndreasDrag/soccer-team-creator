package com.jpmc.soccer.teams.creator;

import com.beust.jcommander.JCommander;
import com.jpmc.soccer.teams.creator.exception.SoccerTeamCreatorException;
import com.jpmc.soccer.teams.creator.model.Arguments;
import com.jpmc.soccer.teams.creator.model.Player;
import com.jpmc.soccer.teams.creator.service.TeamCreatorService;
import com.jpmc.soccer.teams.creator.service.impl.TeamCreatorServiceImpl;
import com.jpmc.soccer.teams.creator.validator.PlayersValidator;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * The type Soccer team creator application.
 */
public class SoccerTeamCreatorApplication {

    private static final PlayersValidator playersValidator = new PlayersValidator();

    private static final TeamCreatorService teamCreatorService = new TeamCreatorServiceImpl();

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Arguments arguments = extractArguments(args);

        String filePath = arguments.getFilePath();
        Path path = Paths.get(filePath);
        List<Player> players = getPlayersFromFile(path);

        playersValidator.validate(players);
        teamCreatorService.createBalancedTeams(players, arguments.getAttempts());
    }

    /**
     * Read players from given csv file.
     *
     * @param path the file path
     * @return the list of players
     */
    private static List<Player> getPlayersFromFile(Path path) {
        try (Reader reader = Files.newBufferedReader(path)) {
            CsvToBean<Player> playersBean = new CsvToBeanBuilder<Player>(reader)
                    .withType(Player.class)
                    .build();
            return playersBean.parse();
        } catch (IOException e) {
            throw new SoccerTeamCreatorException("Something went wrong reading players from file.", e);
        }
    }

    /**
     * Extract data from args and load them to arguments object.
     *
     * @param args the application arguments
     * @return the arguments
     */
    private static Arguments extractArguments(String[] args) {
        Arguments arguments = new Arguments();
        JCommander jCommander = JCommander.newBuilder()
                .programName(SoccerTeamCreatorApplication.class.getSimpleName())
                .addObject(arguments)
                .build();

        jCommander.parse(args);
        return arguments;
    }
}