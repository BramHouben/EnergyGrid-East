package org.energygrid.east.solarparkservice;

import org.energygrid.east.solarparkservice.model.SolarPark;
import org.energygrid.east.solarparkservice.model.dto.AddSolarParkDTO;
import org.energygrid.east.solarparkservice.repo.ISolarParkRepo;
import org.energygrid.east.solarparkservice.service.SolarPowerService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class SolarParkTests {

    @InjectMocks
    private SolarPowerService solarPowerService;

    @Mock
    private ISolarParkRepo iSolarParkRepo;

    @Test
    void isSolarParkNull() {
        SolarPark solarPark = new SolarPark();
        assertNotNull(solarPark);
    }

    @Test
    void createSolarPark() {
        AddSolarParkDTO solarParkdto = new AddSolarParkDTO("test", 10, new Point(1, 1), "test", "5961AM", "Limburg", 12, 1, 2015);
        solarPowerService.addSolarPark(solarParkdto);

        SolarPark solarPark = new SolarPark();
        solarPark.setSolarParkName("test");
        when(iSolarParkRepo.getSolarParkBySolarParkName("test")).thenReturn(solarPark);

        SolarPark solarPark1 = iSolarParkRepo.getSolarParkBySolarParkName("test");
        assertEquals(solarPark.getSolarParkName(), solarPark1.getSolarParkName());

    }
}
