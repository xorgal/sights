package com.example.sights.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.sights.dto.SightDto;
import com.example.sights.model.Sight;

@Mapper(componentModel = "spring", uses = { ServiceMapper.class })
public interface SightMapper {

    @Mapping(source = "type", target = "type")
    @Mapping(source = "town.id", target = "townId")
    SightDto toDto(Sight sight);

    @Mapping(target = "town", ignore = true)
    Sight toEntity(SightDto sightDto);
}
