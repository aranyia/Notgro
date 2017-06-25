package org.aranyia.notgro.model.discount;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class ValueDiscountTest {

    private ValueDiscount valueDiscount;

    @Test
    public void testAppliedToItem() {
        //given
        final double valueStep = 100;
        final double valueStepDiscount = 5;
        final double value = 205;
        final double valueDiscounted = 195;
        valueDiscount = new ValueDiscount(valueStep, valueStepDiscount);

        //when
        final double discountedItemPrice = valueDiscount.applyAsDouble(value);

        //then
        assertThat(discountedItemPrice, equalTo(valueDiscounted));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithInvalidValueStep() {
        valueDiscount = new ValueDiscount(-5,5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithNegativeValueStepDiscount() {
        valueDiscount = new ValueDiscount(100, -5);
    }
}
