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
     * Boolean for if the blocks should be melondimension blocks, and later for generating a floating-y melondimension.
     */
	public boolean isMelonDim;
	/**
	 * Boolean for if the blocks should be caelum blocks, and later for making a fancy caelum-like place.  :D
	 */
	public boolean isCaelum;
	/**
	 * Boolean for if the world should be snowy.  Expect winter worlds from this.
	 */
	public boolean isSnow;
    
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
		/*
			blocks[0][CHUNK_SIZE - 1][CHUNK_SIZE - 1] = Block.blockCobble;
			blocks[1][CHUNK_SIZE - 1][CHUNK_SIZE - 1] = Block.blockGrass;
			blocks[2][CHUNK_SIZE - 1][CHUNK_SIZE - 1] = Block.blockDirt;
			blocks[3][CHUNK_SIZE - 1][CHUNK_SIZE - 1] = Block.blockStone;
			blocks[4][CHUNK_SIZE - 1][CHUNK_SIZE - 1] = Block.MELCobble;
			blocks[5][CHUNK_SIZE - 1][CHUNK_SIZE - 1] = Block.MELGrass;
			blocks[6][CHUNK_SIZE - 1][CHUNK_SIZE - 1] = Block.MELDirt;
			blocks[7][CHUNK_SIZE - 1][CHUNK_SIZE - 1] = Block.MELStone;
			blocks[3][13][3] = Block.MELGrass;
			blocks[4][13][3] = Block.MELGrass;
			blocks[5][13][3] = Block.MELGrass;
			blocks[3][13][4] = Block.MELGrass;
			blocks[4][13][4] = Block.MELGrass;
			blocks[5][13][4] = Block.MELGrass;
			blocks[3][13][5] = Block.MELGrass;
			blocks[4][13][5] = Block.MELGrass;
			blocks[5][13][5] = Block.MELGrass;
			blocks[3][12][3] = Block.MELDirt;
			blocks[4][12][3] = Block.MELDirt;
			blocks[5][12][3] = Block.MELDirt;
			blocks[3][12][4] = Block.MELDirt;
			blocks[4][12][4] = Block.MELDirt;
			blocks[5][12][4] = Block.MELDirt;
			blocks[3][12][5] = Block.MELDirt;
			blocks[4][12][5] = Block.MELDirt;
			blocks[5][12][5] = Block.MELDirt;
			blocks[3][11][3] = Block.MELStone;
			blocks[4][11][3] = Block.MELStone;
			blocks[5][11][3] = Block.MELStone;
			blocks[3][11][4] = Block.MELStone;
			blocks[4][11][4] = Block.MELStone;
			blocks[5][11][4] = Block.MELStone;
			blocks[3][11][5] = Block.MELStone;
			blocks[4][11][5] = Block.MELStone;
			blocks[5][11][5] = Block.MELStone;
			blocks[3][10][3] = Block.MELStone;
			blocks[4][10][3] = Block.MELStone;
			blocks[5][10][3] = Block.MELStone;
			blocks[3][10][4] = Block.MELStone;
			blocks[4][10][4] = Block.MELStone;
			blocks[5][10][4] = Block.MELStone;
			blocks[3][10][5] = Block.MELStone;
			blocks[4][10][5] = Block.MELStone;
			blocks[5][10][5] = Block.MELStone;
		}*/
		System.out.println("Building terrain...");
		if(isMelonDim==false && isCaelum==false && isSnow==false)
		{
		System.out.println("Isn't melondimension.");
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
                    /*else
                    {
                    	if ((this.blocks[i][j][k] == Block.blockStone)) {
                            this.blocks[i][j][k] = Block.blockGrass;
                            if (j > 1) {
                                this.blocks[i][(j - 1)][k] = Block.blockDirt;
                                this.blocks[i][(j - 2)][k] = Block.blockDirt;
                            }

                        }
                    }*/
                	}
                }

            }
        }
        System.out.println("World fully formed!");
		}
		if(isMelonDim==true && isCaelum==false && isSnow==false)
		{
        System.out.println("Forming melonland...");
		for (int i = 0; i < this.CHUNK_SIZE; i++) {
            for (int j = 0; j < this.CHUNK_SIZE; j++) {
                for (int k = 0; k < this.CHUNK_SIZE; k++) {
                	if(i<CHUNK_SIZE && j<CHUNK_SIZE && k<CHUNK_SIZE)
                	{
                    if (5.0D * checkOctave(i, k, this.octave0) + 15.0D * checkOctave(i, k, this.octave1) + 7.0D * checkOctave(i, k, this.octave2) + 1.0D * checkOctave(i, k, this.octave3) + 1.0D * checkOctave(i, k, this.octave4) >= j) {
                        this.blocks[i][j][k] = Block.MELStone.id;
                    }

                    if ((checkOctave(i, k, this.octave4) > 0.9D) && (10.0D * checkOctave(i, k, this.octave4) >= j)) {
                        this.blocks[i][j][k] = Block.MELStone.id;
                    }
                	}
                }
            }
        }
		System.out.println("Melon-Terrain formed...");
		System.out.println("Planting melongrass and melondirt...");
        for (int i = 0; i < this.CHUNK_SIZE; i++) {
            for (int j = 0; j < this.CHUNK_SIZE; j++) {
                for (int k = 0; k < this.CHUNK_SIZE; k++) {
                	if(j<CHUNK_SIZE-1)
                	{
                    if ((this.blocks[i][j][k] == Block.MELStone.id) && (this.blocks[i][(j + 1)][k] == Block.blockAir.id)) {
                        this.blocks[i][j][k] = Block.MELGrass.id;
                        if (j > 1) {
                            this.blocks[i][(j - 1)][k] = Block.MELDirt.id;
                            this.blocks[i][(j - 2)][k] = Block.MELDirt.id;
                        }

                    }
                }
                	else
                	{
                		if ((this.blocks[i][j][k] == Block.MELStone.id)) {
                            this.blocks[i][j][k] = Block.MELGrass.id;
                            if (j > 1) {
                                this.blocks[i][(j - 1)][k] = Block.MELDirt.id;
                                this.blocks[i][(j - 2)][k] = Block.MELDirt.id;
                            }

                        }

                	}
                }

            }
        }
        System.out.println("Melonland fully formed!");
        }
		if(isMelonDim==false && isCaelum==true && isSnow==false)
		{
        System.out.println("Forming caelum...");
		for (int i = 0; i < this.CHUNK_SIZE; i++) {
            for (int j = 0; j < this.CHUNK_SIZE; j++) {
                for (int k = 0; k < this.CHUNK_SIZE; k++) {
                	if(i<CHUNK_SIZE && j<CHUNK_SIZE && k<CHUNK_SIZE)
                	{
                    if (5.0D * checkOctave(i, k, this.octave0) + 15.0D * checkOctave(i, k, this.octave1) + 7.0D * checkOctave(i, k, this.octave2) + 1.0D * checkOctave(i, k, this.octave3) + 1.0D * checkOctave(i, k, this.octave4) >= j) {
                        this.blocks[i][j][k] = Block.whitestone.id;
                    }

                    if ((checkOctave(i, k, this.octave4) > 0.9D) && (10.0D * checkOctave(i, k, this.octave4) >= j)) {
                        this.blocks[i][j][k] = Block.whitestone.id;
                    }
                	}
                }
            }
        }
		System.out.println("Melon-Terrain formed...");
		System.out.println("Planting melongrass and melondirt...");
        for (int i = 0; i < this.CHUNK_SIZE; i++) {
            for (int j = 0; j < this.CHUNK_SIZE; j++) {
                for (int k = 0; k < this.CHUNK_SIZE; k++) {
                	if(j<CHUNK_SIZE-1)
                	{
                    if ((this.blocks[i][j][k] == Block.whitestone.id) && (this.blocks[i][(j + 1)][k] == Block.blockAir.id)) {
                        this.blocks[i][j][k] = Block.skygrass.id;
                        if (j > 1) {
                            this.blocks[i][(j - 1)][k] = Block.bluedirt.id;
                            this.blocks[i][(j - 2)][k] = Block.bluedirt.id;
                        }

                    }
                }
                	else
                	{
                		if ((this.blocks[i][j][k] == Block.whitestone.id)) {
                            this.blocks[i][j][k] = Block.skygrass.id;
                            if (j > 1) {
                                this.blocks[i][(j - 1)][k] = Block.bluedirt.id;
                                this.blocks[i][(j - 2)][k] = Block.bluedirt.id;
                            }

                        }

                	}
                }

            }
        }
        System.out.println("Caelum fully formed!");
        }
		if(isMelonDim==false && isCaelum==false && isSnow==true)
		{
        System.out.println("Forming snow-world.");
		for (int i = 0; i < this.CHUNK_SIZE; i++) {
            for (int j = 0; j < this.CHUNK_SIZE; j++) {
                for (int k = 0; k < this.CHUNK_SIZE; k++) {
                	if(i<CHUNK_SIZE && j<CHUNK_SIZE && k<CHUNK_SIZE)
                	{
                    if (5.0D * checkOctave(i, k, this.octave0) + 15.0D * checkOctave(i, k, this.octave1) + 7.0D * checkOctave(i, k, this.octave2) + 1.0D * checkOctave(i, k, this.octave3) + 1.0D * checkOctave(i, k, this.octave4) >= j) {
                        this.blocks[i][j][k] = Block.icestone.id;
                    }

                    if ((checkOctave(i, k, this.octave4) > 0.9D) && (10.0D * checkOctave(i, k, this.octave4) >= j)) {
                        this.blocks[i][j][k] = Block.icestone.id;
                    }
                	}
                }
            }
        }
		System.out.println("Frozen terrain formed...");
		System.out.println("Plating frozen stuff....");
        for (int i = 0; i < this.CHUNK_SIZE; i++) {
            for (int j = 0; j < this.CHUNK_SIZE; j++) {
                for (int k = 0; k < this.CHUNK_SIZE; k++) {
                	if(j<CHUNK_SIZE-1)
                	{
                    if ((this.blocks[i][j][k] == Block.icestone.id) && (this.blocks[i][(j + 1)][k] == Block.blockAir.id)) {
                        this.blocks[i][j][k] = Block.snowgrass.id;
                        if (j > 1) {
                            this.blocks[i][(j - 1)][k] = Block.colddirt.id;
                            this.blocks[i][(j - 2)][k] = Block.colddirt.id;
                        }

                    }
                }
                	else
                	{
                		if ((this.blocks[i][j][k] == Block.icestone.id)) {
                            this.blocks[i][j][k] = Block.snowgrass.id;
                            if (j > 1) {
                                this.blocks[i][(j - 1)][k] = Block.colddirt.id;
                                this.blocks[i][(j - 2)][k] = Block.colddirt.id;
                            }

                        }

                	}
                }

            }
        }
        System.out.println("Frozen world formed!  Let it snow, let it snow, let it snow.");
        }
		/*
		 * for (int x = 0; x < CHUNK_SIZE; x++) { for (int y = CHUNK_SIZE-1; y <
		 * CHUNK_SIZE; y++) { for (int z = 0; z < CHUNK_SIZE; z++) {
		 * blocks[x][y][z] = Block.blockGrass; } } }
		 */
	}
    
	private double checkOctave(int i, int k, Perlin octave) {
		//if(this.x>0 && this.y>0)
        //return octave.grad(random.nextInt(1), i, k);
		//return random.nextDouble();
		//else
		return octave.getNoiseLevelAtPosition((i)*(1+this.x), (k)*(1+this.y))/2;
    }
}
