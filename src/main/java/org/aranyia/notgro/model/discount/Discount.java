package org.aranyia.notgro.model.discount;

import java.util.function.ToDoubleFunction;

interface Discount<T> extends ToDoubleFunction<T> {
}
