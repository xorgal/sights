package com.example.sights.repository;

import com.example.sights.config.TestAppConfig;
import com.example.sights.model.Town;

import jakarta.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestAppConfig.class })
@Transactional
public class TownRepositoryIntegrationTest {

    @Autowired
    private TownRepository townRepository;

    private Town athens;
    private Town tokyo;

    @Before
    public void setUp() {
        townRepository.deleteAll();

        athens = new Town();
        athens.setName("Athens");
        athens.setPopulation(664046);
        athens.setHasMetro(true);
        townRepository.save(athens);

        tokyo = new Town();
        tokyo.setName("Tokyo");
        tokyo.setPopulation(13929286);
        tokyo.setHasMetro(true);
        townRepository.save(tokyo);
    }

    @Test
    public void testFindAllTowns() {
        List<Town> towns = townRepository.findAll();
        assertNotNull(towns);
        assertEquals(2, towns.size());
    }

    @Test
    public void testFindAthens() {
        Town retrievedTown = townRepository.findById(athens.getId()).orElse(null);
        assertNotNull(retrievedTown);
        assertEquals("Athens", retrievedTown.getName());
        assertEquals(664046, retrievedTown.getPopulation());
        assertTrue(retrievedTown.isHasMetro());
    }

    @Test
    public void testFindTokyo() {
        Town retrievedTown = townRepository.findById(tokyo.getId()).orElse(null);
        assertNotNull(retrievedTown);
        assertEquals("Tokyo", retrievedTown.getName());
        assertEquals(13929286, retrievedTown.getPopulation());
        assertTrue(retrievedTown.isHasMetro());
    }
}
