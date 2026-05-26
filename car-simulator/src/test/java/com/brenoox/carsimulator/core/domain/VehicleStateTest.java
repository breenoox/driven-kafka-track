package com.brenoox.carsimulator.core.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleStateTest {

    @Test
    @DisplayName("Deve atualizar a velocidade e aumentar a quilometragem quando o veículo se movimentar")
    void updateSpeedAndKilometers() {
        //Arrange
        UUID carId = UUID.randomUUID();
        double initialKm = 120000.0;
        VehicleState vehicle = new VehicleState(carId, initialKm);

        //Act
        vehicle.accelerateAndMove();

        //Assert
        assertEquals(carId, vehicle.getCarId(), "ID não deve mudar");
        assertTrue(
                vehicle.getCurrentSpeed() >= 40 && vehicle.getCurrentSpeed() <= 140,
                "Velocidade gerada (" + vehicle.getCurrentSpeed() + " km/h) deve estar entre 40 e 140 km/h"
        );
        assertTrue(
                vehicle.getTotalKm() > initialKm,
                "A quilometragem gerada (" + vehicle.getTotalKm() + " km) deve ser maior que a inicial (" + initialKm + " km)"
        );
    }
}
