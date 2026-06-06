package com.dev.brenoox.billing_processor.infra.gateway;

import com.dev.brenoox.billing_processor.core.dto.SaveBillingDTO;
import com.dev.brenoox.billing_processor.core.gateway.BillingRepositoryGateway;
import com.dev.brenoox.billing_processor.infra.entity.BillingEntity;
import com.dev.brenoox.billing_processor.infra.repository.BillingJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BillingRepositoryGatewayImpl implements BillingRepositoryGateway {

    private final BillingJpaRepository billingJpaRepository;

    public BillingRepositoryGatewayImpl(BillingJpaRepository billingJpaRepository) {
        this.billingJpaRepository = billingJpaRepository;
    }

    @Override
    @Transactional
    public void incrementBilling(SaveBillingDTO saveBillingDTO) {

        BillingEntity billingEntity =
                billingJpaRepository
                        .findByCarId(saveBillingDTO.carId())
                        .orElse(new BillingEntity(saveBillingDTO.carId(), 0.0, 0.0));

        billingEntity.setTotalBilling(
                billingEntity.getTotalBilling() + saveBillingDTO.billing()
        );

        billingEntity.setTotalKm(
                billingEntity.getTotalKm() + saveBillingDTO.kmTraveled()
        );

        billingJpaRepository.save(billingEntity);
    }
}
