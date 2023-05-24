package it.cgmconsulting.Demontis.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class FilmResponse {
    private long filmId;
    private String title;
    private String description;
    private int releaseYear;
    private String languageName;

    public FilmResponse(Long filmId, String title, String description) {
    }


    public void setFilmId(Long filmId) {
    }


}
