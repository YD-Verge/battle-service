package com.YDVerge.battle_service.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BattlerEvent {

    private String matchId;
    private String playerId;
    private String eventType;  
    private String description;
    private String EnemyId;

    
}