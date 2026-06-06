package com.dev.brenoox.billing_processor.infra.kafka.consumer;

import com.dev.brenoox.billing_processor.core.dto.TelemetryDTO;
import com.dev.brenoox.billing_processor.core.usecase.BillingUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TelemetryConsumer {
    private final Logger logger = LoggerFactory.getLogger(TelemetryConsumer.class);
    private final BillingUseCase billingUseCase;

    public TelemetryConsumer(BillingUseCase billingUseCase) {
        this.billingUseCase = billingUseCase;
    }

    @Transactional
    @KafkaListener(topics = "${app.kafka.telemetry-topic}", groupId = "billing-processor-group")
    public void consume(@Payload List<TelemetryDTO> telemetryList, Acknowledgment acknowledgment) {
        logger.info("Recebido lote com {} mensagens do Kafka.", telemetryList.size());

        try {

            Map<UUID, Double> aggregatedTelemetry = telemetryList
                    .stream()
                    .collect(Collectors.groupingBy(
                            TelemetryDTO::carId,
                            Collectors.summingDouble(TelemetryDTO::totalKm)
                    ));

            aggregatedTelemetry.forEach((carId, totalKm) -> {
               TelemetryDTO consolidatedDTO = new TelemetryDTO(
                       carId,
                       0,
                       totalKm,
                       Instant.now().toString()
               );

               billingUseCase.execute(consolidatedDTO);
            });

            acknowledgment.acknowledge();
            logger.debug("Lote processado e commitado com sucesso.");

        } catch (Exception e) {
            logger.error("Erro ao processar lote de telemetria: {}", e.getMessage());
        }
    }
}
