package org.energygrid.east.solarparkservice;

import org.energygrid.east.solarparkservice.model.SolarPark;
import org.energygrid.east.solarparkservice.service.ISolarParkPower;
import org.energygrid.east.solarparkservice.service.SolarPowerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SolarParkTests {

    @Autowired
    ISolarParkPower solarPowerService;

    @BeforeEach
    public void init() {
        solarPowerService = new SolarPowerService();
    }

    @Test
    void isSolarParkNull() {

        SolarPark solarPark = new SolarPark(UUID.randomUUID(), "test", 5, Collections.emptyList());

        assertNotNull(solarPark);
    }

}
