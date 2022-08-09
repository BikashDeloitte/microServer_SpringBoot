package com.example.microservice.currencyconversionservice.DAO;

import com.example.microservice.currencyconversionservice.entity.CurrencyConversion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*
 *FeignClient makes writing web service clients easier.
 * Here name is the name of service which we want to call.
 * url is the front part of url that we want to call.
 * url one is comment as url is hard code, instead of hard code we are using eureka for environment name(port)
 * We are using CurrencyConversion instead of CurrencyExchange ,
 * that is the reason we made CurrencyConversion similar to CurrencyExchange entity.
 * */

//@FeignClient(name = "currency-exchange", url = "localhost:8000")
@FeignClient(name = "currency-exchange")
public interface CurrencyExchangeProxy {

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    CurrencyConversion retrieveExchangeValue(@PathVariable String from, @PathVariable String to);
}
