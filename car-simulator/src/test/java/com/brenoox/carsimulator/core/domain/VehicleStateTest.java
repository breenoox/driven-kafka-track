package com.brenoox.carsimulator.core.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes Entidade VehicleState")
public class VehicleStateTest {

    private UUID carId;
    private VehicleState vehicleState;
    private final double initialKm = 1500.55;

    @BeforeEach
    void setUp() {
        carId = UUID.randomUUID();
        vehicleState = new VehicleState(carId, initialKm);
    }

    @Test
    @DisplayName("Deve inicializar o veículo com os valores corretos no construtor")
    void inicializeCorrectly() {
        assertEquals(carId, vehicleState.getCarId());
        assertEquals(0, vehicleState.getCurrentSpeed());
        assertEquals(initialKm, vehicleState.getTotalKm());
    }

    @Test
    @DisplayName("Deve acelerar e mover o veículo dentro dos limites de velocidade e distância esperados")
    void shouldAccelerateAndMoveWithinExpectedBounds() {

        //Act
        vehicleState.accelerateAndMove();

        //Assert
        assertEquals(carId, vehicleState.getCarId(), "ID não deve mudar");
        assertTrue(
                vehicleState.getCurrentSpeed() >= 40 && vehicleState.getCurrentSpeed() <= 140,
                "Velocidade gerada (" + vehicleState.getCurrentSpeed() + " km/h) deve estar entre 40 e 140 km/h"
        );
        assertTrue(
                vehicleState.getTotalKm() > initialKm,
                "A quilometragem gerada (" + vehicleState.getTotalKm() + " km) deve ser maior que a inicial (" + initialKm + " km)"
        );
    }

    @Test
    @DisplayName("Deve retornar a quilometragem total arredondada para duas casas decimais")
    void shouldReturnTotalMileageRoundedToTwoDecimals() {
        //Act
        VehicleState vehicleToRoundUp = new VehicleState(UUID.randomUUID(), 10.126);

        //Assert
        assertEquals(10.13, vehicleToRoundUp.getTotalMileage());

        //Act
        VehicleState vehicleToRoundDown = new VehicleState(UUID.randomUUID(), 10.124);

        //Assert
        assertEquals(10.12, vehicleToRoundDown.getTotalMileage());
    }

    @Test
    @DisplayName("Deve acumular a distância percorrida corretamente após múltiplas acelerações")
    void shouldAccumulateDistanceOverMultipleMoves() {
        // Act
        for (int i = 0; i < 5; i++) {
            vehicleState.accelerateAndMove();
        }

        // Assert
        assertTrue(vehicleState.getTotalKm() > initialKm);
        assertTrue(vehicleState.getCurrentSpeed() >= 40 && vehicleState.getCurrentSpeed() <= 140);
    }
}
