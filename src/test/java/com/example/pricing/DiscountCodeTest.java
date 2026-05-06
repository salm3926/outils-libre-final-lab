package com.example.pricing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the DiscountCode enum.
 */
public class DiscountCodeTest {

    @Test
    void testNoneDiscount() {
        assertEquals(0.0, DiscountCode.NONE.getPercentage(), 0.01);
    }

    @Test
    void testSave10Discount() {
        assertEquals(0.10, DiscountCode.SAVE10.getPercentage(), 0.01);
    }

    @Test
    void testSave20Discount() {
        assertEquals(0.20, DiscountCode.SAVE20.getPercentage(), 0.01);
    }

    @Test
    void testSave30Discount() {
        assertEquals(0.30, DiscountCode.SAVE30.getPercentage(), 0.01);
    }
}
