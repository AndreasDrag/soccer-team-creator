package com.jpmc.soccer.teams.creator.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
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
    STRIKER("Striker", SoccerPositionCategoryEnum.FORWARD),
    ;

    private final String positionName;
    private final SoccerPositionCategoryEnum positionCategory;

    public static SoccerPositionEnum resolveByPositionName(String positionName) {
        if (StringUtils.isBlank(positionName)) {
            return null;
        }
        return Arrays.stream(SoccerPositionEnum.values())
                .filter(soccerPositionEnum -> soccerPositionEnum.positionName.equals(positionName))
                .findFirst().orElse(null);
    }
}
