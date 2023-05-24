package it.cgmconsulting.Demontis.repository;

import it.cgmconsulting.Demontis.entity.Customer;
import it.cgmconsulting.Demontis.entity.Film;
import it.cgmconsulting.Demontis.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {


    int countByRentalsStore(Store store);
}

