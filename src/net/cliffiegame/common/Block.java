package net.cliffiegame.common;

/**
 * Basic class for blocks.  Contains all blocks in-game right now.
 * @author Cliffie
 */
public class Block
{
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
	public static Block blockAir = new Block(0);
	/**
	 * Cobblestone block.  Nice for construction.
	 */
	public static Block blockCobble = new Block(1);
	/**
	 * Grass block.  Surface of the world has this for a topper.
	 */
	public static Block blockGrass = new Block(2);
	/**
	 * Dirt block, filler block of the surface.
	 */
	public static Block blockDirt = new Block(3);
	/**
	 * Stone block.  Almost half the world is made of these in the current version.
	 */
	public static Block blockStone = new Block(4);
	/**
	 * Meloncobble block, melondimension item!
	 */
	public static Block MELCobble = new Block(5);
	/**
	 * Melongrass block, melondimension item!
	 */
	public static Block MELGrass = new Block(6);
	/**
	 * Melondirt block, melondimension item!
	 */
	public static Block MELDirt = new Block(7);
	/**
	 * Melonstone block, melondimension item!
	 */
	public static Block MELStone = new Block(8);
	/**
	 * Whitecobble block, caelum item!
	 */
	public static Block whitecobble = new Block(9);
	/**
	 * Skygrass block, caelum item!
	 */
	public static Block skygrass = new Block(10);
	/**
	 * Bluedirt block, caelum item!
	 */
	public static Block bluedirt = new Block(11);
	/**
	 * Whitestone block, caelum item!
	 */
	public static Block whitestone = new Block(12);
	/**
	 * Icey stone brick block.  Let it snow.
	 */
	public static Block icebrick = new Block(13);
	/**
	 * Grass block with snow on it.  Let it snow.
	 */
	public static Block snowgrass = new Block(14);
	/**
	 * Cold looking dirt.  Let it snow.
	 */
	public static Block colddirt = new Block(15);
	/**
	 * Icey stone block.  Let it snow.
	 */
	public static Block icestone = new Block(16);
}
