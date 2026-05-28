package com.brenoox.carsimulator.infra.scheduler;

import com.brenoox.carsimulator.core.usecase.GenerateTelemtryUseCase;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class TelemetrySimulationScheduler {
    private final GenerateTelemtryUseCase generateTelemtryUseCase;

    public TelemetrySimulationScheduler(GenerateTelemtryUseCase generateTelemtryUseCase) {
        this.generateTelemtryUseCase = generateTelemtryUseCase;
    }

    @Scheduled(fixedRate = 100)
    public void run() {
        generateTelemtryUseCase.execute();
    }
}
