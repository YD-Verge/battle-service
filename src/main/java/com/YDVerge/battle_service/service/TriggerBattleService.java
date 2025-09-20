package com.YDVerge.battle_service.service;

import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.YDVerge.battle_service.Entity.LootTableDTO;
import com.YDVerge.battle_service.Entity.LootTableEntryDTO;
import com.YDVerge.battle_service.Entity.PlayerDTO;
import com.YDVerge.battle_service.kafka.BattleCompletedEvent;
import com.YDVerge.battle_service.kafka.PlayerAttackEvent;
import com.YDVerge.battle_service.util.LootTableLoader;

@Service
public class TriggerBattleService {
    
    
    private final RestTemplate restTemplate;
    private final KafkaTemplate<String, BattleCompletedEvent> kafkaTemplate;
    private final LootTableLoader lootTableLoader;

    public TriggerBattleService(
	        RestTemplate restTemplate,
	        @Qualifier("battleCompletedKafkaTemplate") KafkaTemplate<String, BattleCompletedEvent> kafkaTemplate, LootTableLoader lootTableLoader
	    ) {
	        this.restTemplate = restTemplate;
	        this.kafkaTemplate = kafkaTemplate;
		this.lootTableLoader = lootTableLoader;
	    }

    
    public void handlePlayerAttack(PlayerAttackEvent event) {
        // Pull data from event and call fight
        String playerId = event.getPlayerId();
        String enemyId = event.getEnemyId();

        fight(playerId);
    }

    public BattleCompletedEvent fight(String playerId) {
	PlayerDTO player = restTemplate.getForObject(
		"http://localhost:8080/api/player/stats",
                PlayerDTO.class
        );
	System.out.println(player);

        LootTableDTO table = lootTableLoader.getEnemy();

        if (table == null || player == null) {
            System.out.println("Invalid enemy or player");
            return null;
        }
        Map<String, Integer> stats = player.getStats();
        // 3. Battle loop
        int enemyHealth = table.getHealth();
        int enemyAttack = table.getAttack();
        int playerHealth = stats.get("HP");
        int playerAttack = stats.get("STR") + stats.get("INT");

        while (enemyHealth > 0 && playerHealth > 0) {
            System.out.println("batttlle started");
            enemyHealth -= playerAttack;
            if (enemyHealth <= 0) break;
            playerHealth -= enemyAttack;
        }

        String result = (enemyHealth <= 0) ? "YOU WIN" : "YOU LOSE";
        System.out.println(result);

        // 4. Reward if win
        int gold = 0;
        String lootItem = null;
        int lootQty = 0;

        if (enemyHealth <= 0) {
            Random random = new Random();
            gold = table.getGoldMin() + random.nextInt(table.getGoldMax() - table.getGoldMin() + 1);

            for (LootTableEntryDTO entry : table.getLoot()) {
                if (random.nextDouble() < entry.getChance()) {
                    lootItem = entry.getItem();
                    lootQty = entry.getMin() + random.nextInt(entry.getMax() - entry.getMin() + 1);
                    break;
                }
            }
            System.out.println("loot distributed");
        }

        // 5. Publish event to Kafka
        BattleCompletedEvent event = new BattleCompletedEvent(playerId, gold, lootItem, lootQty, result);
        //kafkaTemplate.send("battle-completed", event);
        System.out.println(event + "   Inside Fight");
        return event;
    }
}