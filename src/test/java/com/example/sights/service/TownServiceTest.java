package com.example.sights.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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

import com.example.sights.dto.TownDto;
import com.example.sights.exception.NotFoundException;
import com.example.sights.mapper.TownMapper;
import com.example.sights.model.Town;
import com.example.sights.repository.TownRepository;

public class TownServiceTest {

    @Mock
    private TownRepository townRepository;

    private TownMapper townMapper;

    private TownService townService;

    private Town town;
    private TownDto townDto;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize the mapper
        townMapper = Mappers.getMapper(TownMapper.class);

        // Initialize the service with the mocked repository and mapper
        townService = new TownService(townRepository, townMapper);

        // Initialize test data
        town = new Town();
        town.setId(1L);
        town.setPopulation(100000);

        townDto = TownDto.builder()
                .id(1L)
                .population(100000)
                .build();
    }

    @Test
    public void testAddTown() {
        when(townRepository.save(any(Town.class))).thenReturn(town);

        TownDto result = townService.addTown(townDto);

        assertNotNull(result);
        verify(townRepository, times(1)).save(any(Town.class));
    }

    @Test
    public void testDeleteTown() {
        when(townRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(townRepository).deleteById(anyLong());

        townService.deleteTown(1L);

        verify(townRepository, times(1)).deleteById(1L);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteTown_NotFound() {
        when(townRepository.existsById(anyLong())).thenReturn(false);

        townService.deleteTown(999L);
    }

    @Test
    public void testUpdateTown() {
        when(townRepository.findById(anyLong())).thenReturn(Optional.of(town));
        when(townRepository.save(any(Town.class))).thenReturn(town);

        TownDto result = townService.updateTown(1L, 200000, true);

        assertNotNull(result);
        assertEquals(200000, result.getPopulation());
        assertTrue(result.isHasMetro());
        verify(townRepository, times(1)).save(any(Town.class));
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateTown_NotFound() {
        when(townRepository.findById(anyLong())).thenReturn(Optional.empty());

        townService.updateTown(999L, 200000, true);
    }
}
