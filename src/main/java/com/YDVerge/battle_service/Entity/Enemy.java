package com.YDVerge.battle_service.Entity;

import java.util.List;

import lombok.Data;

@Data
public class Enemy {
    private int health;
    private int attack;
    private int goldMin;
    private int goldMax;
    private List<LootTableEntryDTO> loot;

}
