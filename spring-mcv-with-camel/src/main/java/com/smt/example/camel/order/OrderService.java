/* 
 * OrderSercive.java
 *
 * 2014 SMT Software. UMK SEUP project.
 */
package com.smt.example.camel.order;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author pkaczanowski
 * @version 17 kwi 2014
 */
@Service()
public class OrderService {

    private OrderRepository orderRepository;

    private static final Logger LOG = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        super();
        this.orderRepository = orderRepository;
    }

    public Order save(Order order) {
        LOG.info("save oreder {}", order);
        return orderRepository.save(order);
    }

    @Transactional
    public void saveAll(Collection<Order> orders) {
        for (Order order : orders) {
            save(order);
        }
    }
}
