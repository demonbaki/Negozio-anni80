package it.cgmconsulting.Demontis.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable @EqualsAndHashCode
public class FilmStaffId implements Serializable {
    @Column(name = "film_id")
    private Long filmId;

    @Column(name = "staff_id")
    private Long staffId;

    @Column(name = "role_id")
    private Long roleId;
}