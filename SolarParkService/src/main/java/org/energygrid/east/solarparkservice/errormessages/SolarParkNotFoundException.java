package org.energygrid.east.solarparkservice.errormessages;

public class SolarParkNotFoundException extends RuntimeException {

    public SolarParkNotFoundException(int id){
        super("solar park not found with the id:"+ id);
    }

}
