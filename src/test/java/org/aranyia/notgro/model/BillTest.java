package org.aranyia.notgro.model;

import org.aranyia.notgro.model.item.GroceryItem;
import org.aranyia.notgro.model.item.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class BillTest {

    @Test
    public void testConstructorWithItems() {
        //given
        final List<Item> items = Arrays.asList(new Item(10), new Item(20), new GroceryItem(5));

        //when
        final Bill bill = new Bill(items);

        //then
        assertThat(bill, notNullValue());
        assertThat(bill.getItems(), is(items));
    }
}
