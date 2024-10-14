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

import com.example.sights.dto.TownDto;
import com.example.sights.service.TownService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/towns")
@Tag(name = "Sights", description = "Operations related to towns")
public class TownController {

    @Autowired
    private TownService townService;

    @GetMapping
    @Operation(summary = "Get all towns", description = "Returns a list of all towns")
    public List<TownDto> getAllTowns() {
        return townService.getAllTowns();
    }

    @PostMapping
    @Operation(summary = "Add a new town", description = "Adds a new town to the database")
    public TownDto addTown(@Parameter(name = "Town data", required = true) @RequestBody TownDto townDto) {
        return townService.addTown(townDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update town", description = "Updates the population and metro status of an existing town")
    public TownDto updateTown(
            @Parameter(name = "ID of the town to update", required = true) @PathVariable Long id,
            @Parameter(name = "New population", required = true) @RequestParam int population,
            @Parameter(name = "Has metro", required = true) @RequestParam boolean hasMetro) {
        return townService.updateTown(id, population, hasMetro);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a town", description = "Deletes an existing town by ID")
    public void deleteTown(@Parameter(name = "ID of the town to delete", required = true) @PathVariable Long id) {
        townService.deleteTown(id);
    }
}
