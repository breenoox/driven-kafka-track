package com.brenoox.carsimulator.core.dto;

import java.time.Instant;
import java.util.UUID;

public record TelemetryDTO(
        UUID carId,
        int speed,
        double totalKm,
        String timestamp
) { }
