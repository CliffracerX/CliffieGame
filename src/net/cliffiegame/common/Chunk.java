package net.cliffiegame.common;

import java.util.Random;

/**
 * We're moving to chunks.  Here we go...
 * @author Cliffie
 * @author Baggerboot (chunk generator code is from his abandoned project called Swiss Cheese.)
 */
public class Chunk
{
	/**
	 * X of chunk.  On a 2d grid.
	 */
	public int x=0;
	/**
	 * Y of chunk  On a 2d grid.
	 */
	public int y=0;
	/**
	 * Random, used for setting the seed.
	 */
	public Random random = new Random();
	/**
	 * Size of the chunk.  Chunks cubes at the moment (which could lead to infinite worlds in up and down.)!
	 */
	public static final int CHUNK_SIZE=16;
	/**
	 * 3D array of blocks in the chunk.  Most of them will be air blocks or stone blocks.
	 */
	public int[][][] blocks;
	/**
	 * Seed of the world.  
	 */
	public int seed = random.nextInt();
	/**
	 * Octave for terrain generation.
	 */
	public Perlin octave0 = new Perlin(this.seed, 64/2);
    /**
	 * Octave for terrain generation.
	 */
	public Perlin octave1 = new Perlin(this.seed, 32/2);
    /**
	 * Octave for terrain generation.
	 */
	public Perlin octave2 = new Perlin(this.seed, 16/2);
    /**
	 * Octave for terrain generation.
	 */
	public Perlin octave3 = new Perlin(this.seed, 8/2);
    /**
	 * Octave for terrain generation.
	 */
	public Perlin octave4 = new Perlin(this.seed, 4/2);
    
	/**
	 * Generate the chunk.  Uses modified version of chunk generator from Swiss-Cheese (By Baggerboot).
	 */
    public void generate() {
		System.out.println("Generating chunk...");
		blocks = new int[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
		
		for (int x = 0; x < CHUNK_SIZE; x++) {
			for (int y = 0; y < CHUNK_SIZE; y++) {
				for (int z = 0; z < CHUNK_SIZE; z++) {
						blocks[x][y][z] = Block.blockAir.id;
				}
			}
		}
		System.out.println("Chunk filled with air.");
		System.out.println("Building terrain...");
		for (int i = 0; i < this.CHUNK_SIZE; i++) {
            for (int j = 0; j < this.CHUNK_SIZE; j++) {
                for (int k = 0; k < this.CHUNK_SIZE; k++) {
                	if(i<CHUNK_SIZE && j<CHUNK_SIZE && k<CHUNK_SIZE)
                	{
                    if (5.0D * checkOctave(i, k, this.octave0) + 15.0D * checkOctave(i, k, this.octave1) + 7.0D * checkOctave(i, k, this.octave2) + 1.0D * checkOctave(i, k, this.octave3) + 1.0D * checkOctave(i, k, this.octave4) >= j) {
                        this.blocks[i][j][k] = Block.blockStone.id;
                    }

                    if ((checkOctave(i, k, this.octave4) > 0.9D) && (10.0D * checkOctave(i, k, this.octave4) >= j)) {
                        this.blocks[i][j][k] = Block.blockStone.id;
                    }
                    }
                }
            }
        }
		System.out.println("Terrain formed...");
		System.out.println("Planting grass and dirt...");
        for (int i = 0; i < this.CHUNK_SIZE; i++) {
            for (int j = 0; j < this.CHUNK_SIZE; j++) {
                for (int k = 0; k < this.CHUNK_SIZE; k++) {
                	//if(j<CHUNK_SIZE-1)
                	{
                		if ((this.blocks[i][j][k] == Block.blockStone.id) && (this.blocks[i][(j + 1)][k] == Block.blockAir.id)) {
                            this.blocks[i][j][k] = Block.blockGrass.id;
                            if (j > 1) {
                                this.blocks[i][(j - 1)][k] = Block.blockDirt.id;
                                this.blocks[i][(j - 2)][k] = Block.blockDirt.id;
                            }

                    }
                	}
                }

            }
        }
        System.out.println("World fully formed!");
	}
    
	private double checkOctave(int i, int k, Perlin octave) {
		return octave.getNoiseLevelAtPosition((i)*(1+this.x), (k)*(1+this.y))/2;
    }
}
