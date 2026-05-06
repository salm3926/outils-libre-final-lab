package com.example.pricing;

/**
 * Represents an item in an order with a name, unit price, and quantity.
 * Replaces the raw double[] arrays from the bad design.
 */
public class OrderItem {
    private final String name;
    private final double unitPrice;
    private final int quantity;

    public OrderItem(String name, double unitPrice, int quantity) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Item name cannot be null or blank");
        }
        if (unitPrice < 0) {
            throw new IllegalArgumentException("Unit price cannot be negative");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotal() {
        return unitPrice * quantity;
    }
}
