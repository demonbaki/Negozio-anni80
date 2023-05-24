package it.cgmconsulting.Demontis.entity;

import it.cgmconsulting.Demontis.entity.Film;
import it.cgmconsulting.Demontis.entity.Staff;
import it.cgmconsulting.Demontis.entity.common.FilmStaffId;
import jakarta.persistence.*;

@Entity
@Table(name = "film_staff")
public class FilmStaff {
    @EmbeddedId
    private FilmStaffId id;

    @ManyToOne
    @MapsId("filmId")
    @JoinColumn(name = "film_id")
    private Film film;

    @ManyToOne
    @MapsId("staffId")
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private Role role;

}