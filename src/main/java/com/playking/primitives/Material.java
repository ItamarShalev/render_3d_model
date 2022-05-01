package com.playking.primitives;

/**
 * Defines the material of a geometry object.
 */
public class Material {

    /**
     * Diffusion coefficient.
     */
    public Double3 kD;
    /**
     * Specular coefficient.
     */
    public Double3 kS;
    /**
     * Specular exponent.
     */
    public int nShininess;

    public Material() {
        this.kD = new Double3(0);
        this.kS = new Double3(0);
        this.nShininess = 0;
    }

    public Material setKD(Double3 kD) {
        this.kD = kD;
        return this;
    }

    public Material setKD(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    public Material setKS(Double3 kS) {
        this.kS = kS;
        return this;
    }

    public Material setKS(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
