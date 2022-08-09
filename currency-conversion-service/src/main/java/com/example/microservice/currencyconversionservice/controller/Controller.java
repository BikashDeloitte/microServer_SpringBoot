package com.example.microservice.currencyconversionservice.controller;

import com.example.microservice.currencyconversionservice.DAO.CurrencyExchangeProxy;
import com.example.microservice.currencyconversionservice.entity.CurrencyConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class Controller {

    @Autowired
    CurrencyExchangeProxy proxy;

    /*
     * RestTemplate is a class use for consuming other REST APIs means we can call other rest api and get they response
     * As you have notice that entity of CurrencyConversion and CurrencyExchange is almost similar
     * So we can choice the response body as ConversionCurrency (It's a mapping b/w similar name variables)
     * */
    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public ResponseEntity<CurrencyConversion> getConvertCurrency(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {

        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);

        ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity(    //this is predefine function to call API
                "http://localhost:8000/currency-exchange/from/{from}/to/{to}",              // url of currency exchange to the data from h2 database
                CurrencyConversion.class,                                                       // In which type response should be return
                uriVariables);                                                                  // To send values of "from" and "to" in url

        CurrencyConversion entityBody = responseEntity.getBody();

        if(entityBody == null){
            throw new RuntimeException("no found the conversion rate data ");
        }

        CurrencyConversion conversion = new CurrencyConversion(entityBody.getId(), from, to, quantity,
                entityBody.getConversionMultiple(), quantity.multiply(entityBody.getConversionMultiple()),
                entityBody.getEnvironmentPort()+" - using RestTemplate");

        return ResponseEntity.ok(conversion);
    }

    /*
    * Feign is a easier way of calling Rest API of other microservices.
    * We just have to made a interface and a similar entity to API response entity(returning object)
    * */

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public ResponseEntity<CurrencyConversion> getConvertCurrencyFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {

        CurrencyConversion entityBody = proxy.retrieveExchangeValue(from,to);

        CurrencyConversion conversion = new CurrencyConversion(entityBody.getId(), from, to, quantity,
                entityBody.getConversionMultiple(), quantity.multiply(entityBody.getConversionMultiple()),
                entityBody.getEnvironmentPort()+" - using feign");

        return ResponseEntity.ok(conversion);
    }
}
