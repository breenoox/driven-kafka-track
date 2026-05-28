package com.brenoox.carsimulator.infra.config;

import com.brenoox.carsimulator.core.dto.TelemetryDTO;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
    private String bootstrapServer;

    @Bean
    public ProducerFactory<String, TelemetryDTO> producerFactory() {
        Map<String, Object> configs = new HashMap<>();

        // Connection
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        // Performance
        configs.put(ProducerConfig.LINGER_MS_CONFIG, 5);
        configs.put(ProducerConfig.BATCH_SIZE_CONFIG, 65536);
        configs.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "lz4");

        // Resilience
        configs.put(ProducerConfig.ACKS_CONFIG, "all");
        configs.put(ProducerConfig.RETRIES_CONFIG, 3);
        configs.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 1000);

        // Idempotence
        configs.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        configs.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 5);

        return new DefaultKafkaProducerFactory<>(configs);
    }

    @Bean
    public KafkaTemplate<String, TelemetryDTO> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
