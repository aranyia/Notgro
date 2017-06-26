package org.aranyia.notgro.service;

import org.aranyia.notgro.model.Bill;
import org.aranyia.notgro.model.discount.PercentageDiscount;
import org.aranyia.notgro.model.discount.ValueDiscount;
import org.aranyia.notgro.model.item.GroceryItem;
import org.aranyia.notgro.model.item.Item;
import org.aranyia.notgro.model.user.Affiliate;
import org.aranyia.notgro.model.user.Employee;
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
public class DiscountEngineTest {

    private DiscountEngine discountEngine;

    private UserDiscountValidator userDiscountValidator;

    private Bill bill;

    @Before
    public void init() {
        final List<Predicate<Item>> discountFilters = Arrays.asList(DiscountFilter.fromAsNegated(GroceryItem.class));
        discountEngine = new DefaultDiscountEngine(discountFilters);

        userDiscountValidator = DefaultUserDiscountValidator.newBuilder()
                .withAffiliateDiscountRate(0.1)
                .withEmployeeDiscountRate(0.3)
                .withLoyalCustomerDiscountRate(0.05)
                .withLoyalCustomerRegistrationDays(2 * 365)
                .withValueDiscountStep(100.0)
                .withValueDiscount(5.0)
                .build();

        final List<Item> items =
                Arrays.asList(new Item(100), new Item(80), new Item(30), new GroceryItem(5.0));
        bill = new Bill(items);
    }

    @Test
    public void testDiscountsForAffiliate() {
        //given
        final Affiliate user = new Affiliate();
        user.setRegistrationDate(LocalDate.now().minusYears(3));

        //when
        userDiscountValidator.getPercentageDiscount(user);

        final PercentageDiscount percentageDiscount = userDiscountValidator.getPercentageDiscount(user);
        final ValueDiscount valueDiscount = userDiscountValidator.getValueDiscount(user);

        //then
        final Bill actualBill = discountEngine.applyDiscounts(bill, percentageDiscount, valueDiscount);

        assertThat(actualBill, notNullValue());
        assertThat(actualBill.getSumTotal(), equalTo(189.0));
    }

    @Test
    public void testDiscountsForEmployee() {
        //given
        final Employee user = new Employee();
        user.setRegistrationDate(LocalDate.now().minusYears(1));

        //when
        userDiscountValidator.getPercentageDiscount(user);

        final PercentageDiscount percentageDiscount = userDiscountValidator.getPercentageDiscount(user);
        final ValueDiscount valueDiscount = userDiscountValidator.getValueDiscount(user);

        //then
        final Bill actualBill = discountEngine.applyDiscounts(bill, percentageDiscount, valueDiscount);

        assertThat(actualBill, notNullValue());
        assertThat(actualBill.getSumTotal(), equalTo(147.0));
    }

    @Test
    public void testDiscountsForLoyalCustomer() {
        //given
        final User user = new User();
        user.setRegistrationDate(LocalDate.now().minusDays(2 * 365 + 10));

        //when
        userDiscountValidator.getPercentageDiscount(user);

        final PercentageDiscount percentageDiscount = userDiscountValidator.getPercentageDiscount(user);
        final ValueDiscount valueDiscount = userDiscountValidator.getValueDiscount(user);

        //then
        final Bill actualBill = discountEngine.applyDiscounts(bill, percentageDiscount, valueDiscount);

        assertThat(actualBill, notNullValue());
        assertThat(actualBill.getSumTotal(), equalTo(194.5));
    }

    @Test
    public void testDiscountsForCommonUser() {
        //given
        final User user = new User();
        user.setRegistrationDate(LocalDate.now().minusDays(10));

        //when
        userDiscountValidator.getPercentageDiscount(user);

        final PercentageDiscount percentageDiscount = userDiscountValidator.getPercentageDiscount(user);
        final ValueDiscount valueDiscount = userDiscountValidator.getValueDiscount(user);

        //then
        final Bill actualBill = discountEngine.applyDiscounts(bill, percentageDiscount, valueDiscount);

        assertThat(actualBill, notNullValue());
        assertThat(actualBill.getSumTotal(), equalTo(205.0));
    }
}
