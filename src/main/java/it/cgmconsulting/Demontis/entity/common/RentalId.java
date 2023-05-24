package it.cgmconsulting.Demontis.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable @EqualsAndHashCode
public class RentalId implements Serializable {
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "inventory_id")
    private Long inventoryId;


}