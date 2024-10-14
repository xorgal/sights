package com.example.sights.dto;

import java.time.LocalDate;
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
@Schema(description = "Information about the sight")
public class SightDto {

    @Schema(description = "Unique ID of the sight")
    private Long id;

    @Schema(description = "Name of the sight")
    private String name;

    @Schema(description = "Date when the sight was created")
    private LocalDate dateCreated;

    @Schema(description = "Description of the sight")
    private String description;

    @Schema(description = "Type of the sight")
    private String type;

    @Schema(description = "ID of the town where the sight is located")
    private Long townId;

    @Schema(description = "List of services available at the sight")
    private List<ServiceDto> services;
}
