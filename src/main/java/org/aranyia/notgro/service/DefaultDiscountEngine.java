package org.aranyia.notgro.service;

import org.aranyia.notgro.model.Bill;
import org.aranyia.notgro.model.item.Item;
import org.aranyia.notgro.model.discount.PercentageDiscount;
import org.aranyia.notgro.model.discount.ValueDiscount;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public class DefaultDiscountEngine {

    private final Collection<Predicate<Item>> discountFilters;

    public DefaultDiscountEngine(Collection<Predicate<Item>> discountFilters) {
        this.discountFilters = discountFilters;
    }

    public Bill validateDiscounts(Bill bill, PercentageDiscount percentageDiscount, ValueDiscount valueDiscount) {
        final List<Item> items = bill.getItems();

        final Predicate<Item> discountFilterCondition = discountFilters.stream()
                .reduce((f1, f2) -> f1.or(f2)).get();

        double sumDiscountedItems = items.stream()
                .filter(discountFilterCondition)
                .mapToDouble(percentageDiscount)
                .sum();

        double sumNoDiscountItems = items.stream()
                .filter(discountFilterCondition.negate())
                .mapToDouble(Item::getPrice)
                .sum();

        double sumTotal = sumDiscountedItems + sumNoDiscountItems;
        sumTotal = valueDiscount.applyAsDouble(sumTotal);

        bill.setSumTotal(sumTotal);
        return bill;
    }
}
