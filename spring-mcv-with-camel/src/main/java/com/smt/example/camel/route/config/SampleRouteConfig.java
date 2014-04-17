/* 
 * SampleRouteConfig.java
 *
 * 2014 SMT Software. UMK SEUP project.
 */
package com.smt.example.camel.route.config;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.spring.javaconfig.SingleRouteCamelConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.smt.example.camel.order.Order;
import com.smt.example.camel.order.OrderService;
import com.smt.example.cxf.order.InputAddOrder;
import com.smt.example.cxf.order.MaintainOrderSerice;
import com.smt.example.cxf.order.OutputAddOrder;

/**
 * @author pkaczanowski
 * @version 17 kwi 2014
 */
@Configuration
@ImportResource("classpath:META-INF/cxf/cxf.xml")
public class SampleRouteConfig extends SingleRouteCamelConfiguration {

    private final String uri = "cxf:/order?serviceClass=" + MaintainOrderSerice.class.getName();

    @Autowired
    private OrderService orderService;

    //    public SampleRouteConfig(OrderService orderService) {
    //        super();
    //        this.orderService = orderService;
    //    }

    @Override
    public RouteBuilder route() {
        return new RouteBuilder() {

            @Override
            public void configure() throws Exception {

                from("file:c:/temp/in").to("file:c:/temp/out");

                from("file:c:/temp/out?include=.*\\.csv&move=.camel/${file:name.noext}_${date:now:yyyy_MM_dd_HH_mm_ss}.csv").process(
                        new OrderCsvProcessor("corporejszyn")).beanRef(
                        "orderService", "saveAll");
                from("file:c:/temp/out?include=bindy_.*\\.csv&move=.camel/${file:name.noext}_${date:now:yyyy_MM_dd_HH_mm_ss}.csv").unmarshal(
                        new BindyCsvDataFormat(Order.class))
                        .beanRef(
                                "orderService", "saveAll");

                from(uri)
                        .to("log:input")
                        // send the request to the route to handle the operation
                        // the name of the operation is in that header
                        .recipientList(simple("direct:${header.operationName}"));

                from("direct:addOrder").process(new Processor() {

                    @Override
                    public void process(Exchange exchange) throws Exception {
                        InputAddOrder order = exchange.getIn().getBody(InputAddOrder.class);

                        orderService.save(new Order(order.getSize(), order.getProduct(), "uzywam-wsa"));

                        exchange.getOut().setBody(new OutputAddOrder(true));
                    }
                });
            }

        };
    }
}
