package com.jpmc.soccer.teams.creator.model;

import com.opencsv.bean.CsvBindByName;

public class Player {

    @CsvBindByName(column = "ID", required = true)
    private String id;

    @CsvBindByName(column = "Skill Level", required = true)
    private Integer skillLevel;

    @CsvBindByName(column = "Preferred Position", required = true)
    private String preferredPosition;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(Integer skillLevel) {
        this.skillLevel = skillLevel;
    }

    public String getPreferredPosition() {
        return preferredPosition;
    }

    public void setPreferredPosition(String preferredPosition) {
        this.preferredPosition = preferredPosition;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.id);
        sb.append(" (");
        sb.append(this.preferredPosition);
        sb.append(")");
        return sb.toString();
    }
}
