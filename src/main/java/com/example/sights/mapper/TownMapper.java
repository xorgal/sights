package com.example.sights.mapper;

import org.mapstruct.Mapper;

import com.example.sights.dto.TownDto;
import com.example.sights.model.Town;

@Mapper(componentModel = "spring", uses = { SightMapper.class })
public interface TownMapper {

    TownDto toDto(Town town);

    Town toEntity(TownDto townDto);
}
