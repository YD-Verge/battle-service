package com.YDVerge.battle_service.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.YDVerge.battle_service.Entity.LootTableDTO;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class EnemyIndexer {

    private final ElasticsearchClient client;

    public EnemyIndexer(ElasticsearchClient client) {
        this.client = client;
    }

    public void indexEnemies(Map<String, LootTableDTO> enemies) {
        enemies.forEach((enemyId, lootTable) -> {
            try {
                IndexResponse response = client.index(i -> i
                        .index("enemies")
                        .id(enemyId)
                        .document(lootTable)
                );
                System.out.println("Indexed enemy: " + response.id());
            } catch (IOException e) {
                throw new RuntimeException("Failed to index enemy: " + enemyId, e);
            }
        });
    }
}
