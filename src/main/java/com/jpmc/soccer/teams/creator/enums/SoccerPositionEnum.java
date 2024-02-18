package com.jpmc.soccer.teams.creator.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * The enum Soccer position.
 */
public enum SoccerPositionEnum {
    GOALKEEPER("Goalkeeper", SoccerPositionCategoryEnum.GOALKEEPER),
    FULL_BACK("Full Back", SoccerPositionCategoryEnum.DEFENDER),
    CENTER_BACK("Center Back", SoccerPositionCategoryEnum.DEFENDER),
    OUTSIDE_BACK("Outside Back", SoccerPositionCategoryEnum.DEFENDER),
    DEFENSIVE_MIDFIELDER("Defensive Midfielder", SoccerPositionCategoryEnum.MIDFIELDER),
    CENTRAL_MIDFIELDER("Central Midfielder", SoccerPositionCategoryEnum.MIDFIELDER),
    ATTACKING_MIDFIELDER("Attacking Midfielder", SoccerPositionCategoryEnum.MIDFIELDER),
    WINGER("Winger", SoccerPositionCategoryEnum.FORWARD),
    FORWARD("Forward", SoccerPositionCategoryEnum.FORWARD),
    STRIKER("Striker", SoccerPositionCategoryEnum.FORWARD);

    private final String positionName;
    private final SoccerPositionCategoryEnum positionCategory;

    SoccerPositionEnum(String positionName, SoccerPositionCategoryEnum positionCategory) {
        this.positionName = positionName;
        this.positionCategory = positionCategory;
    }

    /**
     * Gets position name.
     *
     * @return the position name
     */
    public String getPositionName() {
        return positionName;
    }

    /**
     * Gets position category.
     *
     * @return the position category
     */
    public SoccerPositionCategoryEnum getPositionCategory() {
        return positionCategory;
    }

    /**
     * Resolve by position name soccer position enum.
     *
     * @param positionName the position name
     * @return soccer position enum
     */
    public static SoccerPositionEnum resolveByPositionName(String positionName) {
        if (StringUtils.isBlank(positionName)) {
            return null;
        }
        return Arrays.stream(SoccerPositionEnum.values())
                .filter(soccerPositionEnum -> soccerPositionEnum.positionName.equals(positionName))
                .findFirst().orElse(null);
    }
}
