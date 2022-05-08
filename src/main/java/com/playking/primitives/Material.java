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
     * Promotes transparency.
     */
    public Double3 kT;
    /**
     * Coefficient of reflection.
     */
    public Double3 kR;
    /**
     * Specular exponent.
     */
    public int nShininess;

    public Material() {
        this.kD = Double3.ZERO;
        this.kS = Double3.ZERO;
        this.kT = Double3.ZERO;
        this.kR = Double3.ZERO;
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

    public Material setKT(Double3 kT) {
        this.kT = kT;
        return this;
    }

    public Material setKT(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    public Material setKR(Double3 kR) {
        this.kR = kR;
        return this;
    }

    public Material setKR(double kR) {
        this.kR = new Double3(kR);
        return this;
    }

    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
