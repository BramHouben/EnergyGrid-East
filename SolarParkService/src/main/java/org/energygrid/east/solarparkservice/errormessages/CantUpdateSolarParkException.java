package org.energygrid.east.solarparkservice.errormessages;

public class CantUpdateSolarParkException extends RuntimeException {

    public CantUpdateSolarParkException() {
        super("Cant update solar park");
    }
}
