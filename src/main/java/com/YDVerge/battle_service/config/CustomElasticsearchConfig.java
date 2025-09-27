package com.YDVerge.battle_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

@Configuration
public class CustomElasticsearchConfig extends ElasticsearchConfiguration {

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
            .connectedTo("localhost:9200")
            // optionally .withBasicAuth("user","password")
            .build();
    }

    // You don’t need to explicitly define ElasticsearchClient or RestClient
    // Spring will create them (ElasticsearchClient, RestClient, ElasticsearchOperations)
    // based on your clientConfiguration override.
}
