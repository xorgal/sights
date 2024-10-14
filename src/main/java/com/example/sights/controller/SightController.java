package com.example.sights.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.sights.dto.SightDto;
import com.example.sights.service.SightService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/sights")
@Tag(name = "Sight Controller", description = "Operations pertaining to sights")
public class SightController {

    @Autowired
    private SightService sightService;

    @GetMapping
    @Operation(summary = "Get all sights", description = "Returns a list of all sights")
    public List<SightDto> getAllSights() {
        return sightService.getAllSights();
    }

    @PostMapping
    @Operation(summary = "Add a new sight", description = "Adds a new sight to the database")
    public SightDto addSightDto(@Parameter(name = "Sight data", required = true) @RequestBody SightDto sightDto) {
        return sightService.addSight(sightDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update sight description", description = "Updates the description of an existing sight")
    public SightDto updateSightDescription(
            @Parameter(name = "ID of the sight to update", required = true) @PathVariable Long id,
            @Parameter(name = "New description", required = true) @RequestParam String description) {
        return sightService.updateSightDescription(id, description);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a sight", description = "Deletes an existing sight by ID")
    public void deleteSight(@Parameter(name = "ID of the sight to delete", required = true) @PathVariable Long id) {
        sightService.deleteSight(id);
    }
}
