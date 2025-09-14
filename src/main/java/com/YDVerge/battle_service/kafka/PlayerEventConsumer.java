package com.YDVerge.battle_service.kafka;

import org.springframework.stereotype.Component;

import com.YDVerge.battle_service.service.TriggerBattleService;

@Component
public class PlayerEventConsumer {
    
    private final TriggerBattleService triggerBattleService;

    public PlayerAttackEventConsumer(TriggerBattleService triggerBattleService) {
        this.triggerBattleService = triggerBattleService;
    }

    @KafkaListener(topics = "player-attack", groupId = "battler-service")
    public void consumePlayerAttack(PlayerAttackEvent event) {
        triggerBattleService.handlePlayerAttack(event);
    }
}
