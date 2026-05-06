package com.example.pricing;

import java.util.List;

/**
 * REFACTORED Pricing Engine.
 * 
 * Improvements over PricingEngineBad:
 * - Clear, descriptive variable and method names
 * - Magic numbers replaced with named constants
 * - Logic separated into focused methods (Single Responsibility)
 * - Enums for CustomerType and DiscountCode (type safety)
 * - OrderItem and OrderResult classes (proper encapsulation)
 * - Input validation
 */
public class PricingEngine {

    private static final double TAX_RATE = 0.07;
    private static final double VIP_DISCOUNT_RATE = 0.15;

    /**
     * Calculates the final price for an order.
     */
    public OrderResult calculateOrder(List<OrderItem> items, CustomerType customerType, DiscountCode discountCode) {
        double subtotal = calculateSubtotal(items);
        double discountAmount = calculateDiscount(subtotal, customerType, discountCode);
        double priceAfterDiscount = subtotal - discountAmount;
        double tax = calculateTax(priceAfterDiscount);
        double finalPrice = priceAfterDiscount + tax;

        return new OrderResult(subtotal, discountAmount, tax, finalPrice);
    }

    /**
     * Calculates the subtotal by summing up all item totals.
     */
    private double calculateSubtotal(List<OrderItem> items) {
        double subtotal = 0;
        for (OrderItem item : items) {
            subtotal += item.getTotal();
        }
        return subtotal;
    }

    /**
     * Calculates the total discount based on code and customer type.
     */
    private double calculateDiscount(double subtotal, CustomerType customerType, DiscountCode discountCode) {
        double discount = subtotal * discountCode.getPercentage();

        if (customerType == CustomerType.VIP) {
            discount += subtotal * VIP_DISCOUNT_RATE;
        }

        return discount;
    }

    /**
     * Calculates the tax on the price after discount.
     */
    private double calculateTax(double amount) {
        return amount * TAX_RATE;
    }
}
