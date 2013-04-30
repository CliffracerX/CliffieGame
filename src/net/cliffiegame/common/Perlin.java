package net.cliffiegame.common;

import java.util.Random;

/**
 * Perlin noise class.  Meant for use in world generation.
 * @author Baggerboot
 * @author Modded by Cliffie
 */
public class Perlin {

    private long seed;
    private Random rand;
    private int octave;

    //BUGFREE
    public Perlin(long seed, int octave) {
        this.seed = seed;
        this.octave = octave;
        rand = new Random();
    }
    
    public final double grad(int par1, double par2, double par4)
    {
    	int j = par1 & 15;
        double d2 = (double)(1 - ((j & 8) >> 3)) * par2;
        double d3 = j < 4 ? 0.0D : (j != 12 && j != 14 ? par4 : par2);
        return ((j & 1) == 0 ? d2 : -d2) + ((j & 2) == 0 ? d3 : -d3);
    }

    public double getNoiseLevelAtPosition(int x, int z) {
        int xmin = (int) (double) x / octave;
        int xmax = xmin + 1;
        int zmin = (int) (double) z / octave;
        int zmax = zmin + 1;

        Coordinate a = new Coordinate(xmin, zmin);
        Coordinate b = new Coordinate(xmax, zmin);
        Coordinate c = new Coordinate(xmax, zmax);
        Coordinate d = new Coordinate(xmin, zmax);

        double ra = getRandomAtPosition(a);
        double rb = getRandomAtPosition(b);
        double rc = getRandomAtPosition(c);
        double rd = getRandomAtPosition(d);

        double ret = cosineInterpolate( //Interpolate Z direction
                cosineInterpolate((float) ra, (float) rb, (float) (x - xmin * octave) / octave), //Interpolate X1
                cosineInterpolate((float) rd, (float) rc, (float) (x - xmin * octave) / octave), //Interpolate X2
                ((float)z - (float)zmin * (float)octave) / (float)octave);
        return ret;
    }

    private float cosineInterpolate(float a, float b, float x) {
        float ft = (float) (x * 3);
        float f = (float) ((1f - Math.cos(ft)) * .5f);
        float ret = a * (1f - f) + b * f;
        return ret;
    }
    private double getRandomAtPosition(Coordinate coord) {
        double var = 10000 * (Math.sin(coord.getX()) + Math.cos(coord.getZ()) + Math.tan(seed));
        rand.setSeed((long) var);
        double ret = rand.nextDouble();
        return ret;
    }
}