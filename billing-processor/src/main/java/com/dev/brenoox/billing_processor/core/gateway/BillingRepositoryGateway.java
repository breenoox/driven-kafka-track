package com.dev.brenoox.billing_processor.core.gateway;

import com.dev.brenoox.billing_processor.core.dto.SaveBillingDTO;

public interface BillingRepositoryGateway {
    void incrementBilling (SaveBillingDTO saveBillingDTO);
}
