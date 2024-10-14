package com.example.sights.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.sights.dto.ServiceDto;
import com.example.sights.model.Service;

@Mapper(componentModel = "spring")
public interface ServiceMapper {
    ServiceDto toDto(Service service);

    @Mapping(target = "sights", ignore = true)
    Service toEntity(ServiceDto serviceDto);
}
