package org.energygrid.east.regionservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "regiondata")
public class House {

    @Id
    private String hash;

    private String number;

    private String street;

    private String city;

    private String region;

    private String postcode;

    private List<Double> coordinates;

    public House(String hash, String number, String street, String city, String region, String postcode, List<Double> coordinates) {
        this.hash = hash;
        this.number = number;
        this.street = street;
        this.city = city;
        this.region = region;
        this.postcode = postcode;
        this.coordinates = coordinates;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }




    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }
}
