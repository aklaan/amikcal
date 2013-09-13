package com.rdupuis.amikcal.opengl;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * GLES20Renderer: the OGLES 2.0 Thread.
 */
public class GLES20Renderer implements GLSurfaceView.Renderer {

	public final static String TAG_ERROR = "CRITICAL ERROR";

	public final static int MAX_POINTS = 30;

	public static int mTex0;

	private Activity mActivity;

	private GLSLProgram mProgramme1;

	private Vertices mVertices;

	private Timer mTimer;

	GLES20Renderer(Activity activity) {
		mActivity = activity;
		mTimer = new Timer();
	}

	// @Override
	public void onSurfaceCreated(GL10 gl2, EGLConfig eglConfig) {
		// create a GLSLProgram
		mProgramme1 = new GLSLProgram(mActivity, "pointsprite");

		// build the program
		mProgramme1.make();

		// Vertices is our 3D object. It contains vertices and indices

		// tableau des vertices qui composent notre objet
		// dans cet exemple on défini MAX_POINTS point de manière aléaloire.
		mVertices = new Vertices();

		for (int i = 0; i < MAX_POINTS; i++) {
			// create a vertex
			P3FT2FR4FVertex v = new P3FT2FR4FVertex();

			v.setPos(getRamdom() * 80.f, getRamdom() * 80.f, 0);
			v.setColor(getRamdom(), getRamdom(), 1.f, 1.f);
			mVertices.putVertice(i, v);
			mVertices.putIndice(i, i);
		}

		// on active le texturing
		GLES20.glEnable(GLES20.GL_TEXTURE_2D);

		// create texture handle
		int[] textures = new int[1];

		// on génère un buffer texture
		GLES20.glGenTextures(1, textures, 0);

		mTex0 = textures[0];

		// on demande à opengl d'utiliser la première texture
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTex0);

		// set parameters
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,
				GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,
				GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
				GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
				GLES20.GL_CLAMP_TO_EDGE);

		// load a texture
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(mActivity.getAssets().open(
					"texture.png"));
		} catch (IOException e) {
			Log.e(TAG_ERROR, "Where is my texture");
			return;
		}

		// on défini un buffer contenant tous les points de l'image (longeur x
		// hauteur)
		// avec leur couleur et alpha (x4)
		ByteBuffer imageBuffer = ByteBuffer.allocateDirect(bitmap.getHeight()
				* bitmap.getWidth() * 4);

		imageBuffer.order(ByteOrder.nativeOrder());
		byte buffer[] = new byte[4];
		for (int i = 0; i < bitmap.getHeight(); i++) {
			for (int j = 0; j < bitmap.getWidth(); j++) {
				int color = bitmap.getPixel(j, i);
				buffer[0] = (byte) Color.red(color);
				buffer[1] = (byte) Color.green(color);
				buffer[2] = (byte) Color.blue(color);
				buffer[3] = (byte) Color.alpha(color);
				imageBuffer.put(buffer);
			}
		}
		imageBuffer.position(0);
		GLES20.glLineWidth(5f);
		GLES20.glTexImage2D(GL10.GL_TEXTURE_2D, 0, GL10.GL_RGBA,
				bitmap.getWidth(), bitmap.getHeight(), 0, GL10.GL_RGBA,
				GL10.GL_UNSIGNED_BYTE, imageBuffer);

	}

	// @Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		GLES20.glViewport(0, 0, width, height);
	}

	// @Override
	public void onDrawFrame(GL10 gl) {
		mTimer.addMark();
		mTimer.logFPS();

		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

		mProgramme1.use();
		//ici on demande à dessiner
		// en mode points GL_POINTS
		// on peu aussi le faire en mode line
		mProgramme1.draw(mVertices, GLES20.GL_POINTS, MAX_POINTS);
	}

	private void checkGlError(String op) {
		int error;
		while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
			Log.e("EllisMarkov", op + ": glError " + error);
		}
	}

	private float getRamdom() {
		float value = (float) (Math.random() * 2. - 1.);
		return value;
	}
}
