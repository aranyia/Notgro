package org.aranyia.notgro.model.discount;

import org.aranyia.notgro.model.item.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class PercentageDiscountTest {

    private PercentageDiscount<Item> percentageDiscount;

    @Test
    public void testAppliedToItem() {
        //given
        final double percentage = 0.5;
        final double itemPrice = 50;
        final Item item = new Item(itemPrice);
        percentageDiscount = new PercentageDiscount<>(percentage);

        //when
        final double discountedItemPrice = percentageDiscount.applyAsDouble(item);

        //then
        assertThat(discountedItemPrice, equalTo((1-percentage) * itemPrice));
    }

    @Test
    public void testGetDiscountRate() {
        //given
        final double expectedPercentage = 0.5;
        percentageDiscount = new PercentageDiscount<>(expectedPercentage);

        //when
        final double actualPercentage = percentageDiscount.getDiscountRate();

        //then
        assertThat(expectedPercentage, equalTo(actualPercentage));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithInvalidPercentage() {
        percentageDiscount = new PercentageDiscount<>(12);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithNegativePercentage() {
        percentageDiscount = new PercentageDiscount<>(-0.1);
    }
}
