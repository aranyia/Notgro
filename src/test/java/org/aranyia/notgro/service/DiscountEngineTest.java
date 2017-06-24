package org.aranyia.notgro.service;

import org.aranyia.notgro.model.discount.PercentageDiscount;
import org.aranyia.notgro.model.discount.ValueDiscount;
import org.aranyia.notgro.model.item.GroceryItem;
import org.aranyia.notgro.model.item.Item;
import org.aranyia.notgro.model.Bill;
import org.aranyia.notgro.model.user.Employee;
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
public class DiscountEngineTest {

    private DiscountEngine discountProcessor;

    private UserDiscountValidator userDiscountValidator;

    @Before
    public void init() {
        List<Predicate<Item>> discountFilters = Arrays.asList(DiscountFilter.fromAsNegated(GroceryItem.class));
        discountProcessor = new DefaultDiscountEngine(discountFilters);

        userDiscountValidator = DefaultUserDiscountValidator.newBuilder()
                .withAffiliateDiscountRate(.1)
                .withEmployeeDiscountRate(0.3)
                .withLoyalCustomerDiscountRate(.05)
                .withValueDiscountStep(100.0)
                .withValueDiscount(5.0)
                .build();
    }

    @Test
    public void testDiscounts() {
        List<Item> items = Arrays.asList(new Item(100), new Item(80), new Item(30), new GroceryItem(5.0));
        Bill bill = new Bill(items);

        Employee user = new Employee();
        user.setRegistrationDate(LocalDate.now().minusYears(2).minusDays(10));

        userDiscountValidator.getPercentageDiscount(user);

        final PercentageDiscount percentageDiscount = userDiscountValidator.getPercentageDiscount(user);

        final ValueDiscount valueDiscount = userDiscountValidator.getValueDiscount(user);

        Bill billOut = discountProcessor.applyDiscounts(bill, percentageDiscount, valueDiscount);

        assertThat(billOut, notNullValue());
        assertThat(billOut.getSumTotal(), equalTo(147.0));
    }
}
