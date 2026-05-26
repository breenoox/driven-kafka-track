package com.brenoox.carsimulator.core.gateway;

import com.brenoox.carsimulator.core.dto.TelemetryDTO;

public interface TelemetryPublisherGateway {
    void publish(TelemetryDTO telemetryDTO);
}
