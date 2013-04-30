package net.cliffiegame.common;

import java.util.Random;

/**
 * World class.  Contains all chunks in the world, and other things.
 * 
 * @author Cliffie
 */
public class World {
	/**
	 * Random, used for setting the seed.
	 */
	Random random = new Random();
	/**
	 * Size of the world.  Will be a square of chunks.
	 */
	public int WORLD_SIZE = 2;
	/**
	 * Array of chunks.
	 */
	public Chunk[][] chunks = new Chunk[WORLD_SIZE][WORLD_SIZE];
	/**
	 * Seed of the world.  
	 */
	public int seed;
    /**
     * Boolean for if the blocks should be melondimension blocks, and later for generating a floating-y melondimension.
     */
    private boolean isMelonDim;
	public boolean isCaelum;
	public boolean isWinter;

	/**
	 * Currently blank method for updating the world
	 */
	public void update() {
	}

	/**
	 * Creates the world!
	 */
	public void generate() {
		System.out.println("Generating world.  Please be patient, as older computers will take longer to create the chunks.");
		chunks = new Chunk[WORLD_SIZE][WORLD_SIZE];
		for(int x = 0; x < WORLD_SIZE; x++)
		{
			for(int y = 0; y < WORLD_SIZE; y++)
			{
				chunks[x][y]=new Chunk();
				if(chunks[x][y]!=null)
				{
				System.out.println("Generating chunks.  X: "+x+" Y: "+y);
				chunks[x][y].x=x;
				chunks[x][y].y=y;
				chunks[x][y].seed=seed;
				chunks[x][y].isMelonDim=this.isMelonDim;
				chunks[x][y].isCaelum=this.isCaelum;
				chunks[x][y].isSnow=this.isWinter;
				//chunks[x][y].octave0=this.octave0;
				//chunks[x][y].octave1=this.octave1;
				//chunks[x][y].octave2=this.octave2;
				//chunks[x][y].octave3=this.octave3;
				//chunks[x][y].octave4=this.octave4;
				chunks[x][y].generate();
				}
			}
		}
		/*blocks = new Block[WORLD_SIZE][WORLD_SIZE][WORLD_SIZE];
		
		for (int x = 0; x < WORLD_SIZE; x++) {
			for (int y = 0; y < WORLD_SIZE; y++) {
				for (int z = 0; z < WORLD_SIZE; z++) {
						blocks[x][y][z] = Block.blockAir;
				}
			}
		}*/
		//System.out.println("World filled with air.");
		/*
			blocks[0][WORLD_SIZE - 1][WORLD_SIZE - 1] = Block.blockCobble;
			blocks[1][WORLD_SIZE - 1][WORLD_SIZE - 1] = Block.blockGrass;
			blocks[2][WORLD_SIZE - 1][WORLD_SIZE - 1] = Block.blockDirt;
			blocks[3][WORLD_SIZE - 1][WORLD_SIZE - 1] = Block.blockStone;
			blocks[4][WORLD_SIZE - 1][WORLD_SIZE - 1] = Block.MELCobble;
			blocks[5][WORLD_SIZE - 1][WORLD_SIZE - 1] = Block.MELGrass;
			blocks[6][WORLD_SIZE - 1][WORLD_SIZE - 1] = Block.MELDirt;
			blocks[7][WORLD_SIZE - 1][WORLD_SIZE - 1] = Block.MELStone;
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
		/*System.out.println("Building terrain...");
		if(isMelonDim==false)
		{
		System.out.println("Isn't melondimension.");
		for (int i = 0; i < this.WORLD_SIZE; i++) {
            for (int j = 0; j < this.WORLD_SIZE; j++) {
                for (int k = 0; k < this.WORLD_SIZE; k++) {
                	if(i<WORLD_SIZE && j<WORLD_SIZE && k<WORLD_SIZE)
                	{
                    if (5.0D * checkOctave(i, k, this.octave0) + 15.0D * checkOctave(i, k, this.octave1) + 7.0D * checkOctave(i, k, this.octave2) + 1.0D * checkOctave(i, k, this.octave3) + 1.0D * checkOctave(i, k, this.octave4) >= j) {
                        this.blocks[i][j][k] = Block.blockStone;
                    }

                    if ((checkOctave(i, k, this.octave4) > 0.9D) && (10.0D * checkOctave(i, k, this.octave4) >= j)) {
                        this.blocks[i][j][k] = Block.blockStone;
                    }
                    }
                }
            }
        }
		System.out.println("Terrain formed...");
		System.out.println("Planting grass and dirt...");
        for (int i = 0; i < this.WORLD_SIZE; i++) {
            for (int j = 0; j < this.WORLD_SIZE; j++) {
                for (int k = 0; k < this.WORLD_SIZE; k++) {
                	if(j<WORLD_SIZE-1)
                	{
                    if ((this.blocks[i][j][k] == Block.blockStone) && (this.blocks[i][(j + 1)][k] == Block.blockAir)) {
                        this.blocks[i][j][k] = Block.blockGrass;
                        if (j > 1) {
                            this.blocks[i][(j - 1)][k] = Block.blockDirt;
                            this.blocks[i][(j - 2)][k] = Block.blockDirt;
                        }

                    }
                	}
                }

            }
        }
        System.out.println("World fully formed!");
		}
		if(isMelonDim==true)
		{
        System.out.println("Forming melonland...");
		for (int i = 0; i < this.WORLD_SIZE; i++) {
            for (int j = 0; j < this.WORLD_SIZE; j++) {
                for (int k = 0; k < this.WORLD_SIZE; k++) {
                	if(i<WORLD_SIZE && j<WORLD_SIZE && k<WORLD_SIZE)
                	{
                    if (5.0D * checkOctave(i, k, this.octave0) + 15.0D * checkOctave(i, k, this.octave1) + 7.0D * checkOctave(i, k, this.octave2) + 1.0D * checkOctave(i, k, this.octave3) + 1.0D * checkOctave(i, k, this.octave4) >= j) {
                        this.blocks[i][j][k] = Block.MELStone;
                    }

                    if ((checkOctave(i, k, this.octave4) > 0.9D) && (10.0D * checkOctave(i, k, this.octave4) >= j)) {
                        this.blocks[i][j][k] = Block.MELStone;
                    }
                	}
                }
            }
        }
		System.out.println("Melon-Terrain formed...");
		System.out.println("Planting melongrass and melondirt...");
        for (int i = 0; i < this.WORLD_SIZE; i++) {
            for (int j = 0; j < this.WORLD_SIZE; j++) {
                for (int k = 0; k < this.WORLD_SIZE; k++) {
                	if(j<WORLD_SIZE-1)
                	{
                    if ((this.blocks[i][j][k] == Block.MELStone) && (this.blocks[i][(j + 1)][k] == Block.blockAir)) {
                        this.blocks[i][j][k] = Block.MELGrass;
                        if (j > 1) {
                            this.blocks[i][(j - 1)][k] = Block.MELDirt;
                            this.blocks[i][(j - 2)][k] = Block.MELDirt;
                        }

                    }
                }
                }

            }
        }
        System.out.println("Melonland fully formed!");
        }*/
		/*
		 * for (int x = 0; x < WORLD_SIZE; x++) { for (int y = WORLD_SIZE-1; y <
		 * WORLD_SIZE; y++) { for (int z = 0; z < WORLD_SIZE; z++) {
		 * blocks[x][y][z] = Block.blockGrass; } } }
		 */
	}

	///**
	// * Returns the 3D block array.
	// * @return
	// */
	//public Block[][][] getBlocks() {
	//	return blocks;
//	}

	/**
	 * Returns blocktype at requested X, Y, and Z.
	 * @param x
	 * @param y
	 * @param z
	 * @return Block ID
	 */
	public int getBlock(int x, int y, int z, int cx, int cz) {
		//System.out.println("X is: "+x+" Y is: "+y+" Z is: "+z+" CX is: "+cx+" CZ is: "+cz+" ID of block is: "+this.chunks[cx][cz].blocks[x][y][z].id);
		return this.chunks[cx][cz].blocks[x][y][z];
	}

	/**
	 * Get world seed.
	 * @return World's seed
	 */
	public int getSeed() {
		return seed;
	}

	/**
	 * Set the seed of the world.
	 * @param seed
	 */
	public void setSeed(int seed) {
		this.seed = seed;
	}

	/**
	 * Gets if the world is melonland.
	 * @return if the world is melonlands.
	 */
	public boolean isMelonDim() {
		return isMelonDim;
	}

	/**
	 * Set the world's isMelonDim to the param isMelonDim.
	 * @param isMelonDim
	 */
	public void setMelonDim(boolean isMelonDim) {
		this.isMelonDim = isMelonDim;
	}
}
