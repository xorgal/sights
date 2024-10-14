package com.example.sights.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.sights.dto.SightDto;
import com.example.sights.exception.NotFoundException;
import com.example.sights.mapper.SightMapper;
import com.example.sights.model.Sight;
import com.example.sights.model.Town;
import com.example.sights.repository.SightRepository;
import com.example.sights.repository.TownRepository;

public class SightServiceTest {

    @Mock
    private SightRepository sightRepository;

    @Mock
    private TownRepository townRepository;

    private SightMapper sightMapper;

    private SightService sightService;

    private Sight sight;
    private Town town;
    private SightDto sightDto;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize the mapper
        sightMapper = Mappers.getMapper(SightMapper.class);

        // Initialize the service with mocked repositories and mapper
        sightService = new SightService(sightRepository, sightMapper, townRepository);

        // Initialize test data
        town = new Town();
        town.setId(1L);

        sight = new Sight();
        sight.setId(1L);
        sight.setTown(town);

        sightDto = SightDto.builder()
                .id(1L)
                .townId(1L)
                .description("Old description")
                .build();
    }

    @Test
    public void testAddSight() {
        when(townRepository.findById(anyLong())).thenReturn(Optional.of(town));
        when(sightRepository.save(any(Sight.class))).thenReturn(sight);

        SightDto result = sightService.addSight(sightDto);

        assertNotNull(result);
        verify(sightRepository, times(1)).save(any(Sight.class));
    }

    @Test
    public void testDeleteSight() {
        when(sightRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(sightRepository).deleteById(anyLong());

        sightService.deleteSight(1L);

        verify(sightRepository, times(1)).deleteById(1L);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteSight_NotFound() {
        when(sightRepository.existsById(anyLong())).thenReturn(false);

        sightService.deleteSight(999L);
    }

    @Test
    public void testUpdateSightDescription() {
        when(sightRepository.findById(anyLong())).thenReturn(Optional.of(sight));
        when(sightRepository.save(any(Sight.class))).thenReturn(sight);

        SightDto result = sightService.updateSightDescription(1L, "Updated description");

        assertNotNull(result);
        assertEquals("Updated description", result.getDescription());
        verify(sightRepository, times(1)).save(any(Sight.class));
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateSightDescription_NotFound() {
        when(sightRepository.findById(anyLong())).thenReturn(Optional.empty());

        sightService.updateSightDescription(999L, "Updated description");
    }
}