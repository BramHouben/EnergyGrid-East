package org.energygrid.east.solarparkservice.errormessages;

public class CantRemoveSolarParkException extends RuntimeException {

    public CantRemoveSolarParkException() {
        super("Cant remove Solar park");
    }
}
