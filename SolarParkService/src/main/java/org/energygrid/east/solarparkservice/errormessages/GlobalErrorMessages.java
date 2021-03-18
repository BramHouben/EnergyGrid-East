package org.energygrid.east.solarparkservice.errormessages;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalErrorMessages extends ResponseEntityExceptionHandler {


    @ExceptionHandler(SolarParkNotFoundException.class)
    public ResponseEntity<String> solarParkNotFound(SolarParkNotFoundException e) {
        //Todo some logging

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(CantAddSolarParkException.class)
    public ResponseEntity<String> cantAddSolarPark(CantAddSolarParkException e) {
        //Todo some logging

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(CantRemoveSolarParkException.class)
    public ResponseEntity<String> cantRemoveSolarPark(CantRemoveSolarParkException e) {
        //Todo some logging

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(CantUpdateSolarParkException.class)
    public ResponseEntity<String> cantRemoveSolarPark(CantUpdateSolarParkException e) {
        //Todo some logging

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}
