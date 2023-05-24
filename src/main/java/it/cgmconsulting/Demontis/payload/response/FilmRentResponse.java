package it.cgmconsulting.Demontis.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FilmRentResponse {

    private long filmId;
    private String title;
    private String StoreName;
    public FilmRentResponse(Long filmId, String title, String storeName) {
    }
}
