//package org.energygrid.east.solarparkservice.repo;
//
//
//import org.energygrid.east.solarparkservice.model.SolarPanel;
//import org.energygrid.east.solarparkservice.model.SolarPark;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public class ISolarParkRepo {
//    //todo implements db and make this interface for now just static list ;
//    private static List<SolarPanel>solarPanels;
//    private static List<SolarPark> solarParks;
//
//    public ISolarParkRepo(){
//
//        SolarPanel solarPanel = new SolarPanel(1,false);
//        solarPanels.add(1,solarPanel);
//        SolarPark solarPark = new SolarPark(1,"NewSolarpark", solarPanels);
//        solarParks.add(solarPark);
//    }
//
//    public static List<SolarPanel> getSolarPanels() {
//        return solarPanels;
//    }
//
//
//    public static List<SolarPark> getSolarParks() {
//        return solarParks;
//    }
//}
