package com.example.sights.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Types of sights")
public enum SightType {
    PALACE,
    PARK,
    MUSEUM,
    ARCHAEOLOGICAL_SITE,
    NATURE_RESERVE
}
