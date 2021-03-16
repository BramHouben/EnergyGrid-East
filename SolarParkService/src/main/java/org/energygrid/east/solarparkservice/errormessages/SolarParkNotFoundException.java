package org.energygrid.east.solarparkservice.errormessages;


public class SolarParkNotFoundException extends RuntimeException {

    public SolarParkNotFoundException(){
        super("solar park not found ");
    }

}
