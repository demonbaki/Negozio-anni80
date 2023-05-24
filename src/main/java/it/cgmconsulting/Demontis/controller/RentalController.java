package it.cgmconsulting.Demontis.controller;
import it.cgmconsulting.Demontis.entity.Customer;
import it.cgmconsulting.Demontis.entity.Store;
import it.cgmconsulting.Demontis.entity.Film;
import it.cgmconsulting.Demontis.entity.Rental;
import it.cgmconsulting.Demontis.payload.response.FilmRentResponse;
import it.cgmconsulting.Demontis.repository.CustomerRepository;
import it.cgmconsulting.Demontis.repository.FilmRepository;
import it.cgmconsulting.Demontis.repository.RentalRepository;
import it.cgmconsulting.Demontis.repository.StoreRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class RentalController {

    private final RentalRepository rentalRepository;
    private final FilmRepository filmRepository;
    private final StoreRepository storeRepository;
    private final CustomerRepository customerRepository;

    public RentalController(RentalRepository rentalRepository, FilmRepository filmRepository, StoreRepository storeRepository, CustomerRepository customerRepository) {
        this.rentalRepository = rentalRepository;
        this.filmRepository = filmRepository;
        this.storeRepository = storeRepository;
        this.customerRepository = customerRepository;
    }


        @PutMapping("/add-update-rental")
        public ResponseEntity<String> addOrUpdateRental(@RequestBody Rental rental) {
            // Verifica se il film è noleggiabile
            Long filmId = rental.getFilmId();
            if (filmId == null) {
                return new ResponseEntity("Film ID is required.",HttpStatus.BAD_REQUEST);
            }

            // Recupera il film dal repository
            Optional<Film> filmOptional = filmRepository.findById(filmId);
            if (filmOptional.isEmpty()) {
                return new  ResponseEntity("Film not found.",HttpStatus.NOT_FOUND);
            }
            Film film = filmOptional.get();

            // Verifica se il film è presente in uno store
            Optional<Store> storeOptional = storeRepository.findByFilmsContaining(film);
            if (storeOptional.isEmpty()) {
                return new ResponseEntity("Film is not available in any store.", HttpStatus.BAD_REQUEST);
            }

            // Verifica se il film è già noleggiato
            boolean isFilmRented = rentalRepository.existsByFilmIdAndRentalReturnIsNull(filmId);
            if (isFilmRented) {
                return new ResponseEntity("Film is already rented.",HttpStatus.BAD_REQUEST);
            }

            // Aggiungi o aggiorna il noleggio
            Rental savedRental = rentalRepository.save(rental);
            return new ResponseEntity("Rental added/updated successfully.",HttpStatus.OK);
        }

    @GetMapping("/count-rentals-in-date-range-by-store/{storeId}")
    public ResponseEntity<Integer> countRentalsInDateRangeByStore
            (@PathVariable Long storeId,
             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

        Optional<Store> storeOptional = storeRepository.findById(storeId);

        if (storeOptional.isEmpty()) {
            return new ResponseEntity("not found",HttpStatus.NOT_FOUND);
        }

        Store store = storeOptional.get();
        int rentalCount = rentalRepository.countByStoreAndRentalDateBetween(store, start, end);

        return new  ResponseEntity(rentalCount,HttpStatus.OK);
    }

    @GetMapping("/find-all-films-rent-by-one-customer/{customerId}")
    public ResponseEntity<List<FilmRentResponse>> findAllFilmsRentByOneCustomer(@PathVariable Long customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if (customerOptional.isEmpty()) {
            return new  ResponseEntity("not found",HttpStatus.NOT_FOUND);
        }

        Customer customer = customerOptional.get();
        List<Rental> rentals = rentalRepository.findByCustomer(customer);

        List<FilmRentResponse> filmRentResponses = new ArrayList<>();
        for (Rental rental : rentals) {
            Long filmId = rental.getFilmId();
            Optional<Film> filmOptional = filmRepository.findById(filmId);
            if (filmOptional.isPresent()) {
                Film film = filmOptional.get();
                String storeName = rental.getStore().getStoreName();
                FilmRentResponse filmRentResponse = new FilmRentResponse(filmId, film.getTitle(), storeName);
                filmRentResponses.add(filmRentResponse);
            }
        }

        return new ResponseEntity(filmRentResponses,HttpStatus.OK);
    }









}


