/* 
 * InputOrder.java
 *
 * 2014 SMT Software. UMK SEUP project.
 */
package com.smt.example.cxf.order;

/**
 * @author pkaczanowski
 * @version 17 kwi 2014
 */
public class InputAddOrder {

    private String product;

    private int size;

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

}
