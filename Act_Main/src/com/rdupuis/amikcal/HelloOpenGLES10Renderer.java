package com.rdupuis.amikcal;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.os.SystemClock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;

public class HelloOpenGLES10Renderer implements GLSurfaceView.Renderer {
	private FloatBuffer triangleVB;
	private FloatBuffer rectangleVB;
	private FloatBuffer cubeVB;
	private Float i;
	public float mAngleX;
	public float mAngleY;
	int inversion = 1;

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {

		// Set the background frame color
		gl.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);

		// initialize the triangle vertex array
		initShapes();
		i = 1f;

		// Enable use of vertex arrays
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glLineWidthx(10);

	}

	public void onDrawFrame(GL10 gl) {
		// Redraw background color
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		// Draw the triangle
		// d�finir la couleur des points

		gl.glColor4f(.5f, 0f, 0f, 0.0f);

		float StartDrawTime = SystemClock.uptimeMillis();

		// drawRectangle(gl);
		//drawPyramide(gl);
		drawCube(gl);

		float DrawingTime = SystemClock.uptimeMillis() - StartDrawTime;
		// System.out.println(DrawingTime);

		// pour une animation � 50fps on a besoin d'afficher 1 image toutes les
		// 20ms
		// si on met moins de 20ms pour tout dessiner, on va attendre le temps
		// restant
		// avant de refaire une image. ceci pour eviter de consommer de la cpu.

		if (DrawingTime < 20) {
			SystemClock.sleep((long) (20 - DrawingTime));
		}

	}

	public void drawPyramide(GL10 gl) {

		// Create a rotation for the triangle
		long time = SystemClock.uptimeMillis() % 4000L;
		float angle = 0.090f * ((int) time);

		gl.glLoadIdentity();
		gl.glRotatef(angle, 0.0f, 1.0f, 0.0f);

		// gl.glRotatef(mAngleX, 0, 1, 0);
		// gl.glRotatef(mAngleY, 1, 0, 0);
		// System.out.println("angleX" + mAngleX);
		gl.glScalef(5.0f, 5.0f, 5.0f);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, triangleVB);
		gl.glLineWidth(5.0f);
		
		gl.glDrawArrays(GL10.GL_LINES, 0, 12);

	}

	
	
	public void drawCube(GL10 gl) {

		// Create a rotation for the triangle
		long time = SystemClock.uptimeMillis() % 4000L;
		float angle = 0.090f * ((int) time);

		gl.glLoadIdentity();
		gl.glRotatef(angle, 1.0f, 1.0f, 1.0f);

		 //gl.glRotatef(mAngleX, 0, 1, 1);
		 //gl.glRotatef(mAngleY, 1, 0, 0);
		// System.out.println("angleX" + mAngleX);
		gl.glScalef(2.0f, 2.0f, 2.0f);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, cubeVB);
		gl.glLineWidth(5.0f);
		
		gl.glDrawArrays(GL10.GL_LINES, 0, 24);

		
		
		gl.glPushMatrix();
		// Create a rotation for the triangle
		angle = (float) (SystemClock.uptimeMillis() * .05);
		gl.glRotatef(angle, 0.0f, 0.0f, 1.0f);
		gl.glScalef(0.5f, 0.5f, 0.5f);
		///gl.glTranslatef(7, -7, 0f);
		gl.glDrawArrays(GL10.GL_LINES, 0, 24);
		gl.glPopMatrix();

		
	}

	
	
	public void drawRectangle(GL10 gl) {

		gl.glLoadIdentity();

		if (i > 8 || i < -8) {
			inversion = -inversion;
		}
		i += (float) .1 * inversion;
		gl.glTranslatef(0, i, 0f);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, rectangleVB);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 6);

		gl.glPushMatrix();
		// Create a rotation for the triangle
		long time = SystemClock.uptimeMillis() % 4000L;
		float angle = 0.090f * ((int) time);

		gl.glRotatef(angle, 0.0f, 0.0f, 1.0f);
		gl.glScalef(0.5f, 0.5f, 0f);
		gl.glTranslatef(5, -2, 0f);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 6);
		gl.glPopMatrix();

		gl.glPushMatrix();
		// Create a rotation for the triangle
		angle = (float) (SystemClock.uptimeMillis() * .5);
		gl.glRotatef(angle, 0.0f, 0.0f, 1.0f);
		gl.glScalef(0.2f, 0.2f, 0f);
		gl.glTranslatef(7, -7, 0f);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 6);
		gl.glPopMatrix();

		gl.glPushMatrix();
		// Create a rotation for the triangle
		angle = (float) (SystemClock.uptimeMillis() * 0.5 + SystemClock
				.uptimeMillis() * .01);
		gl.glRotatef(angle, 0.0f, 0.0f, 1.0f);
		gl.glScalef(0.5f, 0.5f, 0f);
		gl.glTranslatef(5, -5, 0);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 6);
		gl.glPopMatrix();

		gl.glPushMatrix();
		// Create a rotation for the triangle
		angle = (float) (SystemClock.uptimeMillis() * 0.1 + SystemClock
				.uptimeMillis() * .5);
		gl.glRotatef(angle, 0.0f, 0.0f, 1.0f);
		gl.glScalef(0.3f, 0.3f, 0f);
		gl.glTranslatef(-2, -2, 0);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 6);
		gl.glPopMatrix();

	}

	// la m�thode est appel� � la cr�ation de la surface et � sa modification
	public void onSurfaceChanged(GL10 gl, int width, int height) {

		// D�finir la fen�tre de vue, en g�n�ral la taille de l'�cran
		gl.glViewport(0, 0, width, height);

		// je me place sur la matrice de projection
		gl.glMatrixMode(GL10.GL_PROJECTION);

		// initialiser la matrice de projection
		gl.glLoadIdentity();

		// Le calcul du ratio pour ne pas d�former l'image
		// j'ai gal�r� longtemp avant de me rendre compte qu'il falait typer
		// l'expression width / height. si on divise deux entiers on ne r�cup�re
		// que la
		// partie enti�re.
		float ratio = (float) width / height;

		// glOrtho indique que l'on souhaite effectuer une projection othogonale
		// ce mode est pour la 2D uniquement
		gl.glOrthof(-6, 6, -6 / ratio, 6 / ratio, -5.01f, 100.0f);

		// glFrustum indique que l'on souhaite effectuer une projection en
		// persperctive
		// gl.glFrustumf(-1,1/ratio , -ratio, 1, 3, 7); // apply the projection
		// matrix

		// gl.glFrustumf fait la m�me chose que GLU.gluPerspective

		// *GLU.gluPerspective(gl, fovy, aspect, zNear, zFar)
		// GLU.gluPerspective(gl,50,0.575f,1,1000);

		// changement de point de vue de la cam�ra
		// GLU.gluLookAt(gl, 0, 0, 1, 0, 0, 0, 0, 1, 0);

		gl.glMatrixMode(GL10.GL_MODELVIEW);

	}

	private void initShapes() {

		float triangleCoords[] = {
				// X, Y, Z
				-0.5f, -0.5f, 0, 0.5f, -0.5f, 0,

				0.5f, -0.5f, 0, 0.0f, 0.5f, 0,

				0.0f, 0.5f, 0, -0.5f, -0.5f, 0,

				-0.5f, -0.5f, 0, 0.0f, -0.5f, .5f,

				0.5f, -0.5f, 0, 0.0f, -0.5f, .5f,

				0.0f, 0.5f, 0, 0.0f, -0.5f, .5f

		};

		float rectangleCoords[] = {
				// X, Y, Z
				-1f, -1f, 0, 1f, -1f, 0, 1f, 1f, 0,

				1f, 1f, 0, -1f, 1f, 0, -1f, -1f, 0

		};


		
		
		float cubeCoords[] = {
				// X, Y, Z
				//carr� premier plan
				-1f, -1f, 1f,
				 1f, -1f, 1f,
				 
		         1f, -1f, 1f,
				 1f,  1f, 1f,
				
				 1f,  1f, 1f,
				 -1f, 1f, 1f,
				 
				 -1f, 1f, 1f,
				 -1f, -1f, 1f,
				
				//carr� arri�re plan
				
				 -1f, -1f, -1f,
				 1f, -1f, -1f,
				 
		         1f, -1f, -1f,
				 1f,  1f, -1f,
				
				 1f,  1f, -1f,
				 -1f, 1f, -1f,
				 
				 -1f, 1f, -1f,
				 -1f, -1f, -1f,
				 
				 
				 //lignes des cot�s
				 
				 -1f, -1f, -1f,
				 -1f, -1f, 1f,
				 
				 1f, 1f, -1f,
				 1f, 1f, 1f,
				 
				 1f, -1f, 1f,
				 1f, -1f, -1f,
				 
				 -1f, 1f, -1f,
				 -1f, 1f, 1f
				 
							 
		};

		
		// initialize vertex Buffer for triangle
		ByteBuffer vbb = ByteBuffer.allocateDirect(
		// (# of coordinate values * 4 bytes per float)
				triangleCoords.length * 4);
		vbb.order(ByteOrder.nativeOrder());// use the device hardware's native
											// byte order
		triangleVB = vbb.asFloatBuffer(); // create a floating point buffer from
											// the ByteBuffer
		triangleVB.put(triangleCoords); // add the coordinates to the
										// FloatBuffer
		triangleVB.position(0); // set the buffer to read the first coordinate

		
		// initialize vertex Buffer for cube
			ByteBuffer vbb3 = ByteBuffer.allocateDirect(
			
						cubeCoords.length * 4);
				vbb3.order(ByteOrder.nativeOrder());// use the device hardware's native
													// byte order
				cubeVB = vbb3.asFloatBuffer(); // create a floating point buffer from
													// the ByteBuffer
				cubeVB.put(cubeCoords); // add the coordinates to the
												// FloatBuffer
				cubeVB.position(0); // set the buffer to read the first coordinate

		
		
		// initialize vertex Buffer for triangle
		ByteBuffer vbb2 = ByteBuffer.allocateDirect(
		// (# of coordinate values * 4 bytes per float)
				triangleCoords.length * 4);
		vbb2.order(ByteOrder.nativeOrder());// use the device hardware's native
											// byte order
		rectangleVB = vbb2.asFloatBuffer(); // create a floating point buffer
											// from the ByteBuffer
		rectangleVB.put(rectangleCoords); // add the coordinates to the
											// FloatBuffer
		rectangleVB.position(0); // set the buffer to read the first coordinate

	}

}