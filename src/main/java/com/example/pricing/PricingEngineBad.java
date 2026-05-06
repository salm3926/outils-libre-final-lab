package com.example.pricing;

import java.util.List;

/**
 * BAD DESIGN - Starter code (before refactoring).
 * 
 * Problems:
 * - Poor variable names (st, d, ct, dc, fp, t)
 * - All logic crammed into a single method
 * - Magic numbers (0.10, 0.20, 0.15, 0.07)
 * - String comparisons for types instead of enums
 * - No separation of concerns
 * - No input validation
 * - Raw arrays instead of proper objects
 */
public class PricingEngineBad {

    public double[] calculate(double[] prices, int[] quantities, String ct, String dc) {
        // Calculate subtotal
        double st = 0;
        for (int i = 0; i < prices.length; i++) {
            st += prices[i] * quantities[i];
        }

        // Apply discount code
        double d = 0;
        if (dc != null) {
            if (dc.equals("SAVE10")) {
                d = st * 0.10;
            } else if (dc.equals("SAVE20")) {
                d = st * 0.20;
            } else if (dc.equals("SAVE30")) {
                d = st * 0.30;
            }
        }

        // Apply VIP discount
        if (ct.equals("VIP")) {
            d += st * 0.15;
        }

        double fp = st - d;

        // Apply tax
        double t = fp * 0.07;
        fp = fp + t;

        // Return: [subtotal, discount, tax, finalPrice]
        return new double[]{st, d, t, fp};
    }
}
