/* 
 * OrderRepository.java
 *
 * 2014 SMT Software. UMK SEUP project.
 */
package com.smt.example.camel.order;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author pkaczanowski
 * @version 17 kwi 2014
 */
@Repository
@Transactional(readOnly = true)
public class OrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Order save(Order order) {
        entityManager.persist(order);
        return order;
    }

}
