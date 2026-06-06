package com.dev.brenoox.billing_processor.infra.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "billing")
public class BillingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID carId;

    @Column(nullable = false)
    private double totalKm;

    @Column(nullable = false)
    private double totalBilling;

    public BillingEntity(
            UUID carId,
            double totalKm,
            double totalBilling)
    {
        this.carId = carId;
        this.totalKm = totalKm;
        this.totalBilling = totalBilling;
    }
}
