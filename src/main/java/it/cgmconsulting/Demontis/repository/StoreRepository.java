package it.cgmconsulting.Demontis.repository;

import it.cgmconsulting.Demontis.entity.Film;
import it.cgmconsulting.Demontis.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {


    Store findByStoreName(String storeName);

    Optional<Store> findByFilmsContaining(Film film);
}
