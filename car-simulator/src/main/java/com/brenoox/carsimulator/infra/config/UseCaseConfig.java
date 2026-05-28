package com.brenoox.carsimulator.infra.config;

import com.brenoox.carsimulator.core.gateway.TelemetryPublisherGateway;
import com.brenoox.carsimulator.core.usecase.GenerateTelemtryUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public GenerateTelemtryUseCase generateTelemtryUseCase(TelemetryPublisherGateway telemetryPublisherGateway) {
        return new GenerateTelemtryUseCase(telemetryPublisherGateway);
    }
}
