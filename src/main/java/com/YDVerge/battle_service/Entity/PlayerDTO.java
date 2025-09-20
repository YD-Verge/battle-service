package com.YDVerge.battle_service.Entity;

import java.util.EnumMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerDTO {
    private String id;
    private String playerId;
    private int level;
    private Map<String, Integer> stats; // HP, STR, INT etc.

    public Map<String, Integer> getStats() {
        return stats;
    }

    public void setStats(Map<String, Integer> stats) {
        this.stats = stats;
    }

    // This method will be called by Jackson with the nested playerRole object
    @JsonProperty("playerRole")
    private void unpackPlayerRole(Map<String, Object> playerRole) {
        // Extract stats from playerRole map
        if (playerRole != null && playerRole.containsKey("stats")) {
            // Cast to Map<String,Integer>
            Object statsObj = playerRole.get("stats");
            if (statsObj instanceof Map) {
                this.stats = (Map<String, Integer>) statsObj;
            }
        }
     }
}
