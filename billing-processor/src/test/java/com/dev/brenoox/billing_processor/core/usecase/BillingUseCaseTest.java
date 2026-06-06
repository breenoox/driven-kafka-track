package com.dev.brenoox.billing_processor.core.usecase;

import com.dev.brenoox.billing_processor.core.dto.SaveBillingDTO;
import com.dev.brenoox.billing_processor.core.dto.TelemetryDTO;
import com.dev.brenoox.billing_processor.core.gateway.BillingRepositoryGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes Use Case BillingUseCaseTest")
public class BillingUseCaseTest {

    @Mock
    private BillingRepositoryGateway billingRepositoryGateway;

    @Captor
    private ArgumentCaptor<SaveBillingDTO> saveBillingCaptor;

    private BillingUseCase billingUseCase;

    @BeforeEach
    void setUp() {
        billingUseCase = new BillingUseCase(billingRepositoryGateway);
    }

    @Test
    @DisplayName("Calcular e salvar faturamento com sucesso para uma telemetria válida")
    void shouldCalculateAndSaveBillingSuccessfully() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        int speed = 80;
        double totalKm = 100.0;
        String timestamp = "2026-06-05T10:00:00Z";

        TelemetryDTO telemetryDTO = new TelemetryDTO(
                uuid,
                speed,
                totalKm,
                timestamp
        );

        double expectedBilling = 50.0;

        // Act
        billingUseCase.execute(telemetryDTO);
        verify(billingRepositoryGateway, times(1)).incrementBilling(saveBillingCaptor.capture());
        SaveBillingDTO saveBillingDTO = saveBillingCaptor.getValue();

        // Assert
        assertEquals(saveBillingDTO.carId(), uuid);
        assertEquals(totalKm, saveBillingDTO.kmTraveled());
        assertEquals(expectedBilling, saveBillingDTO.billing());
    }
}
