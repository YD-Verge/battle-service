package com.YDVerge.battle_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BattlerEventConsumer {

    @KafkaListener(topics = "${kafka.topic.battler-events}", groupId = "battler-service")
    public void consume(BattlerEvent event) {
        System.out.println("Received battler event: " + event);
        // Process the event, e.g., update player status
    }
}
