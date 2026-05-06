package com.example.pricing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for the refactored PricingEngine.
 */
public class PricingEngineTest {

    private PricingEngine engine;

    @BeforeEach
    void setUp() {
        engine = new PricingEngine();
    }

    // --- Subtotal Tests ---

    @Test
    void testSubtotalSingleItem() {
        List<OrderItem> items = List.of(new OrderItem("Widget", 10.0, 3));
        OrderResult result = engine.calculateOrder(items, CustomerType.REGULAR, DiscountCode.NONE);
        assertEquals(30.0, result.getSubtotal(), 0.01);
    }

    @Test
    void testSubtotalMultipleItems() {
        List<OrderItem> items = List.of(
            new OrderItem("Widget", 10.0, 2),
            new OrderItem("Gadget", 25.0, 1)
        );
        OrderResult result = engine.calculateOrder(items, CustomerType.REGULAR, DiscountCode.NONE);
        assertEquals(45.0, result.getSubtotal(), 0.01);
    }

    // --- Discount Code Tests ---

    @Test
    void testNoDiscount() {
        List<OrderItem> items = List.of(new OrderItem("Item", 100.0, 1));
        OrderResult result = engine.calculateOrder(items, CustomerType.REGULAR, DiscountCode.NONE);
        assertEquals(0.0, result.getDiscountAmount(), 0.01);
    }

    @Test
    void testSave10Discount() {
        List<OrderItem> items = List.of(new OrderItem("Item", 100.0, 1));
        OrderResult result = engine.calculateOrder(items, CustomerType.REGULAR, DiscountCode.SAVE10);
        assertEquals(10.0, result.getDiscountAmount(), 0.01);
    }

    @Test
    void testSave20Discount() {
        List<OrderItem> items = List.of(new OrderItem("Item", 100.0, 1));
        OrderResult result = engine.calculateOrder(items, CustomerType.REGULAR, DiscountCode.SAVE20);
        assertEquals(20.0, result.getDiscountAmount(), 0.01);
    }

    @Test
    void testSave30Discount() {
        List<OrderItem> items = List.of(new OrderItem("Item", 100.0, 1));
        OrderResult result = engine.calculateOrder(items, CustomerType.REGULAR, DiscountCode.SAVE30);
        assertEquals(30.0, result.getDiscountAmount(), 0.01);
    }

    // --- VIP Discount Tests ---

    @Test
    void testVipDiscount() {
        List<OrderItem> items = List.of(new OrderItem("Item", 100.0, 1));
        OrderResult result = engine.calculateOrder(items, CustomerType.VIP, DiscountCode.NONE);
        // VIP gets 15% discount = 15.0
        assertEquals(15.0, result.getDiscountAmount(), 0.01);
    }

    @Test
    void testVipWithSave10() {
        List<OrderItem> items = List.of(new OrderItem("Item", 100.0, 1));
        OrderResult result = engine.calculateOrder(items, CustomerType.VIP, DiscountCode.SAVE10);
        // 10% code + 15% VIP = 25% = 25.0
        assertEquals(25.0, result.getDiscountAmount(), 0.01);
    }

    // --- Tax Tests ---

    @Test
    void testTaxCalculation() {
        List<OrderItem> items = List.of(new OrderItem("Item", 100.0, 1));
        OrderResult result = engine.calculateOrder(items, CustomerType.REGULAR, DiscountCode.NONE);
        // Tax = 100 * 0.07 = 7.0
        assertEquals(7.0, result.getTax(), 0.01);
    }

    @Test
    void testTaxAfterDiscount() {
        List<OrderItem> items = List.of(new OrderItem("Item", 100.0, 1));
        OrderResult result = engine.calculateOrder(items, CustomerType.REGULAR, DiscountCode.SAVE10);
        // After 10% discount: 90.0, tax = 90 * 0.07 = 6.30
        assertEquals(6.30, result.getTax(), 0.01);
    }

    // --- Final Price Tests ---

    @Test
    void testFinalPriceNoDiscount() {
        List<OrderItem> items = List.of(new OrderItem("Item", 100.0, 1));
        OrderResult result = engine.calculateOrder(items, CustomerType.REGULAR, DiscountCode.NONE);
        // 100 + 7 = 107
        assertEquals(107.0, result.getFinalPrice(), 0.01);
    }

    @Test
    void testFinalPriceWithSave20() {
        List<OrderItem> items = List.of(new OrderItem("Item", 100.0, 1));
        OrderResult result = engine.calculateOrder(items, CustomerType.REGULAR, DiscountCode.SAVE20);
        // 100 - 20 = 80, tax = 80 * 0.07 = 5.60, final = 85.60
        assertEquals(85.60, result.getFinalPrice(), 0.01);
    }

    @Test
    void testFullScenarioVipSave20() {
        List<OrderItem> items = List.of(
            new OrderItem("Laptop", 999.99, 1),
            new OrderItem("Mouse", 29.99, 2)
        );
        OrderResult result = engine.calculateOrder(items, CustomerType.VIP, DiscountCode.SAVE20);

        double expectedSubtotal = 999.99 + 29.99 * 2; // 1059.97
        double expectedDiscount = expectedSubtotal * 0.20 + expectedSubtotal * 0.15; // 35% = 370.9895
        double priceAfterDiscount = expectedSubtotal - expectedDiscount; // 688.9805
        double expectedTax = priceAfterDiscount * 0.07;
        double expectedFinal = priceAfterDiscount + expectedTax;

        assertEquals(expectedSubtotal, result.getSubtotal(), 0.01);
        assertEquals(expectedDiscount, result.getDiscountAmount(), 0.01);
        assertEquals(expectedTax, result.getTax(), 0.01);
        assertEquals(expectedFinal, result.getFinalPrice(), 0.01);
    }
}
