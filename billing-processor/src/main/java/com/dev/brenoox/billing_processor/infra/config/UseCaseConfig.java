package com.dev.brenoox.billing_processor.infra.config;

import com.dev.brenoox.billing_processor.core.gateway.BillingRepositoryGateway;
import com.dev.brenoox.billing_processor.core.usecase.BillingUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public BillingUseCase billingUseCase(BillingRepositoryGateway billingRepositoryGateway) {
        return new BillingUseCase(billingRepositoryGateway);
    }
}
