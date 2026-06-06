package com.dev.brenoox.billing_processor.core.dto;

import java.util.UUID;

public record TelemetryDTO(
        UUID carId,
        int speed,
        double totalKm,
        String timestamp
) { }
