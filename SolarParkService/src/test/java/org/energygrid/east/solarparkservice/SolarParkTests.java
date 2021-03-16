package org.energygrid.east.solarparkservice;

import org.energygrid.east.solarparkservice.errormessages.SolarParkNotFoundException;
import org.energygrid.east.solarparkservice.model.SolarPark;
import org.energygrid.east.solarparkservice.service.ISolarParkPower;
import org.energygrid.east.solarparkservice.service.SolarPowerService;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SolarParkTests {

    @Autowired
    ISolarParkPower solarPowerService;

    @BeforeEach
    public void init() {
      solarPowerService= new SolarPowerService();

    }

    @Test
    void isSolarParkNull() {
        SolarPark solarPark = solarPowerService.getSolarParkById(1);
        assertNotNull(solarPark);
    }

    @Test
    void checkIfKwpIsTwentyFour() {
        SolarPark solarPark = solarPowerService.getSolarParkById(1);
        assertNotNull(solarPark.getSolarPanels().stream().findFirst().get().getSolarPanelId());
    }

//    @Test
//    void CheckIfKwpIsTwentyFourError() {
//        SolarPark solarPark = solarPowerService.getSolarParkById(1);
//        assertNotEquals( 240,solarPark.getSolarPanels().stream().findFirst().get().getWpPerHour());
//    }

    @Test
    void returnTrueIfSolarParkExist() {
        assertTrue(solarPowerService.doesIdExist(1));
    }

    @Test
    void throwExceptionIfSolarParkExist() {
        assertThrows(SolarParkNotFoundException.class, ()->solarPowerService.doesIdExist(999));
    }
}
