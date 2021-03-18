package org.energygrid.east.solarparkservice.errormessages;

public class CantAddSolarParkException extends RuntimeException {

    public CantAddSolarParkException() {
        super("Something went wrong in adding a new solar park");
    }
}
