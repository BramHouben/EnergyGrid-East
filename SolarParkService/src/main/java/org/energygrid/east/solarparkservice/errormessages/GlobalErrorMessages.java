package org.energygrid.east.solarparkservice.errormessages;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalErrorMessages extends ResponseEntityExceptionHandler {


    @ExceptionHandler(SolarParkNotFoundException.class)
    public ResponseEntity<?> SolarParkNotFound(SolarParkNotFoundException e) {
        //Todo some logging

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(CantAddSolarParkException.class)
    public ResponseEntity<?> CantAddSolarPark(CantAddSolarParkException e) {
        //Todo some logging

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(CantRemoveSolarParkException.class)
    public ResponseEntity<?> CantRemoveSolarPark(CantRemoveSolarParkException e) {
        //Todo some logging

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(CantUpdateSolarParkException.class)
    public ResponseEntity<?> CantRemoveSolarPark(CantUpdateSolarParkException e) {
        //Todo some logging

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}
