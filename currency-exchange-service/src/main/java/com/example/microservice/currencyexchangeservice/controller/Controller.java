package com.example.microservice.currencyexchangeservice.controller;

import com.example.microservice.currencyexchangeservice.DAO.CurrencyExchangeDAO;
import com.example.microservice.currencyexchangeservice.entity.CurrencyExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class Controller {

    @Autowired
    CurrencyExchangeDAO currencyExchangeDAO;
    @Autowired
    private Environment environment;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to){

        //extracting the port number from local server
        String port = environment.getProperty("local.server.port");

        CurrencyExchange currencyExchange = currencyExchangeDAO.findByFromAndTo(from,to);
        if(currencyExchange == null){
            throw new RuntimeException("unable to find from = " + from + " and to = "+to);
        }

        currencyExchange.setEnvironmentPort(port);
        return currencyExchange;
    }
}
