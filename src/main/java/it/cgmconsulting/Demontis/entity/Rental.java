package it.cgmconsulting.Demontis.entity;

import it.cgmconsulting.Demontis.entity.Customer;
import it.cgmconsulting.Demontis.entity.Inventory;
import it.cgmconsulting.Demontis.entity.common.RentalId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity @Getter @Setter
public class Rental {
    @EmbeddedId
    private RentalId id;

    @ManyToOne
    @MapsId("customerId")
    @JoinColumn(name = "customer_id")
    private Customer customer;


    @ManyToOne
    @MapsId("inventoryId")
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    @Column(name = "rental_date", nullable = false)
    private LocalDateTime rentalDate;

    @Column(name = "rental_return")
    private LocalDateTime rentalReturn;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(name = "film_id")
    private Long filmId;


}