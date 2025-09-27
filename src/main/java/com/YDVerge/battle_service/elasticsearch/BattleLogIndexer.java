package com.YDVerge.battle_service.elasticsearch;

import org.springframework.stereotype.Service;

import com.YDVerge.battle_service.kafka.BattleCompletedEvent;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;

import java.io.IOException;
import java.time.Instant;

@Service
public class BattleLogIndexer {

    private final ElasticsearchClient client;

    public BattleLogIndexer(ElasticsearchClient client) {
	this.client = client;

        // Ensure 'battles' index exists
        try {
            boolean exists = client.indices().exists(e -> e.index("battles")).value();
            if (!exists) {
                client.indices().create(c -> c.index("battles"));
                System.out.println("Created 'battles' index in Elasticsearch.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to check or create 'battles' index", e);
        }
    }

    public void indexBattle(BattleCompletedEvent event) {
        try {
            IndexResponse response = client.index(i -> i
                    .index("battles")
                    .id(event.getPlayerId() + "-" + System.currentTimeMillis())
                    .document(new BattleLogDoc(
                            event.getPlayerId(),
                            event.getGold(),
                            event.getLootItem(),
                            event.getLootQty(),
                            event.getResult(),
                            Instant.now().toString()
                    ))
            );
            System.out.println("Indexed battle: " + response.id());
        } catch (IOException e) {
            throw new RuntimeException("Failed to index battle", e);
        }
    }

    // helper doc class
    record BattleLogDoc(
            String playerId,
            int gold,
            String lootItem,
            int lootQty,
            String result,
            String timestamp
    ) {}
}