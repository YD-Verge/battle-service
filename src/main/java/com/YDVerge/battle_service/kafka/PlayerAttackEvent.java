package com.YDVerge.battle_service.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlayerAttackEvent {
    private String playerId;
    private String enemyId;
    private String matchId;

}
