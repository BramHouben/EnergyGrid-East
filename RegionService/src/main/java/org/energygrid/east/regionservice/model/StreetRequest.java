package org.energygrid.east.regionservice.model;

import java.io.Serializable;
import java.util.List;

public class StreetRequest implements Serializable {

    private  int totalStreet;
    private  List<House> houses;

    public StreetRequest(){

    }

    public StreetRequest(int totalStreet, List<House> houses) {
        this.totalStreet = totalStreet;
        this.houses = houses;
    }

    public int getTotalStreet() {
        return totalStreet;
    }

    public List<House> getHouses() {
        return houses;
    }

    public void setHouses(List<House> houses) {
        this.houses = houses;
    }

    public void setTotalStreet(int totalStreet) {
        this.totalStreet = totalStreet;
    }
}
