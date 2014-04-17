/* 
 * OutputAddOrder.java
 *
 * 2014 SMT Software. UMK SEUP project.
 */
package com.smt.example.cxf.order;

/**
 * @author pkaczanowski
 * @version 17 kwi 2014
 */
public class OutputAddOrder {

    private boolean status;

    public OutputAddOrder(boolean status) {
        super();
        this.status = status;
    }

    /**
     * @return the status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

}
