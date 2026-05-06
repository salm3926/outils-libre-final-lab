package com.example.pricing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the OrderItem class.
 */
public class OrderItemTest {

    @Test
    void testOrderItemCreation() {
        OrderItem item = new OrderItem("Widget", 10.0, 3);
        assertEquals("Widget", item.getName());
        assertEquals(10.0, item.getUnitPrice(), 0.01);
        assertEquals(3, item.getQuantity());
    }

    @Test
    void testOrderItemTotal() {
        OrderItem item = new OrderItem("Widget", 10.0, 3);
        assertEquals(30.0, item.getTotal(), 0.01);
    }

    @Test
    void testOrderItemZeroQuantity() {
        OrderItem item = new OrderItem("Widget", 10.0, 0);
        assertEquals(0.0, item.getTotal(), 0.01);
    }

    @Test
    void testOrderItemNullName() {
        assertThrows(IllegalArgumentException.class, () -> new OrderItem(null, 10.0, 1));
    }

    @Test
    void testOrderItemBlankName() {
        assertThrows(IllegalArgumentException.class, () -> new OrderItem("  ", 10.0, 1));
    }

    @Test
    void testOrderItemNegativePrice() {
        assertThrows(IllegalArgumentException.class, () -> new OrderItem("Item", -5.0, 1));
    }

    @Test
    void testOrderItemNegativeQuantity() {
        assertThrows(IllegalArgumentException.class, () -> new OrderItem("Item", 10.0, -1));
    }
}
