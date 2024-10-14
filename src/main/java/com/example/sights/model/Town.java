package com.example.sights.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
@Schema(description = "Town")
public class Town {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique ID of the town")
    private Long id;

    @Schema(description = "Name of the town")
    private String name;

    @Schema(description = "Population of the town")
    private int population;

    @Schema(description = "Indicates if the town has a metro")
    private boolean hasMetro;

    @OneToMany(mappedBy = "town", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Schema(description = "List of sights in the town")
    private List<Sight> sights;
}
