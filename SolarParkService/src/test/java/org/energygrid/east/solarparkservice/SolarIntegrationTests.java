package org.energygrid.east.solarparkservice;

import org.energygrid.east.solarparkservice.controller.SolarParkController;
import org.energygrid.east.solarparkservice.model.SolarPark;
import org.energygrid.east.solarparkservice.rabbit.RabbitWeatherListener;
import org.energygrid.east.solarparkservice.service.ISolarParkPower;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SolarIntegrationTests {

    @InjectMocks
    private SolarParkController SolarParkController;

    @Mock
    private ISolarParkPower solarParkPower;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(SolarParkController).build();
    }

    @Test
    public void returnOkGetSolarPark() throws Exception {

        //make fake solarpark
        final SolarPark solarPark = new SolarPark();
        solarPark.setSolarParkName("test");
        solarPark.setSolarParkId(UUID.randomUUID());

        // Give data back if method gets called
        when(solarParkPower.doesNameExist(solarPark.getSolarParkName())).thenReturn(true);
        when(solarParkPower.getSolarParkByName(solarPark.getSolarParkName())).thenReturn(solarPark);

        mockMvc.perform(get("/solarpark").param("name", "test"))
                .andExpect(status().isOk());
    }

    @Test
    public void returnBadRequestGetSolarPark() throws Exception {

        when(solarParkPower.doesNameExist("false")).thenReturn(false);

        mockMvc.perform(get("/solarpark").param("id", "999"))
                .andExpect(status().isBadRequest());
    }

//    @Test
//    public void returnOkRequestPostSolarPark() throws Exception {
//
//        mockMvc.perform(post("/solarpark").param("totalsonarpanels", "222").param("name", "test"))
//                .andExpect(status().isCreated());
//    }

    @Test
    public void returnBadRequestPostSolarPark() throws Exception {

        mockMvc.perform(post("/solarpark").param("totalsonarpanels", "222").param("name", (String) null))
                .andExpect(status().isBadRequest());
    }
}
