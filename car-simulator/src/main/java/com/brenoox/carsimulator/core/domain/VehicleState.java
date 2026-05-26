package com.brenoox.carsimulator.core.domain;

import lombok.Getter;

import java.util.Random;
import java.util.UUID;

@Getter
public class VehicleState {
    private UUID carId;
    private int currentSpeed;
    private double totalKm;
    private final Random random = new Random();

    public VehicleState(UUID carId, double initialKm) {
        this.carId = carId;
        this.currentSpeed = 0;
        this.totalKm = initialKm;
    }

    public void accelerateAndMove() {
        this.currentSpeed = 40 + random.nextInt(101);

        double kilometersTraveled = 0.010 + (0.020 * random.nextDouble());
        this.totalKm += kilometersTraveled;
    }

    public double getTotalMileage() {
        return Math.round(this.totalKm * 100.0) / 100.0;
    }
}
