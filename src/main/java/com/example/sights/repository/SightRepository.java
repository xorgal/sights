package com.example.sights.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sights.model.Sight;

public interface SightRepository extends JpaRepository<Sight, Long> {
    List<Sight> findByTownId(Long townId);

    List<Sight> findByType(String type);
}
