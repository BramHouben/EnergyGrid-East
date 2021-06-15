package org.energygrid.east.solarparkservice.model.dto;

import org.springframework.data.geo.Point;


public class AddSolarParkDTO {

    private String solarParkName;
    private int countSonarPanels;
    private Point coordinates;
    private String applicant;
    private String zipCode;
    private String province;
    private double power;
    private int max;
    private int yearOfRealised;

    public AddSolarParkDTO() {
        //Should be empty
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
