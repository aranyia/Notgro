package org.aranyia.notgro.model.discount;

public class ValueDiscount implements Discount<Double> {

    private final double valueStep;

    private final double valueStepDiscount;

    public ValueDiscount(double valueStep, double valueStepDiscount) {
        this.valueStep = valueStep;
        this.valueStepDiscount = valueStepDiscount;
    }

    @Override
    public double applyAsDouble(Double value) {
        final double sumDiscount = Math.floor(value / valueStep) * valueStepDiscount;

        return value - sumDiscount;
    }
}
