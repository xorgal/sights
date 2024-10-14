package com.example.sights.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Information about the town")
public class TownDto {

    @Schema(description = "Unique ID of the town")
    private Long id;

    @Schema(description = "Name of the town")
    private String name;

    @Schema(description = "Population of the town")
    private int population;

    @Schema(description = "Indicates if the town has a metro")
    private boolean hasMetro;

    @Schema(description = "List of sights in the town")
    private List<SightDto> sights;
}
