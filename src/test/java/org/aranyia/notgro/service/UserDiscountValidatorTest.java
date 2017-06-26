package org.aranyia.notgro.service;

import org.aranyia.notgro.model.discount.PercentageDiscount;
import org.aranyia.notgro.model.discount.ValueDiscount;
import org.aranyia.notgro.model.user.Affiliate;
import org.aranyia.notgro.model.user.Employee;
import org.aranyia.notgro.model.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDate;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class UserDiscountValidatorTest {

    private static final double DISCOUNT_RATE_AFFILIATE = 0.1;
    private static final double DISCOUNT_RATE_DEFAULT = 0.0;
    private static final double DISCOUNT_RATE_EMPLOYEE = 0.3;
    private static final double DISCOUNT_RATE_LOYAL_CUSTOMER = 0.05;
    private static final double DISCOUNT_VALUE = 5.0;
    private static final double DISCOUNT_VALUE_STEP = 100.0;
    private static final int LOYAL_CUSTOMER_REG_DAYS = 2 * 365;

    private UserDiscountValidator userDiscountValidator;

    @Before
    public void init() {
        userDiscountValidator = DefaultUserDiscountValidator.newBuilder()
                .withAffiliateDiscountRate(DISCOUNT_RATE_AFFILIATE)
                .withEmployeeDiscountRate(DISCOUNT_RATE_EMPLOYEE)
                .withLoyalCustomerDiscountRate(DISCOUNT_RATE_LOYAL_CUSTOMER)
                .withLoyalCustomerRegistrationDays(LOYAL_CUSTOMER_REG_DAYS)
                .withValueDiscountStep(DISCOUNT_VALUE_STEP)
                .withValueDiscount(DISCOUNT_VALUE)
                .build();
    }

    @Test
    public void testGetDiscountsForAffiliate() {
        //given
        final Affiliate user = new Affiliate();
        user.setRegistrationDate(LocalDate.now().minusYears(3));

        //when
        final PercentageDiscount percentageDiscount = userDiscountValidator.getPercentageDiscount(user);
        final ValueDiscount valueDiscount = userDiscountValidator.getValueDiscount(user);

        //then
        assertThat(percentageDiscount, notNullValue());
        assertThat(percentageDiscount.getDiscountRate(), equalTo(DISCOUNT_RATE_AFFILIATE));
        assertThat(valueDiscount, notNullValue());
        assertThat(valueDiscount.getValueStep(), equalTo(DISCOUNT_VALUE_STEP));
        assertThat(valueDiscount.getValueStepDiscount(), equalTo(DISCOUNT_VALUE));
    }

    @Test
    public void testGetDiscountsForEmployee() {
        //given
        final Employee user = new Employee();
        user.setRegistrationDate(LocalDate.now().minusYears(1));

        //when
        final PercentageDiscount percentageDiscount = userDiscountValidator.getPercentageDiscount(user);
        final ValueDiscount valueDiscount = userDiscountValidator.getValueDiscount(user);

        //then
        assertThat(percentageDiscount, notNullValue());
        assertThat(percentageDiscount.getDiscountRate(), equalTo(DISCOUNT_RATE_EMPLOYEE));
        assertThat(valueDiscount, notNullValue());
        assertThat(valueDiscount.getValueStep(), equalTo(DISCOUNT_VALUE_STEP));
        assertThat(valueDiscount.getValueStepDiscount(), equalTo(DISCOUNT_VALUE));
    }

    @Test
    public void testGetDiscountsForLoyalCustomer() {
        //given
        final User user = new User();
        user.setRegistrationDate(LocalDate.now().minusDays(2 * 365 + 10));

        //when
        final PercentageDiscount percentageDiscount = userDiscountValidator.getPercentageDiscount(user);
        final ValueDiscount valueDiscount = userDiscountValidator.getValueDiscount(user);

        //then
        assertThat(percentageDiscount, notNullValue());
        assertThat(percentageDiscount.getDiscountRate(), equalTo(DISCOUNT_RATE_LOYAL_CUSTOMER));
        assertThat(valueDiscount, notNullValue());
        assertThat(valueDiscount.getValueStep(), equalTo(DISCOUNT_VALUE_STEP));
        assertThat(valueDiscount.getValueStepDiscount(), equalTo(DISCOUNT_VALUE));
    }

    @Test
    public void testGetDiscountsForCommonUser() {
        //given
        final User user = new User();
        user.setRegistrationDate(LocalDate.now().minusDays(10));

        //when
        final PercentageDiscount percentageDiscount = userDiscountValidator.getPercentageDiscount(user);
        final ValueDiscount valueDiscount = userDiscountValidator.getValueDiscount(user);

        //then
        assertThat(percentageDiscount, notNullValue());
        assertThat(percentageDiscount.getDiscountRate(), equalTo(DISCOUNT_RATE_DEFAULT));
        assertThat(valueDiscount, notNullValue());
        assertThat(valueDiscount.getValueStep(), equalTo(DISCOUNT_VALUE_STEP));
        assertThat(valueDiscount.getValueStepDiscount(), equalTo(DISCOUNT_VALUE));
    }

    @Test
    public void testGetDiscountsForUserWithoutRegistrationDate() {
        //given
        final User user = new User();

        //when
        final PercentageDiscount percentageDiscount = userDiscountValidator.getPercentageDiscount(user);
        final ValueDiscount valueDiscount = userDiscountValidator.getValueDiscount(user);

        //then
        assertThat(percentageDiscount, notNullValue());
        assertThat(percentageDiscount.getDiscountRate(), equalTo(DISCOUNT_RATE_DEFAULT));
        assertThat(valueDiscount, notNullValue());
        assertThat(valueDiscount.getValueStep(), equalTo(DISCOUNT_VALUE_STEP));
        assertThat(valueDiscount.getValueStepDiscount(), equalTo(DISCOUNT_VALUE));
    }

    @Test
    public void testGetDiscountsForInvalidUser() {
        //given
        final User user = null;

        //when
        final PercentageDiscount percentageDiscount = userDiscountValidator.getPercentageDiscount(user);
        final ValueDiscount valueDiscount = userDiscountValidator.getValueDiscount(user);

        //then
        assertThat(percentageDiscount, notNullValue());
        assertThat(percentageDiscount.getDiscountRate(), equalTo(DISCOUNT_RATE_DEFAULT));
        assertThat(valueDiscount, notNullValue());
        assertThat(valueDiscount.getValueStep(), equalTo(DISCOUNT_VALUE_STEP));
        assertThat(valueDiscount.getValueStepDiscount(), equalTo(DISCOUNT_VALUE));
    }

    @Test
    public void testCopyingBuilderCreator() {
        //given
        final UserDiscountValidator userDiscountValidatorCopy =
                DefaultUserDiscountValidator.newBuilder((DefaultUserDiscountValidator) userDiscountValidator)
                        .withValueDiscountStep(DISCOUNT_VALUE_STEP + 10.0)
                        .withValueDiscount(DISCOUNT_VALUE + 2.0)
                        .build();
        final User user = new User();

        //then
        final PercentageDiscount percentageDiscount = userDiscountValidatorCopy.getPercentageDiscount(user);
        final ValueDiscount valueDiscount = userDiscountValidatorCopy.getValueDiscount(user);

        assertThat(percentageDiscount, notNullValue());
        assertThat(percentageDiscount.getDiscountRate(), equalTo(DISCOUNT_RATE_DEFAULT));
        assertThat(valueDiscount, notNullValue());
        assertThat(valueDiscount.getValueStep(), equalTo(DISCOUNT_VALUE_STEP + 10.0));
        assertThat(valueDiscount.getValueStepDiscount(), equalTo(DISCOUNT_VALUE + 2.0));
    }
}
