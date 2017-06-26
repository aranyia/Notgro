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
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DiscountEngineTest {

    private static final double DISCOUNT_RATE_AFFILIATE = 0.1;
    private static final double DISCOUNT_RATE_DEFAULT = 0.0;
    private static final double DISCOUNT_RATE_EMPLOYEE = 0.3;
    private static final double DISCOUNT_RATE_LOYAL_CUSTOMER = 0.05;
    private static final double DISCOUNT_VALUE = 5.0;
    private static final double DISCOUNT_VALUE_STEP = 100.0;
    private static final int LOYAL_CUSTOMER_REG_DAYS = 2 * 365;

    private DiscountEngine discountEngine;

    @Mock
    private UserDiscountValidator userDiscountValidator;

    private Bill bill;

    @Before
    public void init() {
        final List<Predicate<Item>> discountFilters = Arrays.asList(DiscountFilter.fromAsNegated(GroceryItem.class));
        discountEngine = new DefaultDiscountEngine(discountFilters);

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
        when(userDiscountValidator.getPercentageDiscount(user))
                .thenReturn(new PercentageDiscount(DISCOUNT_RATE_AFFILIATE));
        when(userDiscountValidator.getValueDiscount(user))
                .thenReturn(new ValueDiscount(DISCOUNT_VALUE_STEP, DISCOUNT_VALUE));

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
        when(userDiscountValidator.getPercentageDiscount(user))
                .thenReturn(new PercentageDiscount(DISCOUNT_RATE_EMPLOYEE));
        when(userDiscountValidator.getValueDiscount(user))
                .thenReturn(new ValueDiscount(DISCOUNT_VALUE_STEP, DISCOUNT_VALUE));

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
        user.setRegistrationDate(LocalDate.now().minusDays(LOYAL_CUSTOMER_REG_DAYS + 10));

        //when
        when(userDiscountValidator.getPercentageDiscount(user))
                .thenReturn(new PercentageDiscount(DISCOUNT_RATE_LOYAL_CUSTOMER));
        when(userDiscountValidator.getValueDiscount(user))
                .thenReturn(new ValueDiscount(DISCOUNT_VALUE_STEP, DISCOUNT_VALUE));

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
        when(userDiscountValidator.getPercentageDiscount(user))
                .thenReturn(new PercentageDiscount(DISCOUNT_RATE_DEFAULT));
        when(userDiscountValidator.getValueDiscount(user))
                .thenReturn(new ValueDiscount(DISCOUNT_VALUE_STEP, DISCOUNT_VALUE));

        final PercentageDiscount percentageDiscount = userDiscountValidator.getPercentageDiscount(user);
        final ValueDiscount valueDiscount = userDiscountValidator.getValueDiscount(user);

        //then
        final Bill actualBill = discountEngine.applyDiscounts(bill, percentageDiscount, valueDiscount);

        assertThat(actualBill, notNullValue());
        assertThat(actualBill.getSumTotal(), equalTo(205.0));
    }
}
