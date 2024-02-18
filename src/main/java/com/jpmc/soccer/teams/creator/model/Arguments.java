package com.jpmc.soccer.teams.creator.model;

import com.beust.jcommander.Parameter;

public class Arguments {

    @Parameter(names = "-filePath", required = true)
    private String filePath;

    @Parameter(names = "-attempts")
    private Integer attempts = 50;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }
}
