package com.example.sights.repository;

import com.example.sights.config.TestAppConfig;
import com.example.sights.model.Sight;
import com.example.sights.model.SightType;
import com.example.sights.model.Town;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestAppConfig.class })
@Transactional
public class SightRepositoryIntegrationTest {

    @Autowired
    private SightRepository sightRepository;

    @Autowired
    private TownRepository townRepository;

    private Town athens;
    private Town tokyo;

    @Before
    public void setUp() {
        sightRepository.deleteAll();
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
    public void testSaveAndFindAthensSight() {
        Sight acropolis = new Sight();
        acropolis.setName("Acropolis of Athens");
        acropolis.setDescription("Ancient citadel containing remains of several ancient buildings");
        acropolis.setType(SightType.ARCHAEOLOGICAL_SITE);
        acropolis.setDateCreated(LocalDate.of(-500, 1, 1));
        acropolis.setTown(athens);

        sightRepository.save(acropolis);

        List<Sight> sights = sightRepository.findAll();
        assertNotNull(sights);
        assertEquals(1, sights.size());

        Sight retrievedSight = sights.get(0);
        assertEquals("Acropolis of Athens", retrievedSight.getName());
        assertEquals("Ancient citadel containing remains of several ancient buildings",
                retrievedSight.getDescription());
        assertEquals(SightType.ARCHAEOLOGICAL_SITE, retrievedSight.getType());
        assertEquals(athens.getId(), retrievedSight.getTown().getId());
    }

    @Test
    public void testSaveAndFindTokyoSight() {
        Sight tokyoTower = new Sight();
        tokyoTower.setName("Tokyo Tower");
        tokyoTower.setDescription("Communications and observation tower in the Shiba-koen district");
        tokyoTower.setType(SightType.PALACE);
        tokyoTower.setDateCreated(LocalDate.of(1958, 12, 23));
        tokyoTower.setTown(tokyo);

        sightRepository.save(tokyoTower);

        List<Sight> sights = sightRepository.findAll();
        assertNotNull(sights);
        assertEquals(1, sights.size());

        Sight retrievedSight = sights.get(0);
        assertEquals("Tokyo Tower", retrievedSight.getName());
        assertEquals("Communications and observation tower in the Shiba-koen district",
                retrievedSight.getDescription());
        assertEquals(SightType.PALACE, retrievedSight.getType());
        assertEquals(tokyo.getId(), retrievedSight.getTown().getId());
    }
}
