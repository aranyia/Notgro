package org.aranyia.notgro.model.user;

import java.time.LocalDate;

public class User {

    private LocalDate customerSince;

    private double discountRate;

    private User() {
    }

    public User(final LocalDate customerSince, final double applicableDiscount) {
        this.customerSince = customerSince;
        this.discountRate = applicableDiscount;
    }

    public LocalDate getCustomerSince() {
        return customerSince;
    }

    public double getDiscountRate() {
        return discountRate;
    }
}
