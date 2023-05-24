package it.cgmconsulting.Demontis.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FilmRentableResponse {
    private String title;
    private String storeName;
    private Long totalCopies;
    private Long availableCopies;

    public FilmRentableResponse(String title, String storeName, long totalCopies, long availableCopies) {
        this.title = title;
        this.storeName = storeName;
        this.totalCopies =  totalCopies;
        this.availableCopies = availableCopies;
    }

}