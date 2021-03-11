package org.energygrid.east.solarparkservice.errormessages;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalErrorMessages extends ResponseEntityExceptionHandler {


    @ExceptionHandler(SolarParkNotFoundExcep.class)
       public ResponseEntity SolarParkNotFount (SolarParkNotFoundExcep e)  {
        //Todo some logging

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
}
