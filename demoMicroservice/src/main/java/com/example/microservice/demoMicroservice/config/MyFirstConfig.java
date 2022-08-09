package com.example.microservice.demoMicroservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter @Setter
@Component
@ConfigurationProperties("my-first-config")
public class MyFirstConfig {

    private int maximum;
    private int minimum;
    private String configType;
}