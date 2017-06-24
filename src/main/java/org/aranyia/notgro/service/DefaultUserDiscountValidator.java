package org.aranyia.notgro.service;

import org.aranyia.notgro.model.discount.PercentageDiscount;
import org.aranyia.notgro.model.discount.ValueDiscount;
import org.aranyia.notgro.model.user.Affiliate;
import org.aranyia.notgro.model.user.Employee;
import org.aranyia.notgro.model.user.User;

import java.time.LocalDate;

public class DefaultUserDiscountValidator implements UserDiscountValidator {

    private final double employeeDiscountRate;

    private final double affiliateDiscountRate;

    private final double loyalCustomerDiscountRate;

    private final double valueDiscount;

    private final double valueDiscountStep;

    private DefaultUserDiscountValidator(final Builder builder) {
        this.employeeDiscountRate = builder.employeeDiscountRate;
        this.affiliateDiscountRate = builder.affiliateDiscountRate;
        this.loyalCustomerDiscountRate = builder.loyalCustomerDiscountRate;
        this.valueDiscount = builder.valueDiscount;
        this.valueDiscountStep = builder.valueDiscountStep;
    }

    @Override
    public PercentageDiscount getPercentageDiscount(User user) {
        PercentageDiscount percentageDiscount = new PercentageDiscount(0);

        if (user instanceof Employee) {
            percentageDiscount = new PercentageDiscount(employeeDiscountRate);
        } else if (user instanceof Affiliate) {
            percentageDiscount = new PercentageDiscount(affiliateDiscountRate);
        } else if (isLoyalUser(user)) {
            percentageDiscount = new PercentageDiscount(loyalCustomerDiscountRate);
        }
        return percentageDiscount;
    }

    @Override
    public ValueDiscount getValueDiscount(User user) {
        return new ValueDiscount(valueDiscountStep, valueDiscount);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(DefaultUserDiscountValidator copy) {
        final Builder builder = new Builder();
        builder.valueDiscountStep = copy.valueDiscountStep;
        builder.valueDiscount = copy.valueDiscount;
        builder.employeeDiscountRate = copy.employeeDiscountRate;
        builder.affiliateDiscountRate = copy.affiliateDiscountRate;
        builder.loyalCustomerDiscountRate = copy.loyalCustomerDiscountRate;
        return builder;
    }

    private boolean isLoyalUser(User user) {
        if (user == null || user.getRegistrationDate() == null) {
            return false;
        } else {
            return LocalDate.now().minusYears(2).isBefore(user.getRegistrationDate());
        }
    }


    public static final class Builder {
        private double employeeDiscountRate;
        private double affiliateDiscountRate;
        private double loyalCustomerDiscountRate;
        private double valueDiscount;
        private double valueDiscountStep;

        private Builder() {
        }

        public Builder withValueDiscountStep(double val) {
            valueDiscountStep = val;
            return this;
        }

        public Builder withValueDiscount(double val) {
            valueDiscount = val;
            return this;
        }

        public Builder withEmployeeDiscountRate(double val) {
            employeeDiscountRate = val;
            return this;
        }

        public Builder withAffiliateDiscountRate(double val) {
            affiliateDiscountRate = val;
            return this;
        }

        public Builder withLoyalCustomerDiscountRate(double val) {
            loyalCustomerDiscountRate = val;
            return this;
        }

        public DefaultUserDiscountValidator build() {
            return new DefaultUserDiscountValidator(this);
        }
    }
}
