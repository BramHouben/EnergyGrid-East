package com.example.solarparkservice.controller;


import com.example.solarparkservice.service.ISolarParkPower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/solarpark")
public class SolarParkController {

    @Autowired
    public ISolarParkPower solarParkPower;





}
