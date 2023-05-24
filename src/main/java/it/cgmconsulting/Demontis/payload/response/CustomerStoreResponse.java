package it.cgmconsulting.Demontis.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CustomerStoreResponse {
    private String storeName;
    private int totalCustomers;

    public CustomerStoreResponse(String storeName, int totalCustomers) {
        this.storeName = storeName;
        this.totalCustomers = totalCustomers;
    }


}
