package com.YDVerge.battle_service.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

    private final String bootstrapServers = "192.168.0.101:9092";

    // ---------------- STRING PRODUCER ----------------
    @Bean
    public ProducerFactory<String, String> stringProducerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, String> stringKafkaTemplate() {
        return new KafkaTemplate<>(stringProducerFactory());
    }

    // ---------------- BATTLE COMPLETED EVENT PRODUCER ----------------
    @Bean
    public ProducerFactory<String, BattleCompletedEvent> battleCompletedProducerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, BattleCompletedEvent> battleCompletedKafkaTemplate() {
        return new KafkaTemplate<>(battleCompletedProducerFactory());
    }
    
    @Bean
    public ProducerFactory<String, BattlerEvent> battlerEventProducerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, BattlerEvent> battlerEventKafkaTemplate() {
        return new KafkaTemplate<>(battlerEventProducerFactory());
    }

    // ---------------- STRING CONSUMER ----------------
    @Bean
    public ConsumerFactory<String, String> stringConsumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "game-events");
        return new DefaultKafkaConsumerFactory<>(config);
    }

    // ---------------- BATTLE COMPLETED EVENT CONSUMER ----------------
    @Bean
    public ConsumerFactory<String, BattleCompletedEvent> battleCompletedConsumerFactory() {
        JsonDeserializer<BattleCompletedEvent> deserializer = new JsonDeserializer<>(BattleCompletedEvent.class);
        deserializer.addTrustedPackages("*"); // allow all packages

        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "battle-service");

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
    }
    
    @Bean
    public ProducerFactory<String, PlayerAttackEvent> playerAttackProducerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, PlayerAttackEvent> playerAttackKafkaTemplate() {
        return new KafkaTemplate<>(playerAttackProducerFactory());
    }

    @Bean
    public ConsumerFactory<String, PlayerAttackEvent> playerAttackConsumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "com.YDVerge.battle-service.kafka");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "battle-service");
        return new DefaultKafkaConsumerFactory<>(
            config,
            new StringDeserializer(),
            new JsonDeserializer<>(PlayerAttackEvent.class)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PlayerAttackEvent> playerAttackKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PlayerAttackEvent> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(playerAttackConsumerFactory());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BattleCompletedEvent> battleCompletedKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, BattleCompletedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(battleCompletedConsumerFactory());
        return factory;
    }

    // ---------------- TOPICS ----------------
    @Bean
    public NewTopic battleTopic() {
        return new NewTopic("battle-events", 1, (short) 1);
    }

    @Bean
    public NewTopic battleCompletedTopic() {
        return new NewTopic("battle-completed", 1, (short) 1);
    }

    @Bean
    public NewTopic economyTopic() {
        return new NewTopic("economy-events", 1, (short) 1);
    }
}
