/* 
 * OrderSercive.java
 *
 * 2014 SMT Software. UMK SEUP project.
 */
package com.smt.example.camel.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pkaczanowski
 * @version 17 kwi 2014
 */
@Service
public class OrderSercive {

    private OrderRepository orderRepository;

    @Autowired
    public OrderSercive(OrderRepository orderRepository) {
        super();
        this.orderRepository = orderRepository;
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

}
