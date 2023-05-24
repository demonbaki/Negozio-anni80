package it.cgmconsulting.Demontis.repository;

import it.cgmconsulting.Demontis.entity.Film;
import it.cgmconsulting.Demontis.entity.Inventory;
import it.cgmconsulting.Demontis.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    int countByFilmAndStore(Film film, Store store);

    int countAvailableByFilmAndStore(Film film, Store store);
}
