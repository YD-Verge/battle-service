package com.YDVerge.battle_service.kafka;

import lombok.Data;

@Data
public class EnemyAttackEvent {
    private String playerId;
    private String enemyId;
    private String matchId;

}
