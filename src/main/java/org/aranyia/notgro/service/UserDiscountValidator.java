package org.aranyia.notgro.service;

import org.aranyia.notgro.model.discount.PercentageDiscount;
import org.aranyia.notgro.model.discount.ValueDiscount;
import org.aranyia.notgro.model.user.User;

public interface UserDiscountValidator {

    <T extends User> PercentageDiscount getPercentageDiscount(T user);

    <T extends User> ValueDiscount getValueDiscount(T user);

}
