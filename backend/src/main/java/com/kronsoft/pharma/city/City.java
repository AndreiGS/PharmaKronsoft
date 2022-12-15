package com.kronsoft.pharma.city;

import com.kronsoft.pharma.country.Country;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "city", uniqueConstraints = @UniqueConstraint(columnNames = {"id", "name"}))
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class City {
    @Id
    @NotNull
    private Integer id;

    @Column(nullable = false)
    @NotNull
    private String name;

    @ManyToOne
    @NotNull
    private Country country;
}
