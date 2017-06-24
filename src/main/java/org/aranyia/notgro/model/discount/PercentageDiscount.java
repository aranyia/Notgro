package org.aranyia.notgro.model.discount;

import org.aranyia.notgro.model.item.Item;

public class PercentageDiscount<T extends Item> implements Discount<T> {

    private final double discountRate;

    public PercentageDiscount(double discountRate) {
        if (discountRate < 0 || discountRate > 1) {
            throw new IllegalArgumentException("rate value must be between 0 and 1 inclusive");
        }
        this.discountRate = discountRate;
    }

    @Override
    public double applyAsDouble(T value) {
        return (1.0 - discountRate) * value.getPrice();
    }

    public double getDiscountRate() {
        return discountRate;
    }
}
