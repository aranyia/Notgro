package org.aranyia.notgro.model.discount;

import org.aranyia.notgro.model.item.Item;

public class PercentageDiscount<T extends Item> implements Discount<T> {

    private final double discountRate;

    public PercentageDiscount(double discountRate) {
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
