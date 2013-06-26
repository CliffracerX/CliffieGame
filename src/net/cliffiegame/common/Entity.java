package net.cliffiegame.common;

/**
 * A entity in the world.  Could be a player, or mob, etc.
 * @author Cliffie
 */
public class Entity
{
	/**
 	* X location in world for this Entity.
 	*/
	public float x;
	
	/**
	 * Y location in world for this Entity.
	 */
	public float y;
	
	/**
	 * Z location in world for this Entity.
	 */
	public float z;
	
	/**
	 * The World this Entity is in.
	 */
	public World theWorld;
	
	public Entity(World world)
	{
		this.theWorld=world;
	}
}
