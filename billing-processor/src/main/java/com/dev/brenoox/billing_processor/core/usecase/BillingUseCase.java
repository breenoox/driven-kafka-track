package com.dev.brenoox.billing_processor.core.usecase;

import com.dev.brenoox.billing_processor.core.domain.Billing;
import com.dev.brenoox.billing_processor.core.dto.SaveBillingDTO;
import com.dev.brenoox.billing_processor.core.dto.TelemetryDTO;
import com.dev.brenoox.billing_processor.core.gateway.BillingRepositoryGateway;

import java.math.BigDecimal;

public class BillingUseCase {
    private final BillingRepositoryGateway billingRepositoryGateway;

    public BillingUseCase(BillingRepositoryGateway billingRepositoryGateway) {
        this.billingRepositoryGateway = billingRepositoryGateway;
    }

    public void execute(TelemetryDTO telemetryDTO) {
        Billing billing = new Billing(
            telemetryDTO.totalKm(),
            0.5
        );

        BigDecimal totalBilling = billing.calculate();

        SaveBillingDTO saveBillingDTO = new SaveBillingDTO(
                telemetryDTO.carId(),
                telemetryDTO.totalKm(),
                totalBilling.doubleValue()
        );

        billingRepositoryGateway.incrementBilling(saveBillingDTO);
    }
}
