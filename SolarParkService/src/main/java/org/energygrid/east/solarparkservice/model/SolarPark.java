package org.energygrid.east.solarparkservice.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document(collection = "solarparkdetails")
public class SolarPark {

    @Id
    private UUID solarParkId;

    @Indexed(unique = true)
    private String solarParkName;

    private int countSonarPanels;

    private List<SolarPanel> solarPanels;

    public SolarPark() {
    }


    public SolarPark(UUID solarParkId, String solarParkName, int countSonarPanels, List<SolarPanel> solarPanels) {
        this.solarParkId = solarParkId;
        this.solarParkName = solarParkName;
        this.solarPanels = solarPanels;
        this.countSonarPanels = countSonarPanels;
    }

    public String getSolarParkName() {
        return solarParkName;
    }

    public void setSolarParkName(String solarParkName) {
        this.solarParkName = solarParkName;
    }

    public List<SolarPanel> getSolarPanels() {
        return solarPanels;
    }

    public void setSolarPanels(List<SolarPanel> solarPanels) {
        this.solarPanels = solarPanels;
    }

    public int getCountSonarPanels() {
        return countSonarPanels;
    }

    public void setCountSonarPanels(int countSonarPanels) {
        this.countSonarPanels = countSonarPanels;
    }

    public UUID getSolarParkId() {
        return solarParkId;
    }

    public void setSolarParkId(UUID solarParkId) {
        this.solarParkId = solarParkId;
    }

    @Override
    public String toString() {
        return "SolarPark{" +
                "solarParkId=" + solarParkId +
                ", solarParkName='" + solarParkName + '\'' +
                ", countSonarPanels=" + countSonarPanels +
                ", solarPanels=" + solarPanels +
                '}';
    }
}
