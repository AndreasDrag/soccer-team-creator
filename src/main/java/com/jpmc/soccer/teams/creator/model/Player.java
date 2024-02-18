package com.jpmc.soccer.teams.creator.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class Player {
    @CsvBindByName(column = "ID", required = true)
    private String id;

    @CsvBindByName(column = "Skill Level", required = true)
    private Integer skillLevel;

    @CsvBindByName(column = "Preferred Position", required = true)
    private String preferredPosition;
}
