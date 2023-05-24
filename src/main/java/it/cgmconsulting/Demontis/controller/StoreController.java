package it.cgmconsulting.Demontis.controller;

import it.cgmconsulting.Demontis.entity.Inventory;
import it.cgmconsulting.Demontis.entity.Store;
import it.cgmconsulting.Demontis.entity.Film;
import it.cgmconsulting.Demontis.repository.FilmRepository;
import it.cgmconsulting.Demontis.repository.StoreRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreRepository storeRepository;
    private final FilmRepository filmRepository;

    @Autowired
    public StoreController(StoreRepository storeRepository, FilmRepository filmRepository) {
        this.storeRepository = storeRepository;
        this.filmRepository = filmRepository;
    }

    @PutMapping("/add-film-to-store/{storeId}/{filmId}")
    public ResponseEntity<?> addFilmToStore(@PathVariable Long storeId, @PathVariable Long filmId) {
        Optional<Store> storeOptional = storeRepository.findById(storeId);
        Optional<Film> filmOptional = filmRepository.findById(filmId);

        if (storeOptional.isEmpty() || filmOptional.isEmpty()) {
            return  new ResponseEntity("not found",HttpStatus.NOT_FOUND);
        }

        Store store = storeOptional.get();
        Film film = filmOptional.get();

        store.addFilm(film);
        storeRepository.save(store);

        return new ResponseEntity("film ok",HttpStatus.OK);
    }


}