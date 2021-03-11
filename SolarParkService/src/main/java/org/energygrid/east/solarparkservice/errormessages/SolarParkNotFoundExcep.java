package org.energygrid.east.solarparkservice.errormessages;

public class SolarParkNotFoundExcep extends RuntimeException {

    public SolarParkNotFoundExcep(int id){
        super("solar park not found with the id:"+ id);
    }

}
