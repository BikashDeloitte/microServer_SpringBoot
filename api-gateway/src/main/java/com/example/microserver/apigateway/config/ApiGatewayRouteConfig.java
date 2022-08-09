package com.example.microserver.apigateway.config;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

/*
 * We have comment the gateway discovery locator from application as we are using router
 * lb://currency-conversion - lb means load balance and currency-conversion is the application name present in eureka
 * application name present in eureka is use to get the url of that application
 *
 * */

@Configuration
public class ApiGatewayRouteConfig {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {

        Function<PredicateSpec, Buildable<Route>> routeFunction
                = predicateSpec -> predicateSpec.path("/currency-conversion-feign/**")
                .uri("lb://currency-conversion");


        return builder.routes()
                .route(routeFunction)                                                   //we can made variable and use it
                .route(myChoice ->                                                      //Or we can directly write there
                        myChoice.path("/currency-conversion/**")               //Find the url with "currency-conversion" in it.
                                .uri("lb://currency-conversion"))                      //replacing the url with url retrieve from eureka

                .route(newPath ->
                        newPath.path("/my-new-path-conversion/**")             //new path which is not a part of Rest API
                                .filters(f -> f.rewritePath(                            //re-writing the receive path/url
                                        "/my-new-path-conversion/(?<segment>.*)",
                                        "/currency-conversion-feign/${segment}"))
                                .uri("lb://currency-conversion"))

                .route(p -> p.path("/get")                                                      //experimented but not succeed
                        .filters(f -> f
                                .addRequestHeader("MyHeader", "MyURI")
                                .addRequestParameter("Param", "MyValue"))
                        .uri("http://httpbin.org:80"))

                .route(predicateSpec -> predicateSpec.path("/currency-exchange/**")
                        .uri("lb://currency-exchange"))

                .build();
    }
}
