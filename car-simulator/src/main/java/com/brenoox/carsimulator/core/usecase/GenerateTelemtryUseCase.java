package com.brenoox.carsimulator.core.usecase;

import com.brenoox.carsimulator.core.dto.TelemetryDTO;
import com.brenoox.carsimulator.core.gateway.TelemetryPublisherGateway;
import com.brenoox.carsimulator.core.domain.VehicleState;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class GenerateTelemtryUseCase {
    private final TelemetryPublisherGateway publisher;
    private final List<VehicleState> trackedVehicles = new ArrayList<>();

    public GenerateTelemtryUseCase(TelemetryPublisherGateway publisher) {
        this.publisher = publisher;

        trackedVehicles.add(new VehicleState(java.util.UUID.randomUUID(), 0.0));
        trackedVehicles.add(new VehicleState(java.util.UUID.randomUUID(), 0.0));
        trackedVehicles.add(new VehicleState(java.util.UUID.randomUUID(), 0.0));
    }

    public void execute() {
        for (VehicleState vehicle : trackedVehicles) {

            vehicle.accelerateAndMove();

            TelemetryDTO telemetry = new TelemetryDTO(
                vehicle.getCarId(),
                vehicle.getCurrentSpeed(),
                vehicle.getTotalMileage(),
                Instant.now().toString()
            );

            publisher.publish(telemetry);
        }
    }
}
