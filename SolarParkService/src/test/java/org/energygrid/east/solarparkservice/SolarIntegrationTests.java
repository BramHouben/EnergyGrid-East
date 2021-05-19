package org.energygrid.east.solarparkservice;

import org.energygrid.east.solarparkservice.controller.SolarParkController;
import org.energygrid.east.solarparkservice.model.SolarPark;
import org.energygrid.east.solarparkservice.service.ISolarParkPower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class SolarIntegrationTests {

    @InjectMocks
    private SolarParkController SolarParkController;

    @Mock
    private ISolarParkPower solarParkPower;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(SolarParkController).build();
    }

    @Test
    void returnOkGetSolarPark() throws Exception {

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
    void returnBadRequestGetSolarPark() throws Exception {


        mockMvc.perform(get("/solarpark").param("id", "999"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void returnBadRequestPostSolarPark() throws Exception {

        mockMvc.perform(post("/solarpark").param("totalsonarpanels", "222").param("name", (String) null))
                .andExpect(status().isBadRequest());
    }

//    @Test
//    public void returnOkPostSolarPark() throws Exception {
//
//        AddSolarParkDTO solarParkDTO = new AddSolarParkDTO("test",40,new Point(1,2),"test","5555AB","Gelderland",1,40,2015);
//           String json =  new ObjectMapper().writeValueAsString(solarParkDTO);
//        mockMvc.perform(post("/solarpark").contentType(MediaType.APPLICATION_JSON).content(json))
//                .andExpect(status().isCreated());
//    }

    @Test
    void returnBadRequestDeleteSolarPark() throws Exception {

        mockMvc.perform(delete("/solarpark").param("name", (String) null))
                .andExpect(status().isBadRequest());
    }

//    @Test
//    void returnDeleteSolarPark() throws Exception {
//
//        final SolarPark solarPark = new SolarPark();
//        solarPark.setSolarParkName("test");
//        solarPark.setSolarParkId(UUID.randomUUID());
//
//        // Give data back if method gets called
//        when(solarParkPower.doesNameExist(solarPark.getSolarParkName())).thenReturn(true);
//        when(solarParkPower.getSolarParkByName(solarPark.getSolarParkName())).thenReturn(solarPark);
//
//        mockMvc.perform(delete("/solarpark").param("name","test"))
//                .andExpect(status().isOk());
//    }

    @Test
    void returnUpdateSolarPark() throws Exception {

        final SolarPark solarPark = new SolarPark();
        solarPark.setSolarParkName("test");
        solarPark.setSolarParkId(UUID.randomUUID());
        solarPark.setCountSonarPanels(60);

        mockMvc.perform(put("/solarpark").param("name", "test").param("id", solarPark.getSolarParkId().toString()).param("solarpanels", "50"))
                .andExpect(status().isOk());
    }

    @Test
    void returnUpdateSolarParkBadRequestName() throws Exception {

        final SolarPark solarPark = new SolarPark();
        solarPark.setSolarParkName("test");
        solarPark.setSolarParkId(UUID.randomUUID());
        solarPark.setCountSonarPanels(60);

        mockMvc.perform(put("/solarpark").param("name", (String) null).param("id", solarPark.getSolarParkId().toString()).param("solarpanels", "55"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void returnUpdateSolarParkBadRequestId() throws Exception {

        final SolarPark solarPark = new SolarPark();
        solarPark.setSolarParkName("test");
        solarPark.setSolarParkId(UUID.randomUUID());
        solarPark.setCountSonarPanels(60);


        mockMvc.perform(put("/solarpark").param("name", "test").param("id", (String) null).param("solarpanels", "0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void returnUpdateSolarParkBadRequestSonarPanels() throws Exception {

        final SolarPark solarPark = new SolarPark();
        solarPark.setSolarParkName("test");
        solarPark.setSolarParkId(UUID.randomUUID());
        solarPark.setCountSonarPanels(60);

        mockMvc.perform(put("/solarpark").param("name", "test").param("id", solarPark.getSolarParkId().toString()).param("solarpanels", (String) null))
                .andExpect(status().isBadRequest());
    }

}
