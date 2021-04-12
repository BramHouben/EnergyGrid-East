package org.energygrid.east.regionservice;

import org.energygrid.east.regionservice.repo.IRegionRepo;
import org.energygrid.east.regionservice.service.RegionService;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class RegionserviceApplicationTests {


    @InjectMocks
    private RegionService regionService;

    @Mock
    private IRegionRepo regionRepo;


}
