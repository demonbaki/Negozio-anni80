package it.cgmconsulting.Demontis.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FilmMaxRentResponse {

    private long filmId;
    private String title;
    private long  numberOfRentals;
    public FilmMaxRentResponse(Long filmId, String title, Long numberOfRentals) {
    }
}
