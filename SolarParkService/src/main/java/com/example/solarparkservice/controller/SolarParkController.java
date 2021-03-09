package com.example.solarparkservice.controller;


import com.example.solarparkservice.service.ISolarParkPower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

@RestController
@RequestMapping("solarpark")
public class SolarParkController {

    @Autowired
    public ISolarParkPower solarParkPower;




}
