package com.YDVerge.battle_service.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public EventPublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishBattleEvent(String message) {
        kafkaTemplate.send("battle-events", message);
    }

    public void publishEconomyEvent(String message) {
        kafkaTemplate.send("economy-events", message);
    }
}
