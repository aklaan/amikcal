package com.rdupuis.amikcal.opengl;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
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

	public final static int MAX_POINTS = 6;

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
			//tel quel , les vertex serront affiché dans le même ordre que leur création
			// mais on peut très biens définir un autre ordre
			// exemple dire que pour faire un carré, on fait 2 triangle avec vertex 0,1,2 et ,2,1,3
			mVertices.putIndice(i, i);
		}

		// on active le texturing 2D
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
					"pointsprite.png"));
		} catch (IOException e) {
			Log.e(this.getClass().getName(), "texture not found");
			return;
		}

		// on défini un buffer contenant tous les points de l'image
		// il en a (longeur x hauteur)
		// pour chaque point on a 4 bytes . 3 pour la couleur RVB et 1 pour
		// l'alpha
		ByteBuffer imageBuffer = ByteBuffer.allocateDirect(bitmap.getHeight()
				* bitmap.getWidth() * 4);

		// on indique que les bytes dans le buffer doivent
		// être enregistré selon le sens de lecture natif de l'architecture CPU
		// (de gaucha a droite ou vice et versa)
		imageBuffer.order(ByteOrder.nativeOrder());

		byte buffer[] = new byte[4];
		// pour chaque pixel composant l'image, on mémorise sa couleur et
		// l'alpha
		// dans le buffer
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
		// on se place a la position 0 du buffer - près à être lu plus tard
		imageBuffer.position(0);
		
		// on indique au moteur de rendu que les lignes doivent avoir une épaisseur
		// de 5 pixels
		GLES20.glLineWidth(5f);
		
		// charger la texture mémorisé dans le buffer dans le moteur de rendu comme 
		//étant la texture 0
		GLES20.glTexImage2D(GL10.GL_TEXTURE_2D, 0, GL10.GL_RGBA,
				bitmap.getWidth(), bitmap.getHeight(), 0, GL10.GL_RGBA,
				GL10.GL_UNSIGNED_BYTE, imageBuffer);

	}

	// @Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		//lorsqu'il y a une modification de la vue , on redéfinie la nouvelle
		// taille de la vue (par exemple quand on incline le téléphone et 
		// que l'on passe de la vue portait à la vue paysage
		GLES20.glViewport(0, 0, width, height);
	}

	// @Override
	public void onDrawFrame(GL10 gl) {
		//mTimer.addMark();
		//mTimer.logFPS(); // on veut mesurer les fps

		GLES20.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

		mProgramme1.use();
		
		// ici on demande à dessiner
		// en mode points GL_POINTS
		// on peu aussi le faire en mode line
		mProgramme1.draw(mVertices, GLES20.GL_TRIANGLES, MAX_POINTS);
	}

	private void checkGlError(String op) {
		int error;
		while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
			Log.e(this.getClass().getSimpleName(), op + ": glError " + error);
		}
	}

	// retourne un float aléatoire entre 0 et 1
	private float getRamdom() {
		float value = (float) (Math.random() * 2. - 1.);
		return value;
	}
}
