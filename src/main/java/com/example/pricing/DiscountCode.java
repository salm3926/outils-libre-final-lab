package com.example.pricing;

/**
 * Represents available discount codes with their corresponding percentage.
 * Replaces magic strings and magic numbers from the bad design.
 */
public enum DiscountCode {
    NONE(0.0),
    SAVE10(0.10),
    SAVE20(0.20),
    SAVE30(0.30);

    private final double percentage;

    DiscountCode(double percentage) {
        this.percentage = percentage;
    }

    public double getPercentage() {
        return percentage;
    }
}
