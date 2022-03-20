package com.playking.primitives;


/**
 * Class describe ray, start with some point and has a direction.
 */
public class Ray {

    private final Point p0;
    private final Vector dir;

    /**
     * Constructor.
     * @param p0 start point
     * @param dir direction vector
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    public Point getP0() {
        return p0;
    }

    public Point getP0(double t) {
        return p0.add(dir.scale(t));
    }

    public Vector getDir() {
        return dir;
    }

    @Override
    public int hashCode() {
        return p0.hashCode() + dir.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Ray)) {
            return false;
        }
        Ray other = (Ray)obj;
        return p0.equals(other.p0) && dir.equals(other.dir);
    }

    /**
     * Create string with the pattern,
     * (x, y, z) -> (dirX, dirY, dirZ).
     * @return string contains : "(x, y, z) -> (dirX, dirY, dirZ)"
     */
    @Override
    public String toString() {
        return p0 + " -> " + dir;
    }
}
