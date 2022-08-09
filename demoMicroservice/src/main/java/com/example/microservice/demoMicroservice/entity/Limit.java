package com.example.microservice.demoMicroservice.entity;

import lombok.*;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor @ToString
public class Limit {

    private int maximum;
    private int minimum;
    private String configType;
}
