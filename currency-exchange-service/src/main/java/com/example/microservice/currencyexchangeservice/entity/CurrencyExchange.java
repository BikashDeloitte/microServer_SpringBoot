package com.example.microservice.currencyexchangeservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/*
* we are using column annotation as from is key of SQL.
* Also conversionMultiple will be write as CONVERSION_MULTIPLE in table.
* */

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
public class CurrencyExchange {
    @Id
    private Long id;

    @Column(name = "Currency_from")
    private String from;
    @Column(name = "Currency_to")
    private String to;
    private BigDecimal conversionMultiple;
    private String environmentPort;

    public CurrencyExchange(Long id, String from, String to, BigDecimal conversionMultiple) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.conversionMultiple = conversionMultiple;
    }
}
