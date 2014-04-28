/* 
 * TestCamelContextInject.java
 *
 * 2014 SMT Software. UMK SEUP project.
 */
package com.smt.example.camel.order;

import javax.annotation.PostConstruct;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelContextAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author pkaczanowski
 * @version 22 kwi 2014
 */
@Component
@Lazy(false)
public class TestCamelContextInject implements CamelContextAware {

    private static final Logger LOG = LoggerFactory.getLogger(TestCamelContextInject.class);

    private CamelContext context;

    @PostConstruct
    public void init() {
        LOG.warn("Context is {}", context.getClass().getName());
    }

    @Override
    public void setCamelContext(CamelContext camelContext) {
        context = camelContext;
    }

    @Override
    public CamelContext getCamelContext() {
        return context;
    }

}
