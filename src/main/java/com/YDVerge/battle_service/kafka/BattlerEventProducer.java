package com.YDVerge.battle_service.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class BattlerEventProducer {

    private final KafkaTemplate<String, BattlerEvent> kafkaTemplate;

    @Value("${kafka.topic.battler-events}")
    private String topicName;

    public BattlerEventProducer(KafkaTemplate<String, BattlerEvent> kafkaTemplate) {
	this.kafkaTemplate = kafkaTemplate;
    }

    public void sendBattlerEvent(BattlerEvent event) {
	kafkaTemplate.send(topicName, event);
    }
}
