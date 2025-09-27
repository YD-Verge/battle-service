package com.YDVerge.battle_service.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class BattleLogService {

    private final ElasticsearchClient client;

    public BattleLogService(ElasticsearchClient client) {
        this.client = client;
    }

    public List<BattleLogDoc> getBattlesByPlayer(String playerId) {
        try {
            SearchResponse<BattleLogDoc> response = client.search(s -> s
                    .index("battles")
                    .query(q -> q
                        .match(t -> t
                            .field("playerId")
                            .query(playerId)
                        )
                    ),
                BattleLogDoc.class);

            return response.hits().hits()
                    .stream()
                    .map(hit -> hit.source())
                    .toList();

        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch battles for player " + playerId, e);
        }
    }

    public List<BattleLogDoc> getAllBattles() {
        try {
            SearchResponse<BattleLogDoc> response = client.search(s -> s
                    .index("battles")
                    .query(Query.of(q -> q.matchAll(m -> m))),
                BattleLogDoc.class);

            return response.hits().hits()
                    .stream()
                    .map(hit -> hit.source())
                    .toList();

        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch battles", e);
        }
    }
    
    public int getGoldByPlayer(String playerId) {
        try {
            SearchResponse<BattleLogDoc> response = client.search(s -> s
                    .index("battles")
                    .query(q -> q
                        .match(t -> t
                            .field("playerId")
                            .query(playerId)
                        )
                    ),
                BattleLogDoc.class);

            int totalGold = response.hits().hits()
        	    .stream()
        	    .map(hit -> hit.source())  
        	    .mapToInt(BattleLogDoc::gold)  
        	    .sum();

            return totalGold;

        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch battles for player " + playerId, e);
        }
    }
    
    
    

    // Same record you used in BattleLogIndexer
    public record BattleLogDoc(
            String playerId,
            int gold,
            String lootItem,
            int lootQty,
            String result,
            String timestamp
    ) {}
}

