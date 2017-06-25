package org.aranyia.notgro.predicates;

import org.aranyia.notgro.model.item.GroceryItem;
import org.aranyia.notgro.model.item.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.function.Predicate;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class DiscountFilterTest {

    @Test
    public void testStaticBuilders() {
        //given
        final GroceryItem groceryItem = new GroceryItem(5);

        //when
        final Predicate<Item> discountFilter = DiscountFilter.from(GroceryItem.class);
        final Predicate<Item> discountFilterNegated = DiscountFilter.fromAsNegated(GroceryItem.class);

        final boolean discountFilterResult = discountFilter.test(groceryItem);
        final boolean discountFilterNegatedResult = discountFilterNegated.test(groceryItem);

        //then
        assertThat(discountFilterResult, is(Boolean.TRUE));
        assertThat(discountFilterNegatedResult, is(Boolean.FALSE));
    }

    @Test
    public void testWithMultipleTestCaseTypes() {
        //given
        final Item item = new Item(10);

        //when
        final Predicate<Item> discountFilter = DiscountFilter.from(GroceryItem.class, Item.class);

        final boolean discountFilterResult = discountFilter.test(item);

        //then
        assertThat(discountFilterResult, is(Boolean.TRUE));
    }
}
