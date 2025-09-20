package com.YDVerge.battle_service.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LootTableEntryDTO {
    private String item;
    private double chance;
    private int min;
    private int max;

    // getters and setters
}
