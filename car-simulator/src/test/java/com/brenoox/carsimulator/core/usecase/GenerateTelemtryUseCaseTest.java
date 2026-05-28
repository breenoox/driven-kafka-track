package com.brenoox.carsimulator.core.usecase;

import com.brenoox.carsimulator.core.dto.TelemetryDTO;
import com.brenoox.carsimulator.core.gateway.TelemetryPublisherGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes Use Case GenerateTelemetryUseCase")
public class GenerateTelemtryUseCaseTest {

    @Mock
    private TelemetryPublisherGateway telemetryPublisherGateway;
    private GenerateTelemtryUseCase generateTelemtryUseCase;

    @BeforeEach
    void setUp() {
        generateTelemtryUseCase = new GenerateTelemtryUseCase(telemetryPublisherGateway);
    }

    @Test
    @DisplayName("Deve publicar a telemetria exatamente 3 vezes para os veículos rastreados")
    void shouldPublishTelemetryForEveryTrackedVehicle() {
        // Act
        generateTelemtryUseCase.execute();

        // Assert
        verify(telemetryPublisherGateway, times(3)).publish(any(TelemetryDTO.class));
    }

    @Test
    @DisplayName("Deve capturar e validar os DTOs enviados para o gateway")
    void shouldPublishCorrectTelemetryData() {
        // Act
        generateTelemtryUseCase.execute();

        // Assert
        ArgumentCaptor<TelemetryDTO> captor = ArgumentCaptor.forClass(TelemetryDTO.class);
        verify(telemetryPublisherGateway, times(3)).publish(captor.capture());

        List<TelemetryDTO> publishedTelemetries = captor.getAllValues();
        assertEquals(3, publishedTelemetries.size(), "Deve publicar exatamente 3 telemetrias");

        for (TelemetryDTO telemetry : publishedTelemetries) {

            assertNotNull(telemetry.carId(), "carId não deve ser nulo");

            assertTrue(telemetry.speed() >= 40 && telemetry.speed() <= 140,
                    "Velocidade fora do padrão: " + telemetry.speed());

            assertTrue(telemetry.totalKm() >= 0,
                    "Total de km não pode ser zero: " + telemetry.totalKm());

            // Verifica se o timestamp foi gerado corretamente
            assertNotNull(telemetry.timestamp(), "O timestamp não pode ser nulo");
            assertFalse(telemetry.timestamp().isBlank(), "O timestamp não pode ser vazio");
        }
    }
}
