package com.jpmc.soccer.teams.creator.exception;

/**
 * The type Soccer team creator exception.
 */
public class SoccerTeamCreatorException extends RuntimeException {
    /**
     * Instantiates a new Soccer team creator exception.
     *
     * @param message the error message
     */
    public SoccerTeamCreatorException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Soccer team creator exception.
     *
     * @param message the error message
     * @param e       the exception
     */
    public SoccerTeamCreatorException(String message, Exception e) {
        super(message, e);
    }
}
