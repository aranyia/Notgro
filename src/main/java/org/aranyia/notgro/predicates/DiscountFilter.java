package org.aranyia.notgro.predicates;

import org.aranyia.notgro.model.item.Item;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;

public class DiscountFilter implements Predicate<Item> {

    private final Collection<Class<? extends Item>> types;

    public DiscountFilter(Collection<Class<? extends Item>> types) {
        this.types = types;
    }

    @Override
    public boolean test(final Item item) {
        return types.stream()
                .map(type -> type.isAssignableFrom(item.getClass()))
                .reduce((a,b) -> a||b)
                .get();
    }

    public static Predicate<Item> from(Class<? extends Item>... types) {
        return new DiscountFilter(Arrays.asList(types));
    }

    public static Predicate<Item> fromAsNegated(Class<? extends Item>... types) {
        return new DiscountFilter(Arrays.asList(types)).negate();
    }
}