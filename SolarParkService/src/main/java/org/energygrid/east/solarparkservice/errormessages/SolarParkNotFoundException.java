package org.energygrid.east.solarparkservice.errormessages;

import java.util.UUID;

public class SolarParkNotFoundException extends RuntimeException {

    public SolarParkNotFoundException(){
        super("solar park not found ");
    }

}
