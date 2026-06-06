package com.dev.brenoox.billing_processor.core.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Billing {
    private final BigDecimal totalKm;
    private final BigDecimal pricePerKm; // Example price per kilometer

    public Billing(
            double totalKm,
            double pricePerKm
    ) {
        this.totalKm = BigDecimal.valueOf(totalKm);
        this.pricePerKm = BigDecimal.valueOf(pricePerKm);
    }

    public BigDecimal calculate() {
        return totalKm.multiply(pricePerKm).setScale(2, RoundingMode.HALF_UP);
    }
}