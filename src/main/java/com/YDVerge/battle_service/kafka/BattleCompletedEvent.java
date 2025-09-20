package com.YDVerge.battle_service.kafka;

import lombok.Data;

@Data
public class BattleCompletedEvent {
    private String playerId;
    private String enemyId;
    private boolean victory;
    private int gold;
    private String lootItem;
    private int lootQty;
    private String result;


    public BattleCompletedEvent(String playerId, int gold, String lootItem, int lootQty, String result) {
        this.playerId = playerId;
        this.gold = gold;
        this.lootItem = lootItem;
        this.lootQty = lootQty;
        this.result = result;
    }
    // constructor, getters, setters
}
