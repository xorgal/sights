package com.example.sights.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Information about the service")
public class ServiceDto {

    @Schema(description = "Unique ID of the service")
    private Long id;

    @Schema(description = "Name of the service")
    private String name;

    @Schema(description = "Description of the service")
    private String description;
}