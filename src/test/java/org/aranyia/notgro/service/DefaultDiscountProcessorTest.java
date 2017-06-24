package org.aranyia.notgro.service;

import org.aranyia.notgro.model.discount.PercentageDiscount;
import org.aranyia.notgro.model.discount.ValueDiscount;
import org.aranyia.notgro.model.item.GroceryItem;
import org.aranyia.notgro.model.item.Item;
import org.aranyia.notgro.model.Bill;
import org.aranyia.notgro.model.user.User;
import org.aranyia.notgro.predicates.DiscountFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class DefaultDiscountProcessorTest {

    private DefaultDiscountEngine discountProcessor;

    @Before
    public void init() {
        List<Predicate<Item>> discountFilters = Arrays.asList(DiscountFilter.fromAsNegated(GroceryItem.class));
        discountProcessor = new DefaultDiscountEngine(discountFilters);
    }

    @Test
    public void testDiscounts() {
        List<Item> items = Arrays.asList(new Item(100), new Item(80), new Item(30), new GroceryItem(5.0));
        Bill bill = new Bill(items);

        User customer = new User(LocalDate.now(),0.3);

        final PercentageDiscount percentageDiscount = new PercentageDiscount<>(customer.getDiscountRate());

        final ValueDiscount valueDiscount = new ValueDiscount(100.0, 5.0);

        Bill billOut = discountProcessor.validateDiscounts(bill, percentageDiscount, valueDiscount);

        assertThat(billOut, notNullValue());
        assertThat(billOut.getSumTotal(), equalTo(147.0));
    }
}
