package com.primitives;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Wrapper class for java.jwt.Color The constructors operate with any
 * non-negative RGB values. The colors are maintained without upper limit of
 * 255. Some additional operations are added that are useful for manipulating
 * light's colors.
 * @author Dan Zilberstein
 */
public class Color {
    public static final Color BLACK = new Color();
    public static final Color GREEN = new Color(0, 255, 0);
    public static final Color YELLOW = new Color(255, 255, 0);
    public static final Color RED = new Color(255, 0, 0);
    public static final Color BLUE = new Color(0, 0, 255);
    public static final double EPSILON = 0.1;
    /* The internal fields tx`o maintain RGB components as double numbers from 0 to whatever... */
    private final Double3 rgb;

    /**
     * Default constructor - to generate Black Color (privately).
     */
    private Color() {
        rgb = Double3.ZERO;
    }

    /**
     * Constructor to generate a color according to RGB components Each component in
     * range 0..255 (for printed white color) or more [for lights].
     * @param r Red component
     * @param g Green component
     * @param b Blue component
     * @throws IllegalArgumentException if one or more of the color are negative
     */
    public Color(double r, double g, double b) throws IllegalArgumentException {
        if (r < 0 || g < 0 || b < 0) {
            throw new IllegalArgumentException("Negative color component is illegal");
        }
        rgb = new Double3(r, g, b);
    }


    /**
     * Constructor to generate a color according to RGB components Each component in
     * range 0..255 (for printed white color) or more [for lights].
     * @param rgb triad of Red/Green/Blue components
     * @throws IllegalArgumentException if one or more of the color are negative
     */
    private Color(Double3 rgb) throws IllegalArgumentException {
        if (rgb.d1 < 0 || rgb.d2 < 0 || rgb.d3 < 0) {
            throw new IllegalArgumentException("Negative color component is illegal");
        }
        this.rgb = rgb;
    }

    /**
     * Constructor on base of java.awt.Color object.
     * @param other java.awt.Color's source object
     */
    public Color(java.awt.Color other) {
        rgb = new Double3(other.getRed(), other.getGreen(), other.getBlue());
    }

    public static Color average(List<Color> colors, int total) {
        Double3 averageValues;
        Double3 sum = Double3.ZERO;
        for (Color c : colors) {
            sum = sum.add(c.rgb);
        }
        averageValues = sum.reduce(total);
        return new Color(averageValues.d1, averageValues.d2, averageValues.d3);
    }

    public static boolean allEquals(List<Color> colors) {
        return IntStream
            .range(1, colors.size())
            .allMatch(index -> colors.get(0).equals(colors.get(index)));
    }

    /**
     * Color getter - returns the color after converting it into java.awt.Color
     * object During the conversion any component bigger than 255 is set to 255.
     * @return java.awt.Color object based on this Color RGB components
     */
    public java.awt.Color getColor() {
        int ir = (int)rgb.d1;
        int ig = (int)rgb.d2;
        int ib = (int)rgb.d3;
        return new java.awt.Color(ir > 255 ? 255 : ir, ig > 255 ? 255 : ig, ib > 255 ? 255 : ib);
    }

    /**
     * Operation of adding this and one or more other colors (by component).
     * @param colors one or more other colors to add
     * @return new Color object which is a result of the operation
     */
    public Color add(Color... colors) {
        double rr = rgb.d1;
        double rg = rgb.d2;
        double rb = rgb.d3;
        for (Color c : colors) {
            rr += c.rgb.d1;
            rg += c.rgb.d2;
            rb += c.rgb.d3;
        }
        return new Color(rr, rg, rb);
    }

    /**
     * Scale the color by a scalar.
     * @param k scale factor MUST be positive
     * @return new Color object which is the result of the operation
     * @throws IllegalArgumentException if k is negative
     */
    public Color scale(double k) throws IllegalArgumentException {
        if (k < 0.0) {
            throw new IllegalArgumentException("Can't scale a color by a negative number");
        }
        return new Color(rgb.scale(k));
    }

    /**
     * Scale the color by a scalar.
     * @param k scale factor
     * @return new Color object which is the result of the operation
     * @throws IllegalArgumentException if one or more of the numbers are negative
     */
    public Color scale(Double3 k) throws IllegalArgumentException {
        if (k.d1 < 0.0 || k.d2 < 0.0 || k.d3 < 0.0) {
            throw new IllegalArgumentException("Can't scale a color by a negative number");
        }
        return new Color(rgb.product(k));
    }

    /**
     * Scale the color by (1 / reduction factor).
     * @param k reduction factor MUST be bigger than 1
     * @return new Color object which is the result of the operation
     * @throws IllegalArgumentException if k is less than 1
     */
    public Color reduce(double k) throws IllegalArgumentException {
        if (k < 1) {
            throw new IllegalArgumentException("Can't scale a color by a by a number lower than 1");
        }
        return new Color(rgb.reduce(k));
    }

    /**
     * Scale the color by (1 / reduction factor).
     * @param k reduction factor
     * @return new Color object which is the result of the operation
     * @throws IllegalArgumentException if one or more of the numbers are less than 1
     */
    public Color reduce(Double3 k) throws IllegalArgumentException {
        if (k.d1 < 1.0 || k.d2 < 1.0 || k.d3 < 1.0) {
            throw new IllegalArgumentException("Can't scale a color by a by a number lower than 1");
        }
        return new Color(rgb.d1 / k.d1, rgb.d2 / k.d2, rgb.d3 / k.d3);
    }

    @Override
    public int hashCode() {
        return rgb != null ? rgb.hashCode() : 0;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Color color = (Color)other;
        return isSame(rgb.d1, color.rgb.d1) && isSame(rgb.d2, color.rgb.d2) &&
               isSame(rgb.d3, color.rgb.d3);
    }

    private boolean isSame(double x, double y) {
        return Math.abs(x - y) <= EPSILON;
    }
}
