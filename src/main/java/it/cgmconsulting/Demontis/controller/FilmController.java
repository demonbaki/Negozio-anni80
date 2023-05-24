package it.cgmconsulting.Demontis.controller;

import it.cgmconsulting.Demontis.entity.Film;
import it.cgmconsulting.Demontis.entity.Genre;
import it.cgmconsulting.Demontis.entity.Language;
import it.cgmconsulting.Demontis.entity.Store;
import it.cgmconsulting.Demontis.payload.request.FilmRequest;
import it.cgmconsulting.Demontis.payload.response.FilmMaxRentResponse;
import it.cgmconsulting.Demontis.payload.response.FilmRentableResponse;
import it.cgmconsulting.Demontis.payload.response.FilmResponse;
import it.cgmconsulting.Demontis.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmRepository filmRepository;
    private final LanguageRepository languageRepository;
    private final GenreRepository genreRepository;

    private final InventoryRepository inventoryRepository;


    public FilmController(FilmRepository filmRepository, LanguageRepository languageRepository, GenreRepository genreRepository, InventoryRepository inventoryRepository) {
        this.filmRepository = filmRepository;
        this.languageRepository = languageRepository;
        this.genreRepository = genreRepository;

        this.inventoryRepository = inventoryRepository;
    }
    @PatchMapping("/update-film/{filmId}")
    public ResponseEntity<?> updateFilm(@PathVariable Long filmId, @RequestBody FilmRequest filmRequest) {
        Optional<Film> optionalFilm = filmRepository.findById(filmId);

        if (optionalFilm.isEmpty()) {
            return new  ResponseEntity("film non trovato",HttpStatus.BAD_REQUEST);
        }

        Film film = optionalFilm.get();

        // Aggiorna le proprietà del film con i valori forniti nel filmRequest
        film.setTitle(filmRequest.getTitle());
        film.setDescription(filmRequest.getDescription());
        // Aggiungi altre proprietà che desideri aggiornare

        // Salva il film aggiornato nel repository
        filmRepository.save(film);

        return new ResponseEntity("post aggiornato con successo",HttpStatus.OK);
    }



    @GetMapping("/find-films-by-language/{languageId}")
    public ResponseEntity<List<FilmResponse>> findFilmsByLanguageAndGenres(@PathVariable Long languageId) {
        Optional<Language> languageOptional = languageRepository.findById(languageId);

        if (languageOptional.isEmpty()) {
            return new ResponseEntity("non valida",HttpStatus.BAD_REQUEST);
        }

        Language language = languageOptional.get();

        List<Genre> genres = genreRepository.findAll();

        if (genres.isEmpty()) {
            return new ResponseEntity("non valida",HttpStatus.BAD_REQUEST);
        }

        List<Film> films = filmRepository.findByLanguageAndGenres(language, genres);
        List<FilmResponse> filmResponses = convertToFilmResponses(films);

        return new ResponseEntity("valida",HttpStatus.OK);
    }

    private List<FilmResponse> convertToFilmResponses(List<Film> films) {
        List<FilmResponse> filmResponses = new ArrayList<>();

        for (Film film : films) {
            FilmResponse filmResponse = new FilmResponse();
            filmResponse.setFilmId(film.getFilmId());
            filmResponse.setTitle(film.getTitle());
            filmResponse.setDescription(film.getDescription());
            filmResponse.setReleaseYear(film.getReleaseYear());
            filmResponse.setLanguageName(film.getLanguage().getLanguageName());

            filmResponses.add(filmResponse);
        }

        return filmResponses;
    }

    @GetMapping("/find-film-with-max-number-of-rent")
    public ResponseEntity<List<FilmMaxRentResponse>> findFilmWithMaxNumberOfRent() {
        List<Object[]> results = filmRepository.findFilmWithMaxNumberOfRent();

        List<FilmMaxRentResponse> filmResponses = new ArrayList<>();

        for (Object[] result : results) {
            Long filmId = Long.parseLong(result[0].toString());
            String title = (String) result[1];
            Long numberOfRentals = Long.parseLong(result[2].toString());

            FilmMaxRentResponse filmResponse = new FilmMaxRentResponse(filmId, title, numberOfRentals);
            filmResponses.add(filmResponse);
        }

        return new  ResponseEntity(filmResponses,HttpStatus.OK);
    }

    @GetMapping("/find-films-by-actors")
    public ResponseEntity<List<FilmResponse>> findFilmsByActors(@RequestParam("actorIds") Collection<Long> actorIds) {
        List<Film> films = filmRepository.findFilmsByActors(actorIds);
        List<FilmResponse> filmResponses = mapToFilmResponses(films);

          return new ResponseEntity(filmResponses,HttpStatus.OK);
    }

    private List<FilmResponse> mapToFilmResponses(List<Film> films) {
        List<FilmResponse> filmResponses = new ArrayList<>();
        for (Film film : films) {
            FilmResponse filmResponse = new FilmResponse(film.getFilmId(), film.getTitle(), film.getDescription());
            filmResponses.add(filmResponse);
        }
        return filmResponses;
    }

    @GetMapping("/find-rentable-films")
    public List<FilmRentableResponse> findRentableFilms(@RequestParam String title) {
        List<Film> films = filmRepository.findByTitle(title);
        List<FilmRentableResponse> filmResponses = new ArrayList<>();

        for (Film film : films) {
            Store store = film.getStore();
            int totalCopies = inventoryRepository.countByFilmAndStore(film, store);
            int availableCopies = inventoryRepository.countAvailableByFilmAndStore(film, store);

            FilmRentableResponse filmResponse = new FilmRentableResponse(film.getTitle(), store.getStoreName(), totalCopies, availableCopies);
            filmResponses.add(filmResponse);
        }

        return filmResponses;
    }



















}

