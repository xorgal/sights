package com.example.sights.controller;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.sights.dto.TownDto;
import com.example.sights.exception.GlobalExceptionHandler;
import com.example.sights.exception.NotFoundException;
import com.example.sights.service.TownService;

public class TownControllerTest {

        @Mock
        private TownService townService;

        @InjectMocks
        private TownController townController;

        private MockMvc mockMvc;

        @Before
        public void setUp() {
                MockitoAnnotations.openMocks(this);

                mockMvc = MockMvcBuilders.standaloneSetup(townController)
                                .setControllerAdvice(new GlobalExceptionHandler())
                                .build();
        }

        @Test
        public void testAddTown() throws Exception {
                TownDto savedTownDto = TownDto.builder()
                                .id(3L)
                                .name("New Town")
                                .population(80000)
                                .hasMetro(false)
                                .build();

                when(townService.addTown(savedTownDto)).thenReturn(savedTownDto);

                String townJson = "{ \"name\": \"New Town\", \"population\": 80000, \"hasMetro\": false }";

                mockMvc.perform(post("/towns")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(townJson))
                                .andExpect(status().isOk());
        }

        @Test
        public void testUpdateTown() throws Exception {
                // Using builder to create TownDto instance
                TownDto updatedTownDto = TownDto.builder()
                                .id(1L)
                                .name("Town 1")
                                .population(200000)
                                .hasMetro(true)
                                .build();

                when(townService.updateTown(anyLong(), anyInt(), anyBoolean()))
                                .thenReturn(updatedTownDto);

                mockMvc.perform(MockMvcRequestBuilders.put("/towns/1")
                                .param("population", "200000")
                                .param("hasMetro", "true"))
                                .andExpect(status().isOk());
        }

        @Test
        public void testUpdateTown_NotFound() throws Exception {
                when(townService.updateTown(anyLong(), anyInt(), anyBoolean()))
                                .thenThrow(new NotFoundException("Town not found"));

                mockMvc.perform(put("/towns/999")
                                .param("population", "200000")
                                .param("hasMetro", "true"))
                                .andExpect(status().isNotFound());
        }

        @Test
        public void testDeleteTown() throws Exception {
                doNothing().when(townService).deleteTown(anyLong());

                mockMvc.perform(delete("/towns/1"))
                                .andExpect(status().isOk());

                verify(townService, times(1)).deleteTown(anyLong());
        }

        @Test
        public void testDeleteTown_NotFound() throws Exception {
                doThrow(new NotFoundException("Town not found")).when(townService).deleteTown(anyLong());

                mockMvc.perform(delete("/towns/999"))
                                .andExpect(status().isNotFound());
        }

        @Test
        public void testGetAllTowns() throws Exception {
                // Arrange
                TownDto town1 = TownDto.builder()
                                .id(1L)
                                .name("Town 1")
                                .population(100000)
                                .hasMetro(true)
                                .build();

                TownDto town2 = TownDto.builder()
                                .id(2L)
                                .name("Town 2")
                                .population(50000)
                                .hasMetro(false)
                                .build();

                List<TownDto> towns = Arrays.asList(town1, town2);

                when(townService.getAllTowns()).thenReturn(towns);

                mockMvc.perform(get("/towns"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].id").value(town1.getId()))
                                .andExpect(jsonPath("$[0].name").value(town1.getName()))
                                .andExpect(jsonPath("$[0].population").value(town1.getPopulation()))
                                .andExpect(jsonPath("$[0].hasMetro").value(town1.isHasMetro()))
                                .andExpect(jsonPath("$[1].id").value(town2.getId()))
                                .andExpect(jsonPath("$[1].name").value(town2.getName()))
                                .andExpect(jsonPath("$[1].population").value(town2.getPopulation()))
                                .andExpect(jsonPath("$[1].hasMetro").value(town2.isHasMetro()));
        }
}
