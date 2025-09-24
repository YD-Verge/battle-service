package com.YDVerge.battle_service.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class BattleCompletedEventProducer {
    
    private final KafkaTemplate<String, BattleCompletedEvent> kafkaTemplate;

    private String topicName="battle-completed";

    public BattleCompletedEventProducer(KafkaTemplate<String, BattleCompletedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendBattleCompletedEvent(BattleCompletedEvent event) {
        kafkaTemplate.send(topicName, event);
    }

}
