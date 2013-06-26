package net.cliffiegame.common;

/**
 * Basic class for blocks.  Contains all blocks in-game right now.
 * @author Cliffie
 */
public class Block
{
	public static final char AIR = 0;
	public static final char COBBLE = 1;
	public static final char GRASS = 2;
	public static final char DIRT = 3;
	public static final char STONE = 4;
	
	/**
	 * Block ID.
	 */
	public int id;

	/**
	 * Main constructor for blocks.
	 * @param id
	 */
	public Block(int id)
	{
		this.id=id;
	}

	/**
	 * Air block.  Almost half the world is made of these in the current version.
	 */
	public static Block blockAir = new Block(AIR);
	/**
	 * Cobblestone block.  Nice for construction.
	 */
	public static Block blockCobble = new Block(COBBLE);
	/**
	 * Grass block.  Surface of the world has this for a topper.
	 */
	public static Block blockGrass = new Block(GRASS);
	/**
	 * Dirt block, filler block of the surface.
	 */
	public static Block blockDirt = new Block(DIRT);
	/**
	 * Stone block.  Almost half the world is made of these in the current version.
	 */
	public static Block blockStone = new Block(STONE);
}
