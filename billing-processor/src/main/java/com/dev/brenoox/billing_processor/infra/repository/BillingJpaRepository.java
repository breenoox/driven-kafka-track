package com.dev.brenoox.billing_processor.infra.repository;


import com.dev.brenoox.billing_processor.infra.entity.BillingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BillingJpaRepository extends JpaRepository <BillingEntity, Long> {

    @Transactional
    @Modifying
    @Query("""
        UPDATE BillingEntity b
        SET
            b.totalKm = b.totalKm + :km,
            b.totalBilling = b.totalBilling + :billing
        WHERE b.carId = :carId
    """)
    void incrementBilling(
            @Param("carId") UUID carId,
            @Param("km") double km,
            @Param("billing") double billing
    );

    Optional<BillingEntity> findByCarId(UUID carId);
}
