package com.YDVerge.battle_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.YDVerge.battle_service.elasticsearch.BattleLogIndexer;
import com.YDVerge.battle_service.service.TriggerBattleService;

@Component
public class PlayerEventConsumer {

    private final BattleLogIndexer indexer;

    private final TriggerBattleService triggerBattleService ;

    public PlayerEventConsumer(TriggerBattleService triggerBattleService,BattleLogIndexer indexer) {
	this.triggerBattleService = triggerBattleService;
	this.indexer = indexer;
    }

    @KafkaListener(
	    topics = "player-attack",
	    groupId = "battle-service",
	    containerFactory = "playerAttackKafkaListenerFactory" 
	    )
    public void consumePlayerAttack(PlayerAttackEvent event) {
	triggerBattleService.handlePlayerAttack(event);
    }

    @KafkaListener(
	    topics = "battle-completed",
	    groupId = "battle-service",
	    containerFactory = "battleCompletedKafkaListenerFactory"
	    )
    public void jusListen(BattleCompletedEvent event) {
	indexer.indexBattle(event);
	System.out.println("Was Able to Listen BattleCompleted " + event);;
    }
}
