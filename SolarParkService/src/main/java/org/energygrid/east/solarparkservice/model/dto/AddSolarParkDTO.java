package org.energygrid.east.solarparkservice.model.dto;

import org.springframework.data.geo.Point;


public class AddSolarParkDTO {

    private final String solarParkName;
    private final int countSonarPanels;
    private final Point coordinates;
    private final String applicant;
    private final String zipCode;
    private final String province;
    private final double power;
    private final int max;
    private final int yearOfRealised;

    public AddSolarParkDTO(String solarParkName, int countSonarPanels, Point coordinates, String applicant, String zipCode, String province, double power, int max, int yearOfRealised) {

        this.solarParkName = solarParkName;
        this.countSonarPanels = countSonarPanels;
        this.coordinates = coordinates;
        this.applicant = applicant;
        this.zipCode = zipCode;
        this.province = province;
        this.power = power;
        this.max = max;
        this.yearOfRealised = yearOfRealised;

    }

    public String getSolarParkName() {
        return solarParkName;
    }

    public int getCountSonarPanels() {
        return countSonarPanels;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public String getApplicant() {
        return applicant;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getProvince() {
        return province;
    }

    public double getPower() {
        return power;
    }

    public int getMax() {
        return max;
    }

    public int getYearOfRealised() {
        return yearOfRealised;
    }


}
