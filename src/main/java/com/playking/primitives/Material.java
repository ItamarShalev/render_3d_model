package com.playking.primitives;

/**
 * Defines the material of a geometry object.
 */
public class Material {

    /**
     * Diffusion coefficient.
     */
    public double kD;
    /**
     * Specular coefficient.
     */
    public double kS;
    /**
     * Specular exponent.
     */
    public int nShininess;

    public Material setKD(double kD) {
        this.kD = kD;
        return this;
    }

    public Material setKS(double kS) {
        this.kS = kS;
        return this;
    }

    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
