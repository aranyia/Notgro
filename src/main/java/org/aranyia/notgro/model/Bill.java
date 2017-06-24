package org.aranyia.notgro.model;

import org.aranyia.notgro.model.item.Item;

import java.util.List;

public class Bill {

    private final List<Item> items;

    private double sumTotal;

    public Bill(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public double getSumTotal() {
        return sumTotal;
    }

    public void setSumTotal(double sumTotal) {
        this.sumTotal = sumTotal;
    }
}
