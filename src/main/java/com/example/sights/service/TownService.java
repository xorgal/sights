package com.example.sights.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sights.dto.TownDto;
import com.example.sights.exception.NotFoundException;
import com.example.sights.mapper.TownMapper;
import com.example.sights.model.Town;
import com.example.sights.repository.TownRepository;

@Service
public class TownService {

    private String cityNotFoundExceptionText = "Town not found";

    @Autowired
    private TownRepository townRepository;

    @Autowired
    TownMapper townMapper;

    public TownService(TownRepository townRepository, TownMapper townMapper) {
        this.townRepository = townRepository;
        this.townMapper = townMapper;
    }

    public List<TownDto> getAllTowns() {
        return townRepository.findAll().stream().map(townMapper::toDto).collect(Collectors.toList());
    }

    public TownDto addTown(TownDto townDto) {
        return townMapper.toDto(townRepository.save(townMapper.toEntity(townDto)));
    }

    public TownDto updateTown(Long id, int population, boolean hasMetro) {
        Town town = townRepository.findById(id).orElseThrow(() -> new NotFoundException(cityNotFoundExceptionText));
        town.setPopulation(population);
        town.setHasMetro(hasMetro);
        return townMapper.toDto(townRepository.save(town));
    }

    public void deleteTown(Long id) {
        if (!townRepository.existsById(id)) {
            throw new NotFoundException(cityNotFoundExceptionText);
        }
        townRepository.deleteById(id);
    }
}
