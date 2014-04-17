/* 
 * SampleRouteConfig.java
 *
 * 2014 SMT Software. UMK SEUP project.
 */
package com.smt.example.camel.route.config;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.javaconfig.SingleRouteCamelConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * @author pkaczanowski
 * @version 17 kwi 2014
 */
@Configuration
public class SampleRouteConfig extends SingleRouteCamelConfiguration {

    @Override
    public RouteBuilder route() {
        return new RouteBuilder() {

            @Override
            public void configure() throws Exception {

                from("file:c:/temp/in").to("file:c:/temp/out");

            }

        };
    }

}
