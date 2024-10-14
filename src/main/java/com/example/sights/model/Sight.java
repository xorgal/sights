package com.example.sights.model;

import java.time.LocalDate;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
@Schema(description = "Sight")
public class Sight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique ID of the sight")
    private Long id;

    @Schema(description = "Name of the sight")
    private String name;

    @Schema(description = "Date when the sight was created")
    private LocalDate dateCreated;

    @Schema(description = "Description of the sight")
    private String description;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Type of the sight")
    private SightType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "town_id")
    @Schema(description = "Town where the sight is located")
    private Town town;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "sight_service", joinColumns = @JoinColumn(name = "sight_id"), inverseJoinColumns = @JoinColumn(name = "service_id"))
    @Schema(description = "Services available at the sight")
    private List<Service> services;
}
