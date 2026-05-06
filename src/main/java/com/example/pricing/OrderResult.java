package com.example.pricing;

/**
 * Holds the result of a pricing calculation.
 * Replaces the raw double[] return value from the bad design.
 */
public class OrderResult {
    private final double subtotal;
    private final double discountAmount;
    private final double tax;
    private final double finalPrice;

    public OrderResult(double subtotal, double discountAmount, double tax, double finalPrice) {
        this.subtotal = subtotal;
        this.discountAmount = discountAmount;
        this.tax = tax;
        this.finalPrice = finalPrice;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public double getTax() {
        return tax;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    @Override
    public String toString() {
        return String.format(
            "OrderResult { subtotal=%.2f, discount=%.2f, tax=%.2f, finalPrice=%.2f }",
            subtotal, discountAmount, tax, finalPrice
        );
    }
}
