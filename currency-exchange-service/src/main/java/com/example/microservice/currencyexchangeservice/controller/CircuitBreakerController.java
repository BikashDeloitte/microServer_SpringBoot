package com.example.microservice.currencyexchangeservice.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
* If u call this rest api , then in console u will see 3 times "check how many times it retry"
* as it try 3 time to re-run it - @Retry(name = "default")
*
*@Retry(name = "CircuitBreaker") - it is customize to retry 5 time (customize code is in application.properties)
*fallbackMethod method is call when all retries are fail.
*
*  @CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")- after retiring some time if it continues fails
* then it will return directly fallbackMethod - just refresh/send request and list value will increase but after sometime it will stop.
* CircuitBreaker state:
* closed - when the request is passed or the start of the request fails.
* open - When it repeatedly fails after a closed state, it moves to an open state, and in this state, it directly returns to fallbackMethod
* half-open - After the open state, it moved to a half-open state and sent a request to the api (which user is sending/requesting a response)
* If the failing rate is below a threshold value, it is moved to a closed state, otherwise to an open state.
*
* RateLimiter is used for limiting the number of access requests.
*
* bulkhead allow u to control concurrent access of request.(not try yet)
* */
@RestController
public class CircuitBreakerController {

    private final Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);
    private int i = 1;
    private final Map<Integer, Date> list = new HashMap<>();

    @GetMapping("/circuit-breaker")
//    @Retry(name = "CircuitBreaker", fallbackMethod = "hardcodedResponse")
//    @CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    @RateLimiter(name="default")
    @Bulkhead(name="CircuitBreaker")
    public String CircuitBreaker(){

        list.put(i,new Date());
        i++;
        logger.info("check how many times it retry");

        //use for Retry and CircuitBreaker as this code will fail the request
//        ResponseEntity<String> entity = new RestTemplate().getForEntity("http://localhost:8000/dummay-api", String.class);
//        return entity.getBody();

        return "Rate Limiter";
    }

    public String hardcodedResponse(Exception ex) {
        return list.toString();
    }
}
