package com.kronsoft.pharma.country;

import com.kronsoft.pharma.city.City;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "city", uniqueConstraints = @UniqueConstraint(columnNames = {"id", "name"}))
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Integer id;

    @NotNull
    private String name;

    @OneToMany
    @NotNull
    private List<City> cities;
}
