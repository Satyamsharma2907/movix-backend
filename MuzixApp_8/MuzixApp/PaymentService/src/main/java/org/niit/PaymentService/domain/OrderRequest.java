package org.niit.PaymentService.domain;

import java.math.BigInteger;


public class OrderRequest {

    private String email;
    private BigInteger amount;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }
}
