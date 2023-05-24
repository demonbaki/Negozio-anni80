package it.cgmconsulting.Demontis.repository;

import it.cgmconsulting.Demontis.entity.Film;
import it.cgmconsulting.Demontis.entity.Genre;
import it.cgmconsulting.Demontis.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {


    Optional<Film> findById(Long filmId);
    List<Film> findByLanguageId(@Param("languageId") Long languageId);

    @Query("SELECT f FROM Film f WHERE f.language = :language AND f.genre IN :genres")
    List<Film> findByLanguageAndGenres(@Param("language") Language language, @Param("genres") List<Genre> genres);



    @Query(value = "SELECT f.film_id, f.title, COUNT(r.rental_id) " +
            "FROM film f " +
            "LEFT JOIN rental r ON f.film_id = r.film_id " +
            "GROUP BY f.film_id, f.title " +
            "ORDER BY COUNT(r.rental_id) DESC", nativeQuery = true)
    List<Object[]> findFilmWithMaxNumberOfRent();




    @Query("SELECT f FROM Film f INNER JOIN f.actors actor WHERE actor.staffId IN :actorIds GROUP BY f HAVING COUNT(DISTINCT actor.staffId) = :actorCount")
    List<Film> findFilmsByActors(@Param("actorIds") Collection<Long> actorIds);


    List<Film> findByTitle(String title);
}







