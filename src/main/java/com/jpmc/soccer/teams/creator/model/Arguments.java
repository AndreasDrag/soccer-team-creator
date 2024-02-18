package com.jpmc.soccer.teams.creator.model;

import com.beust.jcommander.Parameter;
import lombok.Data;

@Data
public class Arguments {

    @Parameter(names = "-filePath")
    private String filePath;

    @Parameter(names = "-attempt")
    private Integer attempts;
}
