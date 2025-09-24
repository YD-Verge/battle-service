package com.YDVerge.battle_service;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.YDVerge.battle_service.kafka.BattleCompletedEvent;

@EnableKafka
@SpringBootApplication
public class BattleServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BattleServiceApplication.class, args);
	}
	
	
	
	/*
	 * @Bean public ConcurrentKafkaListenerContainerFactory<String,
	 * BattleCompletedEvent> battleCompleteKafkaListenerFactory() { Map<String,
	 * Object> props = new HashMap<>();
	 * props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.0.104:9092");
	 * props.put(ConsumerConfig.GROUP_ID_CONFIG, "battle-service");
	 * props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
	 * 
	 * return new ConcurrentKafkaListenerContainerFactory<String,
	 * BattleCompletedEvent>() {{ setConsumerFactory(new
	 * DefaultKafkaConsumerFactory<>( props, new StringDeserializer(), new
	 * JsonDeserializer<>(BattleCompletedEvent.class, false) )); }}; }
	 */

}
