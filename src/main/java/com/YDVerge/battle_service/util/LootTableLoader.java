package com.YDVerge.battle_service.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.YDVerge.battle_service.Entity.LootTableDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
@Component
public class LootTableLoader {
    
    private final ObjectMapper objectMapper;
    private Map<String, LootTableDTO> lootTables = new HashMap<>();

    public LootTableLoader(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    @PostConstruct
    public void loadLootTables() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream in = LootTableLoader.class.getResourceAsStream("/loot-tables.json");
            TypeReference<Map<String, LootTableDTO>> typeRef = new TypeReference<>() {};
            lootTables = objectMapper.readValue(in, typeRef);
            System.out.println("✅ Loaded loot tables: " + lootTables.keySet());
        } catch (Exception e) {
            throw new RuntimeException("Failed to load loot tables", e);
        }
    }
    
    public LootTableDTO getEnemy() {
	if (lootTables == null || lootTables.isEmpty()) {
	        throw new IllegalStateException("Loot tables are not loaded or empty.");
	    }

	    List<String> keys = new ArrayList<>(lootTables.keySet());
	    String randomKey = keys.get(new Random().nextInt(keys.size()));
	    return lootTables.get(randomKey);
    }
    
    public Map<String, LootTableDTO> getAll() {
        return lootTables;
    }
}
