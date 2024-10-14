package com.example.sights.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sights.dto.SightDto;
import com.example.sights.exception.NotFoundException;
import com.example.sights.mapper.SightMapper;
import com.example.sights.model.Sight;
import com.example.sights.model.Town;
import com.example.sights.repository.SightRepository;
import com.example.sights.repository.TownRepository;

@Service
public class SightService {

    private String cityNotFoundExceptionText = "Town not found";
    private String sightNotFoundExceptionText = "Sight not found";

    public SightService(SightRepository sightRepository, SightMapper sightMapper, TownRepository townRepository) {
        this.sightRepository = sightRepository;
        this.sightMapper = sightMapper;
        this.townRepository = townRepository;
    }

    @Autowired
    private SightRepository sightRepository;

    @Autowired
    private TownRepository townRepository;

    @Autowired
    private SightMapper sightMapper;

    public List<SightDto> getAllSights() {
        return sightRepository.findAll().stream().map(sightMapper::toDto).collect(Collectors.toList());
    }

    public SightDto addSight(SightDto sightDto) {
        Town town = townRepository.findById(sightDto.getTownId())
                .orElseThrow(() -> new NotFoundException(cityNotFoundExceptionText));

        Sight sight = sightMapper.toEntity(sightDto);

        sight.setTown(town);

        return sightMapper.toDto(sightRepository.save(sight));
    }

    public SightDto updateSightDescription(Long id, String description) {
        Sight sight = sightRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(sightNotFoundExceptionText));
        sight.setDescription(description);
        return sightMapper.toDto(sightRepository.save(sight));
    }

    public void deleteSight(Long id) {
        if (!sightRepository.existsById(id)) {
            throw new NotFoundException(sightNotFoundExceptionText);
        }
        sightRepository.deleteById(id);
    }
}
