package com.example.sights.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sights.model.Town;

public interface TownRepository extends JpaRepository<Town, Long> {
}
