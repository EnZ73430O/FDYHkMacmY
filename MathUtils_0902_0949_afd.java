// 代码生成时间: 2025-09-02 09:49:31
package com.example.mathutils;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utility class to perform mathematical calculations.
 */
@ApplicationScoped
public class MathUtils {

    // Constant for rounding precision
    private static final int PRECISION = 2;

    /**
     * Adds two numbers.
     *
     * @param a The first number
     * @param b The second number
     * @return The sum of the two numbers
     */
    public BigDecimal add(BigDecimal a, BigDecimal b) {
        return a.add(b);
    }

    /**
     * Subtracts one number from another.
     *
     * @param a The first number
     * @param b The second number
     * @return The difference of the two numbers
     */
    public BigDecimal subtract(BigDecimal a, BigDecimal b) {
        return a.subtract(b);
    }

    /**
     * Multiplies two numbers.
     *
     * @param a The first number
     * @param b The second number
     * @return The product of the two numbers
     */
    public BigDecimal multiply(BigDecimal a, BigDecimal b) {
        return a.multiply(b);
    }

    /**
     * Divides one number by another.
     *
     * @param a The dividend
     * @param b The divisor
     * @return The quotient rounded to a specified precision
     * @throws ArithmeticException If the divisor is zero
     */
    public BigDecimal divide(BigDecimal a, BigDecimal b) {
        if (b.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return a.divide(b, PRECISION, RoundingMode.HALF_UP);
    }

    /**
     * Calculates the square root of a number.
     *
     * @param number The number to find the square root of
     * @return The square root of the number
     * @throws IllegalArgumentException If the number is negative
     */
    public BigDecimal sqrt(BigDecimal number) {
        if (number.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Cannot calculate the square root of a negative number");
        }
        return number.sqrt(PRECISION, RoundingMode.HALF_UP);
    }
}
