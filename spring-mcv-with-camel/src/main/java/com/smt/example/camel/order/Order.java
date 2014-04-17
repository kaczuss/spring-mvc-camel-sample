/* 
 * Order.java
 *
 * 2014 SMT Software. UMK SEUP project.
 */
package com.smt.example.camel.order;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.Objects;

/**
 * @author pkaczanowski
 * @version 17 kwi 2014
 */
@Entity
@Table(name = "orders")
public class Order implements Serializable {

    private static final long serialVersionUID = 758920631253473994L;

    @Id
    @GeneratedValue
    private Long id;

    private int size;

    private String product;

    private String company;

    public Order(int size, String product, String company) {
        super();
        this.size = size;
        this.product = product;
        this.company = company;
    }

    public Order() {
        super();
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * @return the product
     */
    public String getProduct() {
        return product;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(String product) {
        this.product = product;
    }

    /**
     * @return the company
     */
    public String getCompany() {
        return company;
    }

    /**
     * @param company the company to set
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", size=" + size + ", product=" + product + ", company=" + company + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, size, product, company);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Order) {
            Order that = (Order) object;
            return Objects.equal(this.id, that.id)
                    && Objects.equal(this.size, that.size)
                    && Objects.equal(this.product, that.product)
                    && Objects.equal(this.company, that.company);
        }
        return false;
    }

}
