package com.YDVerge.battle_service.kafka;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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


    @JsonCreator
    public BattleCompletedEvent(
        @JsonProperty("playerId") String playerId,
        @JsonProperty("gold") int gold,
        @JsonProperty("lootItem") String lootItem,
        @JsonProperty("lootQty") int lootQty,
        @JsonProperty("result") String result) {
        this.playerId = playerId;
        this.gold = gold;
        this.lootItem = lootItem;
        this.lootQty = lootQty;
        this.result = result;
    }
    // constructor, getters, setters
}
