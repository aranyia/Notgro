package org.aranyia.notgro.service;

import org.aranyia.notgro.model.Bill;
import org.aranyia.notgro.model.discount.PercentageDiscount;
import org.aranyia.notgro.model.discount.ValueDiscount;

public interface DiscountEngine {

    Bill applyDiscounts(Bill bill, PercentageDiscount percentageDiscount, ValueDiscount valueDiscount);

}
