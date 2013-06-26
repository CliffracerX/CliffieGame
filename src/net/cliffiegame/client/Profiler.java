package net.cliffiegame.client;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

/**
 * Simple class for profiling and controlling game timing.
 * Originally based on this tutorial:
 * http://ninjacave.com/lwjglbasics4 
 * @author ericw
 *
 */
public class Profiler {
	/**
	 * Properties used in timing / the game loop.
	 */
	private long lastFrame; // time at last frame
	private int fps; // frames per second
	private long lastFPS; // last fps time

	/**
	 * Constructor:  Initialize timing, etc.
	 */
	public Profiler() {
		getDelta(); // Initialize timing.
		lastFPS = getTime(); // Initialize the FPS timer.
	}
	
	/**
	 * Calculate how many milliseconds have passed since last frame.
	 * 
	 * @return milliseconds passed since last frame
	 */
	public int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;
		return delta;
	}

	/**
	 * Get the accurate system time
	 * 
	 * @return The system time in milliseconds
	 */
	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * Calculate the FPS and set it in the title bar.
	 * 
	 * @param display If true, the window title will be set to the FPS.
	 */
	public void updateFPS(Boolean display, String name) {
		if (getTime() - lastFPS > 1000) {
			if (display) {
				Display.setTitle(name+" FPS: " + fps);
			}
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}
}