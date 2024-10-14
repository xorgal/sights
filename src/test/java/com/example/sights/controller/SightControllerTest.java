package com.example.sights.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.sights.dto.SightDto;
import com.example.sights.dto.TownDto;
import com.example.sights.exception.GlobalExceptionHandler;
import com.example.sights.exception.NotFoundException;
import com.example.sights.service.SightService;
import com.example.sights.service.TownService;

public class SightControllerTest {

        @Mock
        TownService townService;

        @Mock
        private SightService sightService;

        @InjectMocks
        private SightController sightController;

        private MockMvc mockMvc;

        @Before
        public void setUp() {
                MockitoAnnotations.openMocks(this);

                mockMvc = MockMvcBuilders.standaloneSetup(sightController)
                                .setControllerAdvice(new GlobalExceptionHandler())
                                .build();
        }

        @Test
        public void testAddSight() throws Exception {
                TownDto savedTownDto = TownDto.builder()
                                .id(1L)
                                .name("New Town")
                                .population(80000)
                                .hasMetro(false)
                                .build();

                SightDto savedSightDto = SightDto.builder()
                                .id(1L)
                                .name("New Sight")
                                .dateCreated(LocalDate.of(2022, 1, 1))
                                .description("New Description")
                                .type("MUSEUM")
                                .townId(1L)
                                .build();

                when(townService.addTown(savedTownDto)).thenReturn(savedTownDto);
                when(sightService.addSight(savedSightDto)).thenReturn(savedSightDto);

                String sightJson = "{ \"name\": \"New Sight\", \"dateCreated\": \"2022-01-01\", \"description\": \"New Description\", \"type\": \"MUSEUM\", \"townId\": 1 }";

                mockMvc.perform(post("/sights")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(sightJson))
                                .andExpect(status().isOk());
        }

        @Test
        public void testUpdateSightDescription() throws Exception {
                // Using builder to create SightDto instance
                SightDto updatedSightDto = SightDto.builder()
                                .id(1L)
                                .description("Updated description")
                                .build();

                when(sightService.updateSightDescription(anyLong(), any(String.class)))
                                .thenReturn(updatedSightDto);

                mockMvc.perform(put("/sights/1")
                                .param("description", "Updated description"))
                                .andExpect(status().isOk());
        }

        @Test
        public void testUpdateSightDescription_NotFound() throws Exception {
                when(sightService.updateSightDescription(anyLong(), any(String.class)))
                                .thenThrow(new NotFoundException("Sight not found"));

                mockMvc.perform(put("/sights/999")
                                .param("description", "Updated description"))
                                .andExpect(status().isNotFound());
        }

        @Test
        public void testDeleteSight() throws Exception {
                doNothing().when(sightService).deleteSight(anyLong());

                mockMvc.perform(delete("/sights/1"))
                                .andExpect(status().isOk());

                verify(sightService, times(1)).deleteSight(anyLong());
        }

        @Test
        public void testDeleteSight_NotFound() throws Exception {
                doThrow(new NotFoundException("Sight not found")).when(sightService).deleteSight(anyLong());

                mockMvc.perform(delete("/sights/999"))
                                .andExpect(status().isNotFound());
        }

        @Test
        public void testGetAllSights() throws Exception {

                SightDto sight1 = SightDto.builder()
                                .id(1L)
                                .name("Sight 1")
                                .description("Description 1")
                                .type("PARK")
                                .townId(1L)
                                .build();

                SightDto sight2 = SightDto.builder()
                                .id(2L)
                                .name("Sight 2")
                                .description("Description 2")
                                .type("MUSEUM")
                                .townId(2L)
                                .build();

                List<SightDto> sights = Arrays.asList(sight1, sight2);

                when(sightService.getAllSights()).thenReturn(sights);

                mockMvc.perform(get("/sights"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].id").value(sight1.getId()))
                                .andExpect(jsonPath("$[0].name").value(sight1.getName()))
                                .andExpect(jsonPath("$[0].description").value(sight1.getDescription()))
                                .andExpect(jsonPath("$[0].type").value(sight1.getType()))
                                .andExpect(jsonPath("$[0].townId").value(sight1.getTownId()))
                                .andExpect(jsonPath("$[1].id").value(sight2.getId()))
                                .andExpect(jsonPath("$[1].name").value(sight2.getName()))
                                .andExpect(jsonPath("$[1].description").value(sight2.getDescription()))
                                .andExpect(jsonPath("$[1].type").value(sight2.getType()))
                                .andExpect(jsonPath("$[1].townId").value(sight2.getTownId()));
        }
}
