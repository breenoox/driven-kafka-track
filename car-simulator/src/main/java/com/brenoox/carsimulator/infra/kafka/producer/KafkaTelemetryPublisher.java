package com.brenoox.carsimulator.infra.kafka.producer;

import com.brenoox.carsimulator.core.dto.TelemetryDTO;
import com.brenoox.carsimulator.core.gateway.TelemetryPublisherGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaTelemetryPublisher implements TelemetryPublisherGateway {

    private static final Logger log = LoggerFactory.getLogger(KafkaTelemetryPublisher.class);

    private final KafkaTemplate<String, TelemetryDTO> kafkaTemplate;
    private final String topic;

    public KafkaTelemetryPublisher(
            KafkaTemplate<String, TelemetryDTO> kafkaTemplate,
            @Value("${app.kafka.telemetry-topic}") String topic
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void publish(TelemetryDTO telemetryDTO) {
        String key = telemetryDTO.carId().toString();

        kafkaTemplate.send(topic, key, telemetryDTO).whenComplete((result, exception) -> {
            if (exception != null) {
                log.error("Falha ao enviar telemetria do carro {}: {}", key, exception.getMessage());
            } else {
                log.debug("Telemetria do carro {} enviada para a partição {}", key, result.getRecordMetadata().partition());
            }
        });
    }
}

