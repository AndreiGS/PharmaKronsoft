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
@Table(name = "country", uniqueConstraints = @UniqueConstraint(columnNames = {"id", "name"}))
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Country {
    @Id
    @NotNull
    private Integer id;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "country")
    @NotNull
    private List<City> cities;
}
