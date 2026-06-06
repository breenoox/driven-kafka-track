package com.dev.brenoox.billing_processor.core.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Testes de Domínio: Billing")
public class BillingTest {

    @Test
    @DisplayName("Deve calcular o faturamento corretamente sem necessidade de arredondamento")
    void shouldCalculateBillingCorrectly() {
        // Arrange
        double totalKm = 100;
        double pricePerKm = 0.5;
        Billing billing = new Billing(totalKm, pricePerKm);

        BigDecimal expectedValue = new BigDecimal("50.00");

        // Act
        BigDecimal result = billing.calculate();

        // Assert
        assertEquals(expectedValue, result);
    }

    @Test
    @DisplayName("Deve calcular e arredondar o valor para cima")
    void shouldCalculateAndRoundUpCorrectly() {
        // Arrange
        double totalKm = 10.55;
        double pricePerKm = 1.5;
        Billing billing = new Billing(totalKm, pricePerKm);

        BigDecimal expectedValue = new BigDecimal("15.83");

        // Act
        BigDecimal result = billing.calculate();

        // Assert
        assertEquals(expectedValue, result);
    }

    @Test
    @DisplayName("Deve calcular e arredondar o valor para baixo")
    void shouldCalculateAndRoundDownCorrectly() {
        // Arrange
        double totalKm = 10.55;
        double pricePerKm = 1.4;
        Billing billing = new Billing(totalKm, pricePerKm);

        BigDecimal expectedValue = new BigDecimal("14.77");

        // Act
        BigDecimal result = billing.calculate();

        // Assert
        assertEquals(expectedValue, result);
    }

    @Test
    @DisplayName("Deve retornar zero quando a quilometragem for zero")
    void shouldReturnZeroWhenKmIsZero() {
        // Arrange
        double totalKm = 0.0;
        double pricePerKm = 0.5;
        Billing billing = new Billing(totalKm, pricePerKm);
        BigDecimal expectedValue = new BigDecimal("0.00");

        // Act
        BigDecimal result = billing.calculate();

        // Assert
        assertEquals(expectedValue, result);
    }
}
