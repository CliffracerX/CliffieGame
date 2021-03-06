package net.cliffiegame.client;

//import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
//import org.lwjgl.Sys;

import java.nio.ByteOrder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import net.cliffiegame.common.Chunk;
import net.cliffiegame.common.EntityPlayer;

import javax.imageio.ImageIO;
//import java.nio.FloatBuffer;
//import java.nio.IntBuffer;

import net.cliffiegame.common.Block;
//import net.cliffiegame.common.Ray;
import net.cliffiegame.common.World;

//import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
//import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
//import org.lwjgl.opengl.GL14;
//import org.lwjgl.opengl.GL15;
//import org.lwjgl.opengl.GL20;
//import org.lwjgl.opengl.GL21;
import org.lwjgl.opengl.GL30;
//import org.lwjgl.opengl.GL31;
//import org.lwjgl.opengl.GL32;
//import org.lwjgl.opengl.GL33;
//import org.lwjgl.opengl.GL40;
//import org.lwjgl.opengl.GL41;
//import org.lwjgl.opengl.GL42;
import org.lwjgl.util.glu.GLU;
//import org.lwjgl.util.vector.Vector;
import org.lwjgl.util.vector.Vector3f;
import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

/**
 * Main class of CliffieGame.
 * 
 * @author Cliffie
 */
public class CliffieGame {
	/** A profiler for calculating FPS, generating a delta, etc. */
	private Profiler profiler = new Profiler();
	
	/** The position of the player as a 3D vector (xyz). */
	private static Vector3f position = new Vector3f(-6, -43, -30);

	/**
	 * The rotation of the axis (where to the player looks). The X component
	 * stands for the rotation along the x-axis, where 0 is dead ahead, 180 is
	 * backwards, and 360 is automically set to 0 (dead ahead). The value must
	 * be between (including) 0 and 360. The Y component stands for the rotation
	 * along the y-axis, where 0 is looking straight ahead, -90 is straight up,
	 * and 90 is straight down. The value must be between (including) -90 and
	 * 90.
	 */
	private static Vector3f rotation = new Vector3f(30, -40, 0);
	private static final int maxLookUp = 85; // maximum angle at which the player can look up. */
	private static final int maxLookDown = -85; // minimum angle at which the player can look down.
	private int mouseSpeed = 1;
	private float walkingSpeed = 0.03125F * 8;
	private int rendermode = GL11.GL_QUADS; // Can be overridden by config.properties
	private int displaywidth = 768; // Can be overridden by config.properties
	private int displayheight = 512; // Can be overridden by config.properties
	
	World theWorld = new World();
	private EntityPlayer thePlayer = new EntityPlayer(theWorld);
	DisplayMode displayMode;
	/**
	 * Do the options thing when starting client.
	 */
	boolean doOptionsOnStart = true;
	private int skyColor;
	private int fogColor;
	private int cloudColor;
	public float fogr;
	public float fogg;
	public float fogb;
	private float skyr;
	private float skyg;
	private float skyb;

	public static void main(String[] args) throws LWJGLException {
		CliffieGame cg = new CliffieGame();
		cg.start(args);
	}

	public void start(String[] args) {
		try {
			loadConfig();
			createWindow();
			System.out.println("OpenGL version: "
					+ GL11.glGetString(GL11.GL_VERSION));
			initGL();
			run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadConfig() {
		int tmpint;

		Properties config = new Properties();
		try {
			// load a properties file
			config.load(new FileInputStream("config.properties"));
			this.theWorld.setSeed(Integer.parseInt(config
					.getProperty("worldseed")));
			this.theWorld.WORLD_SIZE = Integer.parseInt(config
					.getProperty("worldsize"));

			tmpint = Integer.parseInt(config.getProperty("displaywidth"));
			if (tmpint > 0 && tmpint < 10000) displaywidth = tmpint;

			tmpint = Integer.parseInt(config.getProperty("displayheight"));
			if (tmpint > 0 && tmpint < 10000) displayheight = tmpint;
			
			tmpint = Integer.parseInt(config.getProperty("rendermode"));
			if (tmpint == GL11.GL_POINTS || 
				tmpint == GL11.GL_LINES ||
				tmpint == GL11.GL_LINE_STRIP ||
				tmpint == GL11.GL_LINE_LOOP ||
				tmpint == GL11.GL_TRIANGLES ||
				tmpint == GL11.GL_TRIANGLE_STRIP ||
				tmpint == GL11.GL_TRIANGLE_FAN ||
				tmpint == GL11.GL_QUADS ||
				tmpint == GL11.GL_QUAD_STRIP ||
				tmpint == GL11.GL_POLYGON) {
				rendermode = tmpint;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public World getWorld() {
		return this.theWorld;
	}

	public void createWindow() throws Exception {
		Display.setFullscreen(false);
		this.theWorld.generate();
		/*
		 * DisplayMode d[] = Display.getAvailableDisplayModes(); for (int i = 0;
		 * i < d.length; i++) { if (d[i].getWidth() == 640 && d[i].getHeight()
		 * == 480 && d[i].getBitsPerPixel() == 32) { displayMode = d[i]; break;
		 * } }
		 */
		Display.setDisplayMode(new DisplayMode(displaywidth, displayheight));
		Display.setTitle("CliffieGame");
		Display.create();
		if (this.skyColor == 0) {
			this.skyColor = 10079487;
		}

		if (this.fogColor == 0) {
			this.fogColor = 16777215;
		}

		if (this.cloudColor == 0) {
			this.cloudColor = 16777215;
		}

		fogr = (float) (fogColor >> 16 & 255) / 255.0F;
		fogg = (float) (fogColor >> 8 & 255) / 255.0F;
		fogb = (float) (fogColor & 255) / 255.0F;
		skyr = (float) (skyColor >> 16 & 255) / 255.0F;
		skyg = (float) (skyColor >> 8 & 255) / 255.0F;
		skyb = (float) (skyColor & 255) / 255.0F;
		//System.out.println("Rs: " + fogr + " " + skyr);
		//System.out.println("Gs: " + fogg + " " + skyg);
		//System.out.println("Bs: " + fogb + " " + skyb);
	}

	private int[] texIds = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	String binpath = getClass().getClassLoader().getResource(".").getPath();

	void setupTextures() {
		// System.out.println(binpath);

		//texIds[Block.AIR]; // Nothing to be done here.
		texIds[Block.COBBLE] = this.loadPNGTexture(binpath + "/textures/cobble.png",
				GL13.GL_TEXTURE0);
		texIds[Block.GRASS] = this.loadPNGTexture(binpath + "/textures/topgrass.png",
				GL13.GL_TEXTURE0);
		texIds[Block.DIRT] = this.loadPNGTexture(binpath + "/textures/dirt.png",
				GL13.GL_TEXTURE0);
		texIds[Block.STONE] = this.loadPNGTexture(binpath + "/textures/stone.png",
				GL13.GL_TEXTURE0);
	}

	private static final float[] colors = new float[] { 0.2f, 0.5f, 1.0f, 1.0f };

	private int loadPNGTexture(String filename, int textureUnit) {
		ByteBuffer buf = null;
		int tWidth = 0;
		int tHeight = 0;

		try {
			// Open the PNG file as an InputStream
			InputStream in = new FileInputStream(filename);
			// Link the PNG decoder to this stream
			PNGDecoder decoder = new PNGDecoder(in);

			// Get the width and height of the texture
			tWidth = decoder.getWidth();
			tHeight = decoder.getHeight();

			// Decode the PNG file in a ByteBuffer
			buf = ByteBuffer.allocateDirect(4 * decoder.getWidth()
					* decoder.getHeight());
			decoder.decode(buf, decoder.getWidth() * 4, Format.RGBA);
			buf.flip();

			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		// Create a new texture object in memory and bind it
		int texId = GL11.glGenTextures();
		GL13.glActiveTexture(textureUnit);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);

		// All RGB bytes are aligned to each other and each component is 1 byte
		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

		// Upload the texture data and generate mip maps (for scaling)
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, tWidth, tHeight,
				0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

		// Setup the ST coordinate system
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S,
				GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
				GL11.GL_REPEAT);

		// Setup what to do when the texture has to be scaled
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,
				GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
				GL11.GL_LINEAR_MIPMAP_LINEAR);

		return texId;
	}

	public void initGL() {
		setupTextures();
		if (GL11.glIsEnabled(GL11.GL_CULL_FACE)) {
			GL11.glEnable(GL11.GL_CULL_FACE);
		}
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(3553);
		GL11.glEnable(2884);
		GL11.glEnable(GL11.GL_REPEAT);
		GL11.glEnable(GL11.GL_FOG);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glCullFace(1029);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glClearColor(skyr, skyg, skyb, 1.0f);
		GL11.glFogf(GL11.GL_FOG_START, 384f);
		GL11.glFogf(GL11.GL_FOG_END, 512f);
		GL11.glFogf(GL11.GL_FOG_DENSITY, 0.004f);
		ByteBuffer temp = ByteBuffer.allocateDirect(16);
		colors[0] = fogr + (skyr / 4);
		colors[1] = fogg + (skyg / 4);
		colors[2] = fogb + (skyb / 4);
		temp.order(ByteOrder.nativeOrder());
		temp.asFloatBuffer().put(colors).flip();

		GL11.glFogi(GL11.GL_FOG_MODE, 1); // Fog Mode
		GL11.glFog(GL11.GL_FOG_COLOR, temp.asFloatBuffer());
		// GL11.glFogf(GL11.GL_FOG_DENSITY, 0.012f);
		// How Dense Will The Fog Be
		GL11.glHint(GL11.GL_FOG_HINT, GL11.GL_DONT_CARE);
		// Fog Hint Value
		// GL11.glFogf(GL11.GL_FOG_START, 3f * 512f);
		// Fog Start Depth
		// GL11.glFogf(GL11.GL_FOG_END, 4f * 512f);
		// Fog End Depth
		GL11.glEnable(GL11.GL_FOG);
		// Enables GL_FOG
		// FloatBuffer colorsLocal = BufferUtils.createFloatBuffer(4);
		// colorsLocal.put(fogColor);
		// GL11.glFog(GL11.GL_FOG_COLOR, colorsLocal);

		GL11.glClearDepth(1.0);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glEnable(3008);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();

		GLU.gluPerspective(45.0f, (float) 768 / (float) 512, 0.1f, 1000000.0f);

		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
	}

	String currentTime;
	private final DateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd_HH.mm.ss");

	/**
	 * Version of CliffieGame at the moment.
	 */
	private String version = "0.02a";

	public void run() {
		int delta;

		while (!Display.isCloseRequested()) {
			delta = profiler.getDelta();
			processInput(delta);
			renderGL(delta);

			// Display.update() causes the buffer to be drawn to the screen and
			// keyboard and mouse IO to be processed.
			Display.update();
			Display.sync(60); // cap fps at 60fps

		}
		Display.destroy();
	}

	/**
	 * Update position, etc. based on mouse and keyboard input. 
	 * 
	 * TODO: Movement needs to use the delta parameter to control speed 
	 * independent of the FPS. [EJW]
	 * 
	 * @param delta
	 *            The number of milliseconds that have passed since the last
	 *            frame. This value can be used to move entities in the game in
	 *            a frame independent way. Meaning that regardless of how fast
	 *            or slow the fps is everything should move at a constant speed.
	 */
	public void processInput(int delta) {
		currentTime = this.dateFormat.format(new Date()); // Used for timestamp
															// on screencaps.

		// GL11.glTranslatef(0, 1, -5); // Move Right
		if (Mouse.isGrabbed()) {
			/*
			 * float mouseDX = Mouse.getDX() * mouseSpeed * 0.16f; float mouseDY
			 * = Mouse.getDY() * mouseSpeed * 0.16f; if (rotation.y + mouseDX >=
			 * 360) { rotation.y = rotation.y + mouseDX - 360; } else if
			 * (rotation.y + mouseDX < 0) { rotation.y = 360 - rotation.y +
			 * mouseDX; } else { rotation.y += mouseDX; } if (rotation.x -
			 * mouseDY >= maxLookDown && rotation.x - mouseDY <= maxLookUp) {
			 * rotation.x += -mouseDY; } else if (rotation.x - mouseDY <
			 * maxLookDown) { rotation.x = maxLookDown; } else if (rotation.x -
			 * mouseDY > maxLookUp) { rotation.x = maxLookUp; }
			 */
			float DX = Mouse.getDX() * mouseSpeed * 0.16F;
			float DY = Mouse.getDY() * mouseSpeed * 0.16F;
			if (rotation.y + DX >= 360) {
				rotation.y = rotation.y + DX - 360;
			} else if (rotation.y + DX < 0) {
				rotation.y = 360 - rotation.y + DX;
			} else {
				rotation.y += DX;
			}
			if (rotation.x - DY >= maxLookDown && rotation.x - DY <= maxLookUp) {
				rotation.x += -DY;
			}
			if (rotation.x - DY < maxLookDown) {
				rotation.x = maxLookDown;
			}
			if (rotation.x - DY > maxLookUp) {
				rotation.x = maxLookUp;
			}
			// Vector3f destination =
			// (Vector3f)getPickingRay(Display.getWidth() / 2,
			// Display.getHeight() / 2);
			// Ray rayPick = new Ray(new Vector3f(thePlayer.x,
			// thePlayer.y, thePlayer.z), destination);
			// if(rotation.x >= 270 && rotation.x <= 360)
			// rayPick.begin.x -= 8; //This is what about I'm
			// talking.
		}
		boolean keyUp = Keyboard.isKeyDown(Keyboard.KEY_UP)
				|| Keyboard.isKeyDown(Keyboard.KEY_W);
		boolean keyDown = Keyboard.isKeyDown(Keyboard.KEY_DOWN)
				|| Keyboard.isKeyDown(Keyboard.KEY_S);
		boolean keyLeft = Keyboard.isKeyDown(Keyboard.KEY_LEFT)
				|| Keyboard.isKeyDown(Keyboard.KEY_A);
		boolean keyRight = Keyboard.isKeyDown(Keyboard.KEY_RIGHT)
				|| Keyboard.isKeyDown(Keyboard.KEY_D);
		boolean flyUp = Keyboard.isKeyDown(Keyboard.KEY_SPACE);
		boolean flyDown = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
		boolean moveFaster = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
		boolean moveSlower = Keyboard.isKeyDown(Keyboard.KEY_TAB);
		boolean keyScreenshot = Keyboard.isKeyDown(Keyboard.KEY_F2);
		boolean keyExit = Keyboard.isKeyDown(Keyboard.KEY_ESCAPE);
		if (moveFaster && !moveSlower) {
			walkingSpeed *= 4f;
		}
		if (moveSlower && !moveFaster) {
			walkingSpeed /= 10f;
		}

		if (keyExit) {
			Display.destroy();
		}

		if (keyScreenshot) {
			GL11.glReadBuffer(GL11.GL_FRONT);
			int width = Display.getDisplayMode().getWidth();
			int height = Display.getDisplayMode().getHeight();
			int bpp = 4; // Assuming a 32-bit display with a byte each
							// for red, green, blue, and alpha.
			ByteBuffer buffer = BufferUtils.createByteBuffer(width * height
					* bpp);
			GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA,
					GL11.GL_UNSIGNED_BYTE, buffer);
			File temp = new File(this.binpath + "screenshots/");
			temp.mkdir();
			File file = new File(this.binpath + "screenshots/"
					+ this.currentTime + ".png"); // The file to save
													// to.
			String format = "PNG"; // Example: "PNG" or "JPG"
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);

			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					int i = (x + (width * y)) * bpp;
					int r = buffer.get(i) & 0xFF;
					int g = buffer.get(i + 1) & 0xFF;
					int b = buffer.get(i + 2) & 0xFF;
					image.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16)
							| (g << 8) | b);
				}
			}

			try {
				ImageIO.write(image, format, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (keyUp && keyRight && !keyLeft && !keyDown) {
			float angle = rotation.y + 45;
			float hypotenuse = (walkingSpeed);
			float adjacent = hypotenuse
					* (float) Math.cos(Math.toRadians(angle));
			float opposite = (float) (Math.sin(Math.toRadians(angle)) * hypotenuse);
			thePlayer.z += adjacent;
			thePlayer.x -= opposite;


		}
		if (keyUp && keyLeft && !keyRight && !keyDown) {
			float angle = rotation.y - 45;
			float hypotenuse = (walkingSpeed);
			float adjacent = hypotenuse
					* (float) Math.cos(Math.toRadians(angle));
			float opposite = (float) (Math.sin(Math.toRadians(angle)) * hypotenuse);
			thePlayer.z += adjacent;
			thePlayer.x -= opposite;


		}
		if (keyUp && !keyLeft && !keyRight && !keyDown) {
			float angle = rotation.y;
			float hypotenuse = (walkingSpeed);
			float adjacent = hypotenuse
					* (float) Math.cos(Math.toRadians(angle));
			float opposite = (float) (Math.sin(Math.toRadians(angle)) * hypotenuse);
			thePlayer.z += adjacent;
			thePlayer.x -= opposite;


		}
		if (keyDown && keyLeft && !keyRight && !keyUp) {
			float angle = rotation.y - 135;
			float hypotenuse = (walkingSpeed);
			float adjacent = hypotenuse
					* (float) Math.cos(Math.toRadians(angle));
			float opposite = (float) (Math.sin(Math.toRadians(angle)) * hypotenuse);
			thePlayer.z += adjacent;
			thePlayer.x -= opposite;


		}
		if (keyDown && keyRight && !keyLeft && !keyUp) {
			float angle = rotation.y + 135;
			float hypotenuse = (walkingSpeed);
			float adjacent = hypotenuse
					* (float) Math.cos(Math.toRadians(angle));
			float opposite = (float) (Math.sin(Math.toRadians(angle)) * hypotenuse);
			thePlayer.z += adjacent;
			thePlayer.x -= opposite;


		}
		if (keyDown && !keyUp && !keyLeft && !keyRight) {
			float angle = rotation.y;
			float hypotenuse = -(walkingSpeed);
			float adjacent = hypotenuse
					* (float) Math.cos(Math.toRadians(angle));
			float opposite = (float) (Math.sin(Math.toRadians(angle)) * hypotenuse);
			thePlayer.z += adjacent;
			thePlayer.x -= opposite;


		}
		if (keyLeft && !keyRight && !keyUp && !keyDown) {
			float angle = rotation.y - 90;
			float hypotenuse = (walkingSpeed);
			float adjacent = hypotenuse
					* (float) Math.cos(Math.toRadians(angle));
			float opposite = (float) (Math.sin(Math.toRadians(angle)) * hypotenuse);
			thePlayer.z += adjacent;
			thePlayer.x -= opposite;


		}
		if (keyRight && !keyLeft && !keyUp && !keyDown) {
			float angle = rotation.y + 90;
			float hypotenuse = (walkingSpeed);
			float adjacent = hypotenuse
					* (float) Math.cos(Math.toRadians(angle));
			float opposite = (float) (Math.sin(Math.toRadians(angle)) * hypotenuse);
			thePlayer.z += adjacent;
			thePlayer.x -= opposite;
		}
		if (flyUp && !flyDown) {
			double newPositionY = (walkingSpeed);
			thePlayer.y -= newPositionY;
		}
		if (flyDown && !flyUp) {
			double newPositionY = (walkingSpeed);
			thePlayer.y += newPositionY;
		}
		if (moveFaster && !moveSlower) {
			walkingSpeed /= 4f;
		}
		if (moveSlower && !moveFaster) {
			walkingSpeed *= 10f;
		}
		while (Mouse.next()) {
			if (Mouse.isButtonDown(0)) {
				Mouse.setGrabbed(true);
			}
			if (Mouse.isButtonDown(1)) {
				Mouse.setGrabbed(false);
			}
		}
		while (Keyboard.next()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
				position = new Vector3f(0, 0, 0);
				rotation = new Vector3f(0, 0, 0);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
				mouseSpeed += 1;
				System.out
						.println("Mouse speed changed to " + mouseSpeed + ".");
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_L)) {
				if (mouseSpeed - 1 > 0) {
					mouseSpeed -= 1;
					System.out.println("Mouse speed changed to " + mouseSpeed
							+ ".");
				}
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
				System.out.println("Walking speed changed to " + walkingSpeed
						+ ".");
				walkingSpeed += 1;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
				System.out.println("Walking speed changed to " + walkingSpeed
						+ ".");
				walkingSpeed -= 1;
			}
		}
		profiler.updateFPS(true, "CliffieGame "+this.version); // Update the FPS counter
	}

	/**
	 * Render OpenGL. LWJGL uses double buffering and everything will be
	 * drawn to an offscreen buffer.
	 * 
	 * @param delta
	 *            The number of milliseconds that have passed since the last
	 *            frame. This value can be used to move entities in the game in
	 *            a frame independent way. Meaning that regardless of how fast
	 *            or slow the fps is everything should move at a constant speed.
	 *            EJW: This is currently unused.
	 */
	public void renderGL(int delta) {
		int ID;
		int tmprendermode=rendermode;
		RenderBlocks.renderNormalBlock((float) 500, (float) 500, (float) 500, true, 1, rendermode);

		GL11.glRotatef(rotation.x, 1, 0, 0);
		GL11.glRotatef(rotation.y, 0, 1, 0);
		GL11.glRotatef(rotation.z, 0, 0, 1);

		GL11.glTranslatef(thePlayer.x + this.theWorld.WORLD_SIZE / 2, thePlayer.y
				+ this.theWorld.WORLD_SIZE / 4, thePlayer.z
				+ this.theWorld.WORLD_SIZE / 2);
		// Vector3f destination = (Vector3f) getPickingRay(
		// Display.getWidth() / 2, Display.getHeight() / 2);
		// Ray rayPick = new Ray(new Vector3f(thePlayer.x, thePlayer.y,
		// thePlayer.z), destination);
		// GL11.glBegin(GL11.GL_LINES);
		// GL11.glColor3f(1f, 1f, 1f);
		// GL11.glVertex3f(rayPick.begin.x, rayPick.begin.y,
		// rayPick.begin.z);
		// GL11.glColor3f(.3f, .3f, .3f);
		// GL11.glVertex3f(rayPick.dest.x, rayPick.dest.y,
		// rayPick.dest.z);
		// GL11.glEnd();
		GL11.glCullFace(GL11.GL_BACK);
		// GL11.glCullFace(GL11.GL_);
		for (int cx = 0; cx < this.theWorld.WORLD_SIZE; cx++) {
			for (int cz = 0; cz < this.theWorld.WORLD_SIZE; cz++) {
				for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
					for (int y = 0; y < Chunk.CHUNK_SIZE; y++) {
						for (int z = 0; z < Chunk.CHUNK_SIZE; z++) {
							ID = this.theWorld.getBlock(x, y, z, cx, cz);
							if (ID != 0) {
								// if(/*this.theWorld.getBlock(x, y+1,
								// z, cx, cz)==Block.blockAir ||
								// */this.theWorld.getBlock(x, y-1, z,
								// cx, cz)==Block.blockAir)// ||
								// this.theWorld.getBlock(x+1, y, z, cx,
								// cz)==Block.blockAir ||
								// this.theWorld.getBlock(x-1, y, z, cx,
								// cz)==Block.blockAir ||
								// this.theWorld.getBlock(x, y, z+1, cx,
								// cz)==Block.blockAir ||
								// this.theWorld.getBlock(x, y, z-1, cx,
								// cz)==Block.blockAir)
								// {
								// System.out.println("X: " + thePlayer.x
								// + " Y: " + thePlayer.y +
								// " Z: " + thePlayer.z);
								// System.out.println("Rendering a block at: "+x+" "+y+" "+z+" Of type: "+ID);
								RenderBlocks.renderNormalBlock(
										(float) (x + (cx * 16)) * 2,
										(float) y * 2,
										(float) (z + (cz * 16)) * 2, false, ID, 
										tmprendermode);
								// }
								/*
								 * else { RenderBlocks.renderNormalBlock(
								 * (float) (x+(cx*16)) * 2, (float) y * 2,
								 * (float) (z+(cz*16)) * 2, false,
								 * Block.MELCobble.id); }
								 */
							}
						}
					}
				}
			}
		}
	}

	/*
	 * public Vector getPickingRay(float cursorX, float cursorY) { IntBuffer
	 * viewport = ByteBuffer.allocateDirect((Integer.SIZE / 8) * 16)
	 * .order(ByteOrder.nativeOrder()).asIntBuffer(); FloatBuffer modelview =
	 * ByteBuffer .allocateDirect((Float.SIZE / 8) * 16)
	 * .order(ByteOrder.nativeOrder()).asFloatBuffer(); FloatBuffer projection =
	 * ByteBuffer .allocateDirect((Float.SIZE / 8) * 16)
	 * .order(ByteOrder.nativeOrder()).asFloatBuffer(); FloatBuffer
	 * pickingRayBuffer = ByteBuffer .allocateDirect((Float.SIZE / 8) * 3)
	 * .order(ByteOrder.nativeOrder()).asFloatBuffer(); // FloatBuffer zBuffer =
	 * //
	 * ByteBuffer.allocateDirect((Float.SIZE/8)*1).order(ByteOrder.nativeOrder
	 * ()).asFloatBuffer(); GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX,
	 * modelview); GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);
	 * GL11.glGetInteger(GL11.GL_VIEWPORT, viewport); float winX = (float)
	 * cursorX; // convert window coordinates to opengl coordinates (top left to
	 * bottom // left for (0,0) float winY = (float) viewport.get(3) - (float)
	 * cursorY;
	 * 
	 * // now unproject this to get the vector in to the screen // take the
	 * frustrm and unproject in to the screen // frustrum has a near plane and a
	 * far plane
	 * 
	 * // first the near vector GLU.gluUnProject(winX, winY, 0, modelview,
	 * projection, viewport, pickingRayBuffer); Vector3f nearVector = new
	 * Vector3f(pickingRayBuffer.get(0), pickingRayBuffer.get(1),
	 * pickingRayBuffer.get(2));
	 * 
	 * pickingRayBuffer.rewind();
	 * 
	 * // now the far vector GLU.gluUnProject(winX, winY, 1, modelview,
	 * projection, viewport, pickingRayBuffer); Vector3f farVector = new
	 * Vector3f(pickingRayBuffer.get(0), pickingRayBuffer.get(1),
	 * pickingRayBuffer.get(2));
	 * 
	 * // save the results in a vector, far-near return
	 * farVector.sub(nearVector, farVector, null).normalise(); }
	 */

}
