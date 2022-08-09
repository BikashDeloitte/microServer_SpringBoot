package com.example.microservice.demoMicroservice.controller;

import com.example.microservice.demoMicroservice.config.MyFirstConfig;
import com.example.microservice.demoMicroservice.entity.Limit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private MyFirstConfig myFirstConfig;

    @GetMapping("/limit")
    public Limit retrievelLimits(){
        return new Limit(myFirstConfig.getMaximum(), myFirstConfig.getMinimum(), myFirstConfig.getConfigType());
    }
}
