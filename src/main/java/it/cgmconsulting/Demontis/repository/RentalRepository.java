package it.cgmconsulting.Demontis.repository;

import it.cgmconsulting.Demontis.entity.Customer;
import it.cgmconsulting.Demontis.entity.Rental;
import it.cgmconsulting.Demontis.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    int countByStoreAndRentalDateBetween(Store store, Date startDate, LocalDate endDate);
    boolean existsByFilmIdAndRentalReturnIsNull(Long filmId);
    List<Rental> findByCustomer(Customer customer);
}
