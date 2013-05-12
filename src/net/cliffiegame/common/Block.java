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
	public static final char MELCOBBLE = 5;
	public static final char MELGRASS = 6;
	public static final char MELDIRT = 7;
	public static final char MELSTONE = 8;
	public static final char WHITECOBBLE = 9;
	public static final char SKYGRASS = 10;
	public static final char BLUEDIRT = 11;
	public static final char WHITESTONE = 12;
	public static final char ICEBRICK = 13;
	public static final char SNOWGRASS = 14;
	public static final char COLDDIRT = 15;
	public static final char ICESTONE = 16;
	
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
	/**
	 * Meloncobble block, melondimension item!
	 */
	public static Block MELCobble = new Block(MELCOBBLE);
	/**
	 * Melongrass block, melondimension item!
	 */
	public static Block MELGrass = new Block(MELGRASS);
	/**
	 * Melondirt block, melondimension item!
	 */
	public static Block MELDirt = new Block(MELDIRT);
	/**
	 * Melonstone block, melondimension item!
	 */
	public static Block MELStone = new Block(MELSTONE);
	/**
	 * Whitecobble block, caelum item!
	 */
	public static Block whitecobble = new Block(WHITECOBBLE);
	/**
	 * Skygrass block, caelum item!
	 */
	public static Block skygrass = new Block(SKYGRASS);
	/**
	 * Bluedirt block, caelum item!
	 */
	public static Block bluedirt = new Block(BLUEDIRT);
	/**
	 * Whitestone block, caelum item!
	 */
	public static Block whitestone = new Block(WHITESTONE);
	/**
	 * Icey stone brick block.  Let it snow.
	 */
	public static Block icebrick = new Block(ICEBRICK);
	/**
	 * Grass block with snow on it.  Let it snow.
	 */
	public static Block snowgrass = new Block(SNOWGRASS);
	/**
	 * Cold looking dirt.  Let it snow.
	 */
	public static Block colddirt = new Block(COLDDIRT);
	/**
	 * Icey stone block.  Let it snow.
	 */
	public static Block icestone = new Block(ICESTONE);
}
