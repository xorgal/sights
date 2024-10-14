package com.example.sights.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Data
@Entity
@Schema(description = "Service available at a sight")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique ID of the service")
    private Long id;

    @Schema(description = "Name of the service")
    private String name;

    @Schema(description = "Description of the service")
    private String description;

    @ManyToMany(mappedBy = "services")
    @Schema(description = "List of sights offering this service")
    private List<Sight> sights;
}
