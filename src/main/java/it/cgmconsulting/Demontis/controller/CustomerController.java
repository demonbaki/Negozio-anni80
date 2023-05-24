package it.cgmconsulting.Demontis.controller;

import it.cgmconsulting.Demontis.entity.Store;
import it.cgmconsulting.Demontis.payload.response.CustomerStoreResponse;
import it.cgmconsulting.Demontis.repository.CustomerRepository;
import it.cgmconsulting.Demontis.repository.StoreRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CustomerController {
    private final StoreRepository storeRepository;
    private final CustomerRepository customerRepository;

    public CustomerController(StoreRepository storeRepository, CustomerRepository customerRepository) {
        this.storeRepository = storeRepository;
        this.customerRepository = customerRepository;
    }


    @GetMapping("/count-customers-by-store/{storeName}")
    public ResponseEntity<CustomerStoreResponse> countCustomersByStore(@PathVariable String storeName) {
        Optional<Store> storeOptional = Optional.ofNullable(storeRepository.findByStoreName(storeName));

        if (storeOptional.isEmpty()) {
            return new ResponseEntity("storname not found",HttpStatus.NOT_FOUND);
        }

        Store store = storeOptional.get();
        int totalCustomers = customerRepository.countByRentalsStore(store);

        CustomerStoreResponse response = new CustomerStoreResponse(store.getStoreName(), totalCustomers);
        return new ResponseEntity("count is ok",HttpStatus.OK);
    }


}

