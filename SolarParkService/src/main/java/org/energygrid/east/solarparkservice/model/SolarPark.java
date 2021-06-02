package org.energygrid.east.solarparkservice.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document(collection = "solarparkdetails")
public class    SolarPark {

    @Id
    private UUID solarParkId;

    @Indexed(unique = true)
    private String solarParkName;

    private int countSonarPanels;

    private Point coordinates;

    private String applicant;

    private String zipCode;

    private String province;

    private double power;

    private int max;

    private int yearOfRealised;

    private List<SolarParkUnit> units;

    public SolarPark() {
    }


    public SolarPark(UUID solarParkId, String solarParkName, int countSonarPanels, Point coordinates, List<SolarParkUnit> units, String applicant, String zipCode, String province, double power, int max, int yearOfRealised, double longCord, double langCord) {
        this.solarParkId = solarParkId;
        this.coordinates = coordinates;
        this.units = units;
        this.countSonarPanels = countSonarPanels;
        this.applicant = applicant;
        this.zipCode = zipCode;
        this.province = province;
        this.power = power;
        this.max = max;
        this.solarParkName = solarParkName;
        this.yearOfRealised = yearOfRealised;
    }

    public String getSolarParkName() {
        return solarParkName;
    }

    public void setSolarParkName(String solarParkName) {
        this.solarParkName = solarParkName;
    }

    public List<SolarParkUnit> getUnits() {
        return units;
    }

    public void setUnits(List<SolarParkUnit> units) {
        this.units = units;
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
                ", coordinates=" + coordinates +
                ", applicant='" + applicant + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", province='" + province + '\'' +
                ", power=" + power +
                ", max=" + max +
                ", yearOfRealised=" + yearOfRealised +
                ", units=" + units +
                '}';
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getYearOfRealised() {
        return yearOfRealised;
    }

    public void setYearOfRealised(int yearOfRealised) {
        this.yearOfRealised = yearOfRealised;
    }


}
