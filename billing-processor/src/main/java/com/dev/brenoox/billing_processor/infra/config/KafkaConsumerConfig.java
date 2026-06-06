package com.dev.brenoox.billing_processor.infra.config;

import com.dev.brenoox.billing_processor.core.dto.TelemetryDTO;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
    private String bootstrapServer;

    @Bean
    public ConsumerFactory<String, TelemetryDTO> consumerFactory() {
        Map<String, Object> configs = new HashMap<>();

        // Connection
        // Sets the Kafka broker address (where the consumer will connect)
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        // Defines the consumer group ID to allow load balancing across multiple instances
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, "billing-processor-group");

        // Deserialization
        // Specifies the class used to convert the message key from bytes to a String
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // Specifies the class used to convert the message payload from bytes to a JSON object
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        // Secures the deserializer by only allowing it to instantiate classes from this specific package
        configs.put(JsonDeserializer.TRUSTED_PACKAGES, "com.dev.brenoox.billing_processor.core.dto");

        // Performance
        // Limits the maximum number of records returned in a single call to poll()
        configs.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 500);
        // Sets the minimum amount of data (in bytes) the server should return for a fetch request (1MB here)
        configs.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 1048576);
        // Sets the maximum amount of time the server will block before answering the fetch request if data isn't enough
        configs.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 10000);

        // Offset Management
        // Disables auto-committing of offsets to allow manual control after successful business logic execution
        configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        // Tells the consumer to start reading from the beginning of the topic if no previous offset is found
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // Isolation
        // Ensures the consumer only reads messages that have been fully committed by transactional producers
        configs.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");

        return new DefaultKafkaConsumerFactory<>(
                configs,
                new StringDeserializer(),
                new JsonDeserializer<>(TelemetryDTO.class, false)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TelemetryDTO> kafkaListenerContainerFactory() {

        // Instantiates the factory that will build and manage the Kafka listener containers (the threads executing your @KafkaListener)
        ConcurrentKafkaListenerContainerFactory<String, TelemetryDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        // Injects the foundational consumer properties (broker connection, network limits, and deserializers) built in the consumerFactory() method
        factory.setConsumerFactory(consumerFactory());

        // Concurrency: Enables parallel processing by spinning up 3 separate listener threads (Requires at least 3 partitions in the Kafka topic to be effective)
        factory.setConcurrency(3);

        // Enables micro-batching, changing the listener signature to receive a List<TelemetryDTO> instead of a single message, reducing database I/O operations
        factory.setBatchListener(true);

        // Ack Mode: Disables automatic offset tracking and forces the application to manually call Acknowledgment.acknowledge() to commit the read immediately after successful processing
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);

        // Resilience: Creates an error handling mechanism using a FixedBackOff strategy
        // In case of an exception, it pauses the specific thread for 2000 milliseconds (2 seconds) and retries the exact same batch up to 3 times before giving up
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
                new FixedBackOff(2000L, 3L)
        );

        // Applies the configured retry mechanism globally to all listeners created by this factory
        factory.setCommonErrorHandler(errorHandler);

        return factory;
    }
}
