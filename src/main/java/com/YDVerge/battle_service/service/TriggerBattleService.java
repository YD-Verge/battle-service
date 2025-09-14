package com.YDVerge.battle_service.service;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class TriggerBattleService {
    
    
    public TriggerBattleService(EventBus eventBus, Map<String, LootTable> lootTables) {
        this.eventBus = eventBus;
        this.lootTables = lootTables;
    }

    public void fight(String enemyId, String playerId) {
//        if (table == null) {
//            System.out.println("No loot table for enemy: " + enemyId);
//            return;
//        }
        int enemyHealth = table.getHealth();
        int enemyAttack = table.getAttack();
        int playerHealth = player.getStat(StatType.HP);
        int playerAttack = player.getStat(StatType.STR) + player.getStat(StatType.INT);
        System.out.println("Fighting " + enemyId + " with " + enemyHealth + " HP");
        while(enemyHealth >= 0 && playerHealth >= 0) {
            System.out.println(enemyId + "'s health is " + enemyHealth);
            System.out.println(player.getDescription() + "attacked for " + playerAttack + " damage!!");
            enemyHealth = enemyHealth - playerAttack;
            if (enemyHealth <= 0) continue;
            System.out.println(player.getDescription() + "'s health is " + playerHealth);
            System.out.println(enemyId + " attacked for " + enemyAttack + " damage!!");
            playerHealth = playerHealth - enemyAttack;
            
        }
        
        String result = (enemyHealth <= 0) ? "YOU WIN" : "YOU LOSE";
        System.out.println(result);
        
        int gold = table.getGoldMin() + random.nextInt(table.getGoldMax() - table.getGoldMin() + 1);

        // Pick loot (at most 1 for now, could extend to multiple drops)
	    String lootItem = null;
	    int lootQty = 0;
	    for (LootEntry entry : table.getLoot()) {
	        if (random.nextDouble() < entry.getChance()) {
	            lootItem = entry.getItem();
	            lootQty = entry.getMin() + random.nextInt(entry.getMax() - entry.getMin() + 1);
	            break;
	        }
	    }

        eventBus.publish(new BattleCompletedEvent(player, gold, lootItem, lootQty));
    }

}
