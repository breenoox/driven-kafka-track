package com.dev.brenoox.billing_processor.core.dto;

import java.util.UUID;

public record SaveBillingDTO(
    UUID carId,
    double kmTraveled,
    double billing
) { }
