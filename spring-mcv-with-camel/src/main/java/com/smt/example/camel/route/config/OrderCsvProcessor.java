/* 
 * OrderCsvProcessor.java
 *
 * 2014 SMT Software. UMK SEUP project.
 */
package com.smt.example.camel.route.config;

import java.io.InputStream;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import com.smt.example.camel.order.Order;

/**
 * @author pkaczanowski
 * @version 17 kwi 2014
 */
public class OrderCsvProcessor implements Processor {

    private static final Logger LOG = LoggerFactory.getLogger(OrderCsvProcessor.class);

    private final String company;

    public OrderCsvProcessor(String company) {
        super();
        this.company = company;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        InputStream body = exchange.getIn().getBody(InputStream.class);
        List<String> lines = IOUtils.readLines(body);
        lines.remove(0);
        List<Order> result = Lists.newArrayListWithCapacity(lines.size());
        for (String line : lines) {
            if (StringUtils.isNotBlank(line)) {
                List<String> values = Splitter.on(";").splitToList(line);
                Order order = new Order(Ints.tryParse(values.get(1)), values.get(0), company);
                result.add(order);
                LOG.debug("Add order {}", order);
            }
        }
        exchange.getIn().setBody(result);
    }

}
