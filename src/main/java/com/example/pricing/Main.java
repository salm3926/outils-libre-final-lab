package com.example.pricing;

import java.util.List;

/**
 * Main entry point to demonstrate the Pricing Engine.
 */
public class Main {
    public static void main(String[] args) {
        PricingEngine engine = new PricingEngine();

        // Example 1: Regular customer with SAVE10
        List<OrderItem> items1 = List.of(
            new OrderItem("Laptop", 999.99, 1),
            new OrderItem("Mouse", 29.99, 2)
        );
        OrderResult result1 = engine.calculateOrder(items1, CustomerType.REGULAR, DiscountCode.SAVE10);
        System.out.println("=== Order 1: Regular + SAVE10 ===");
        System.out.println(result1);

        // Example 2: VIP customer with SAVE20
        List<OrderItem> items2 = List.of(
            new OrderItem("Monitor", 499.99, 1),
            new OrderItem("Keyboard", 79.99, 1),
            new OrderItem("USB Cable", 9.99, 3)
        );
        OrderResult result2 = engine.calculateOrder(items2, CustomerType.VIP, DiscountCode.SAVE20);
        System.out.println("\n=== Order 2: VIP + SAVE20 ===");
        System.out.println(result2);

        // Example 3: VIP customer with no discount
        List<OrderItem> items3 = List.of(
            new OrderItem("Headphones", 149.99, 1)
        );
        OrderResult result3 = engine.calculateOrder(items3, CustomerType.VIP, DiscountCode.NONE);
        System.out.println("\n=== Order 3: VIP + No Discount ===");
        System.out.println(result3);
    }
}
