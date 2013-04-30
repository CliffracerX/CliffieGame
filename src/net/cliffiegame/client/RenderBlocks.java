package net.cliffiegame.client;

import net.cliffiegame.common.World;
import org.lwjgl.opengl.GL11;

/**
 * Class for rendering blocks.
 * 
 * @author Cliffie
 */
public class RenderBlocks {
	// CliffieGame cliffieGameInstance;
	World worldInst;
	static int displayListSolid;
	public float PX, PY, PZ;

	public RenderBlocks(World world) {
		this.worldInst = world;
	}

	/**
	 * Actually renders a normal block.
	 */
	public static void renderNormalBlock(float x, float y, float z,
			boolean clear, int id) {
		// GL11.glTranslatef(x, y, z);
		if (clear) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glLoadIdentity();
		}// GL20.glUseProgram(0);

		if (id == 1) {
			// GL11.glColor3f(0.2f, 1.0f, 0.3f);
		} else if (id == 2) {
			// GL11.glColor3f(0.5f, 0.5f, 0.5f);
		}
		// Bind the texture
		// GL13.glActiveTexture(GL13.GL_TEXTURE0);
		// System.out.println(texIds[id]);

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		if (!GL11.glIsEnabled(GL11.GL_CULL_FACE)) {
			GL11.glEnable(GL11.GL_CULL_FACE);
		}

		// displayListSolid = GL11.glGenLists(1);

		// GL11.glNewList(displayListSolid, GL11.GL_COMPILE);

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		GL11.glNormal3f(0, 1, 0);
		GL11.glTexCoord2d(0.0f, 0.0f);
		GL11.glVertex3f(1.0f + x, 1.0f + y, -1.0f + z);
		GL11.glTexCoord2d(1.0f, 0.0f);
		GL11.glVertex3f(-1.0f + x, 1.0f + y, -1.0f + z);
		GL11.glTexCoord2d(1.0f, 1.0f);
		GL11.glVertex3f(-1.0f + x, 1.0f + y, 1.0f + z);
		GL11.glTexCoord2d(0.0f, 1.0f);
		GL11.glVertex3f(1.0f + x, 1.0f + y, 1.0f + z);
		GL11.glNormal3f(0, -1, 0);
		GL11.glColor3f(0.35f, 0.35f, 0.35f);
		GL11.glTexCoord2d(0.0f, 0.0f);
		GL11.glVertex3f(1.0f + x, -1.0f + y, 1.0f + z);
		GL11.glTexCoord2d(1.0f, 0.0f);
		GL11.glVertex3f(-1.0f + x, -1.0f + y, 1.0f + z);
		GL11.glTexCoord2d(1.0f, 1.0f);
		GL11.glVertex3f(-1.0f + x, -1.0f + y, -1.0f + z);
		GL11.glTexCoord2d(0.0f, 1.0f);
		GL11.glVertex3f(1.0f + x, -1.0f + y, -1.0f + z);
		GL11.glNormal3f(0, 0, 1);
		GL11.glColor3f(0.8f, 0.8f, 0.8f);
		GL11.glTexCoord2d(0.0f, 0.0f);
		GL11.glVertex3f(1.0f + x, 1.0f + y, 1.0f + z);
		GL11.glTexCoord2d(1.0f, 0.0f);
		GL11.glVertex3f(-1.0f + x, 1.0f + y, 1.0f + z);
		GL11.glTexCoord2d(1.0f, 1.0f);
		GL11.glVertex3f(-1.0f + x, -1.0f + y, 1.0f + z);
		GL11.glTexCoord2d(0.0f, 1.0f);
		GL11.glVertex3f(1.0f + x, -1.0f + y, 1.0f + z);
		GL11.glNormal3f(0, 0, -1);
		GL11.glColor3f(0.5f, 0.5f, 0.5f);
		GL11.glTexCoord2d(1.0f, 1.0f);
		GL11.glVertex3f(1.0f + x, -1.0f + y, -1.0f + z);
		GL11.glTexCoord2d(0.0f, 1.0f);
		GL11.glVertex3f(-1.0f + x, -1.0f + y, -1.0f + z);
		GL11.glTexCoord2d(0.0f, 0.0f);
		GL11.glVertex3f(-1.0f + x, 1.0f + y, -1.0f + z);
		GL11.glTexCoord2d(1.0f, 0.0f);
		GL11.glVertex3f(1.0f + x, 1.0f + y, -1.0f + z);
		GL11.glNormal3f(1, 0, 0);
		GL11.glColor3f(0.6f, 0.6f, 0.6f);
		GL11.glTexCoord2d(0.0f, 0.0f);
		GL11.glVertex3f(-1.0f + x, 1.0f + y, 1.0f + z);
		GL11.glTexCoord2d(1.0f, 0.0f);
		GL11.glVertex3f(-1.0f + x, 1.0f + y, -1.0f + z);
		GL11.glTexCoord2d(1.0f, 1.0f);
		GL11.glVertex3f(-1.0f + x, -1.0f + y, -1.0f + z);
		GL11.glTexCoord2d(0.0f, 1.0f);
		GL11.glVertex3f(-1.0f + x, -1.0f + y, 1.0f + z);
		GL11.glNormal3f(-1, 0, 0);
		GL11.glColor3f(0.7f, 0.7f, 0.7f);
		GL11.glTexCoord2d(0.0f, 0.0f);
		GL11.glVertex3f(1.0f + x, 1.0f + y, -1.0f + z);
		GL11.glTexCoord2d(1.0f, 0.0f);
		GL11.glVertex3f(1.0f + x, 1.0f + y, 1.0f + z);
		GL11.glTexCoord2d(1.0f, 1.0f);
		GL11.glVertex3f(1.0f + x, -1.0f + y, 1.0f + z);
		GL11.glTexCoord2d(0.0f, 1.0f);
		GL11.glVertex3f(1.0f + x, -1.0f + y, -1.0f + z);
		GL11.glEnd();

		//GL11.glEndList();
		//GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_FILL);

		/*float size = 1.0F;

		    GL11.glBegin(GL11.GL_QUADS);
		    GL11.glTexCoord2d(0.0f * size, 0.0f * size);
		    GL11.glVertex3f(-1.0f * size, -1.0f * size, 1.0f * size);
		    GL11.glTexCoord2d(1.0f * size, 0.0f * size);
		    GL11.glVertex3f(1.0f * size, -1.0f * size, 1.0f * size);
		    GL11.glTexCoord2d(1.0f * size, 1.0f * size);
		    GL11.glVertex3f(1.0f * size, 1.0f * size, 1.0f * size);
		    GL11.glTexCoord2d(0.0f * size, 1.0f * size);
		    GL11.glVertex3f(-1.0f * size, 1.0f * size, 1.0f * size);
		    GL11.glTexCoord2d(1.0f * size, 0.0f * size);
		    GL11.glVertex3f(-1.0f * size, -1.0f * size, -1.0f * size);
		    GL11.glTexCoord2d(1.0f * size, 1.0f * size);
		    GL11.glVertex3f(-1.0f * size, 1.0f * size, -1.0f * size);
		    GL11.glTexCoord2d(0.0f * size, 1.0f * size);
		    GL11.glVertex3f(1.0f * size, 1.0f * size, -1.0f * size);
		    GL11.glTexCoord2d(0.0f * size, 0.0f * size);
		    GL11.glVertex3f(1.0f * size, -1.0f * size, -1.0f * size);
		    GL11.glTexCoord2d(0.0f * size, 1.0f * size);
		    GL11.glVertex3f(-1.0f * size, 1.0f * size, -1.0f * size);
		    GL11.glTexCoord2d(0.0f * size, 0.0f * size);
		    GL11.glVertex3f(-1.0f * size, 1.0f * size, 1.0f * size);
		    GL11.glTexCoord2d(1.0f * size, 0.0f * size);
		    GL11.glVertex3f(1.0f * size, 1.0f * size, 1.0f * size);
		    GL11.glTexCoord2d(1.0f * size, 1.0f * size);
		    GL11.glVertex3f(1.0f * size, 1.0f * size, -1.0f * size);
		    GL11.glTexCoord2d(1.0f * size, 1.0f * size);
		    GL11.glVertex3f(-1.0f * size, -1.0f * size, -1.0f * size);
		    GL11.glTexCoord2d(0.0f * size, 1.0f * size);
		    GL11.glVertex3f(1.0f * size, -1.0f * size, -1.0f * size);
		    GL11.glTexCoord2d(0.0f * size, 0.0f * size);
		    GL11.glVertex3f(1.0f * size, -1.0f * size, 1.0f * size);
		    GL11.glTexCoord2d(1.0f * size, 0.0f * size);
		    GL11.glVertex3f(-1.0f * size, -1.0f * size, 1.0f * size);
		    GL11.glTexCoord2d(1.0f * size, 0.0f * size);
		    GL11.glVertex3f(1.0f * size, -1.0f * size, -1.0f * size);
		    GL11.glTexCoord2d(1.0f * size, 1.0f * size);
		    GL11.glVertex3f(1.0f * size, 1.0f * size, -1.0f * size);
		    GL11.glTexCoord2d(0.0f * size, 1.0f * size);
		    GL11.glVertex3f(1.0f * size, 1.0f * size, 1.0f * size);
		    GL11.glTexCoord2d(0.0f * size, 0.0f * size);
		    GL11.glVertex3f(1.0f * size, -1.0f * size, 1.0f * size);
		    GL11.glTexCoord2d(0.0f * size, 0.0f * size);
		    GL11.glVertex3f(-1.0f * size, -1.0f * size, -1.0f * size);
		    GL11.glTexCoord2d(1.0f * size, 0.0f * size);
		    GL11.glVertex3f(-1.0f * size, -1.0f * size, 1.0f * size);
		    GL11.glTexCoord2d(1.0f * size, 1.0f * size);
		    GL11.glVertex3f(-1.0f * size, 1.0f * size, 1.0f * size);
		    GL11.glTexCoord2d(0.0f * size, 1.0f * size);
		    GL11.glVertex3f(-1.0f * size, 1.0f * size, -1.0f * size);
		    GL11.glEnd();*/
	}
}