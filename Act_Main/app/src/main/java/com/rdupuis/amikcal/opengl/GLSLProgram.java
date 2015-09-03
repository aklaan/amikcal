package com.rdupuis.amikcal.opengl;


import android.app.Activity;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

public class GLSLProgram {

    //! activity
    private Activity mActivity;

    //! GLES20 Program
    private int mProgramObject;
    //! Vertex shader
    private int mVertexShader;
    // Fragment shader
    private int mFragmentShader;

    //! ProgramName (Vertex filename is ProgramName.vsh and Fragment filename is ProgramName.fsh)
    private String mProgramName;

    //! Matrix Model View Projection
    private float[] mMvp = new float[16];

    //! Rotation
    private float[] mRotation = new float[16];
    //! Projection
    private float[] mProjection = new float[16];

    // attibutes
    private int mPositionLoc;
    private int mColorLoc;
    private int mTexCoordLoc;
    // uniform
    private int mMvpLoc;
    private int mScreenSizeLoc;
    private int mTimeLoc;
    private int mMousePos;
    // sampler
    private int mTex0Loc;
    private int mTex1Loc;
    private int mTex2Loc;
    private int mTex3Loc;


    public GLSLProgram(Activity activity, String programName) {
        mActivity = activity;
        mProgramObject = 0;
        mVertexShader = 0;
        mFragmentShader = 0;
        mProgramName = programName;

        // create Program
        mProgramObject = GLES20.glCreateProgram();

        // set MVP to Identity
        //float mProjMatrix[] = new float[16];
        
        // on fabrique une matrice orthogonale qui va servir de matrice de projection
        Matrix.orthoM (mProjection, 0, -100, 100, -100, 100, -10.f, 100000.f);
        //Matrix.setIdentityM(mMvp, 0);


    }

    public void delete() {
        // delete Vertex shader
        if (mVertexShader!=0) {
            GLES20.glDeleteShader(mVertexShader);
        }
        // delete Fragment shader
        if (mFragmentShader!=0) {
            GLES20.glDeleteShader(mFragmentShader);
        }
        // delete a GLES20 program
        if (mProgramObject!=0) {
            GLES20.glDeleteProgram(mProgramObject);
        }
    }

    
    public boolean make() {
        String vShaderFilename = mProgramName + ".vsh";
        String fShaderFilename = mProgramName + ".fsh";
        // load and compile Shaders
        if (loadShaders(vShaderFilename, fShaderFilename)==false) {
            Log.e(this.getClass().getName(), "Cannot load shaders");
            return false;
        }
        // link
        link();

        // attributes
        mPositionLoc = GLES20.glGetAttribLocation(mProgramObject, "aPosition");
        mColorLoc = GLES20.glGetAttribLocation(mProgramObject, "aColor");
        mTexCoordLoc = GLES20.glGetAttribLocation(mProgramObject, "aTexCoord");
    
        // uniforms
        mMvpLoc = GLES20.glGetUniformLocation(mProgramObject, "uMvp");
        mScreenSizeLoc = GLES20.glGetUniformLocation(mProgramObject, "uScreenSize");
        mTimeLoc = GLES20.glGetUniformLocation(mProgramObject, "uTime");
        
        // samplers
        mTex0Loc = GLES20.glGetUniformLocation(mProgramObject, "tex0");
        mTex1Loc = GLES20.glGetUniformLocation(mProgramObject, "tex1");
        mTex2Loc = GLES20.glGetUniformLocation(mProgramObject, "tex2");
        mTex3Loc = GLES20.glGetUniformLocation(mProgramObject, "tex3");

        Log.i(this.getClass().getName(), "program compiled & linked: " + mProgramName);
        return true;
    }

    static float counter = 0;
    
    void use() {
        // use program
        GLES20.glUseProgram(mProgramObject);

        if (mMvpLoc != -1) {
            //Log.d(this.getClass().getName(),"setMvp");
            counter += 1.f;
            // on calcule la matrice "mRotation" a utiliser pour pivoter
            // d'un angle de x radian 
            // ici l'angle c'est counter
            // le pivot est au centre à 0,0,0
            Matrix.setRotateEulerM(mRotation,0, 0.f, 0.f, counter);
            
            // on calcule la nouvelle matrice de projection mMvp
            Matrix.multiplyMM(mMvp, 0, mProjection, 0, mRotation, 0);
            
            //on alimente la donnée UNIFORM mMvpLoc du programme OpenGL avec une matrice de 4 flotant
            GLES20.glUniformMatrix4fv(mMvpLoc, 1, false, mMvp, 0);
        }
        
        if (mScreenSizeLoc != -1) {
            float width = 480;
            float height = 800;
            GLES20.glUniform2f(mScreenSizeLoc, width, height);
        }
        
        if (mTimeLoc != -1) {
            float time = 0;
            // on alimente la donnée UNIFORM mTimeLoc avec un flotant time
            GLES20.glUniform1f(mTimeLoc, time);
        }

        if (mTex0Loc != -1) {
            //GLES20.glEnable(GLES20.GL_TEXTURE_2D);
        	
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, GLES20Renderer.mTex0);
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            // on alimente la donnée UNIFORM mTex0Loc avc un integer 0
            GLES20.glUniform1i(mTex0Loc, 0);
        }
    }

    public void draw(Vertices vertices, int mode, int count) {
        //appel la fonction qui passe à enable toutes les variables
    	enableVertexAttribArray(vertices);
        
    	//on se positionne au debut du Buffer
    	  	vertices.getIndices().position(0);
        
        
        
        /**
         * void glDrawElements(	GLenum mode,				Specifies what kind of primitives to render. 
         * 													 Symbolic constants GL_POINTS, GL_LINE_STRIP, GL_LINE_LOOP, GL_LINES, GL_TRIANGLE_STRIP, GL_TRIANGLE_FAN, 
         * 													 and GL_TRIANGLES are accepted.
 								GLsizei count,				Specifies the number of elements to be rendered.
 								GLenum type,				Specifies the type of the values in indices. Must be GL_UNSIGNED_BYTE or GL_UNSIGNED_SHORT.
 								const GLvoid * indices		Specifies a pointer to the location where the indices are stored.
 								);
 								
 			Prototype de la fonction	Description

			
				on Crée une géométrie avec le type de primitive "mode",
				 cette géométrie est composée de n vertex
				 les vertex à utiliser sont spécifié dans le tableau des indices
				 
				  par exemple :  on veut dessiner un carré, pour cela on va faire 2 triangles côte à côte
				  on utilise le mode GL_TRIANGLES
				  pour dessiner un triangle, il faut 3 sommets, 
				  on demande donc a dessiner la forme avec les groupe de 3 vertex, on va donc
				  lire notre tableau des indices par groupe de 3. 
				  pour savoir quels vertex sont à utiliser, on récupère les n° de vertex
				  référencé dans le tableau des indices
				  (pour simplifier les coordonées sont en 2D)
				  indice 0 - vertex 0 (-1,1)
				  indice 1 - vertex 1 (-1,-1)
				  indice 2 - vertex 2 (1,1)
				  --fin du premier triange
				  indice 3 - vertex 2 (1,1)
				  indice 4 - vertex 1 (-1,-1)
				  indice 5 - vertex 3 (1,-1) 
				  --fin du second triange					
 								
			on appel cette fonction avec : 
			mProgramme1.draw(mVertices, GLES20.GL_POINTS, MAX_POINTS);
         */
        
        
        GLES20.glDrawElements(mode, 3, GLES20.GL_UNSIGNED_SHORT, vertices.getIndices());
        disableVertexAttribArray();
    }

    private void enableVertexAttribArray(Vertices vertices) {
        //si l'adresse mémoire de l'objet désigné par mPositionLoc n'est pas vide
    	if (mPositionLoc != -1) {
           
    		// on va chercher le FloatBuffer où sont stocké les coordonnées des sommets
           // on se positionne au début du Buffer
        	vertices.getVertices().position(0);
            
             
  /**
   * define an array of generic vertex attribute data
   * void glVertexAttribPointer(	GLuint index,		-Specifies the index of the generic vertex attribute to be modified.
								GLint size,   			-Specifies the number of components per generic vertex attribute. Must be 1, 2, 3, 4.
					               							Additionally, the symbolic constant GL_BGRA is accepted by glVertexAttribPointer.
 											        		The initial value is 4.
 								GLenum type,			-Specifies the data type of each component in the array.
 								GLboolean normalized,	- For glVertexAttribPointer, specifies whether fixed-point data values should be normalized (GL_TRUE) 
 															or converted directly as fixed-point values (GL_FALSE) when they are accessed
 								GLsizei stride,			-Specifies the byte offset between consecutive generic vertex attributes. If stride is 0,
 								 							the generic vertex attributes are understood to be tightly packed in the array.
 								 							The initial value is 0
 								const GLvoid * pointer	-Specifies a offset of the first component of the first generic vertex attribute in the array in the 
 															data store of the buffer currently bound to the GL_ARRAY_BUFFER target. The initial value is 0
 								);
             * 
             * 
             */
            // on souhaite passer à opengl un tableaux de coordonées pour alimenter la aPosition des vertex.
            // dans l'objet désigné par l'adresse mPositionLoc (càd aPosition), on va écrire le contenu du FloatBuffer contenant les coordonées des sommets
        	// on spécifie comment OPENGL doit interpréter le buffer en spécifiant que chaque index du tableau comporte 3 Float d'une longeur P3FT2FR4FVertex_SIZE_BYTES
        	// autrement dit, a la lecture du Buffer, au bout de 3 Float d'une longeur P3FT2FR4FVertex_SIZE_BYTES, opengl cree un nouvel index.
            GLES20.glVertexAttribPointer(mPositionLoc, 3, GLES20.GL_FLOAT, false, P3FT2FR4FVertex.P3FT2FR4FVertex_SIZE_BYTES, vertices.getVertices());
            
            // on rend l'utilisation de mPositionLoc (c�d aPosition) possible par le moteur de rendu
            // dans le cas contraire, OPENGL n'utilisera pas les donn�es pass�e � aPosition et le fragment
            // se comporte comme si aPosition vaut 0.
            
            GLES20.glEnableVertexAttribArray(mPositionLoc);
        }
        if (mColorLoc != -1) {
            vertices.getVertices().position(0);
            GLES20.glVertexAttribPointer(mColorLoc, 4, GLES20.GL_FLOAT, false, P3FT2FR4FVertex.P3FT2FR4FVertex_SIZE_BYTES, vertices.getVertices());
           
            // ici, si on n'active pas le lien entre le programme java et le programme OPENGL, dans le programme OpenGL, aColor serra à zéro
            // et les formes seront noires / sans couleur.
            GLES20.glEnableVertexAttribArray(mColorLoc);
        }
        if (mTexCoordLoc != -1) {
            vertices.getVertices().position(3);
            GLES20.glVertexAttribPointer(mTexCoordLoc, 2, GLES20.GL_FLOAT, false, P3FT2FR4FVertex.P3FT2FR4FVertex_SIZE_BYTES, vertices.getVertices());
            GLES20.glEnableVertexAttribArray(mTexCoordLoc);
        }
    }

    private void disableVertexAttribArray() {
        if (mPositionLoc != -1) {
            //on a plus besoin des variables, on les retire du moteur de rendu
        	GLES20.glDisableVertexAttribArray(mPositionLoc);
        }
        if (mColorLoc != -1) {
            GLES20.glDisableVertexAttribArray(mColorLoc);
        }
        if (mTexCoordLoc != -1) {
            GLES20.glDisableVertexAttribArray(mTexCoordLoc);
        }
    }

    private boolean link() {
        if (mProgramObject==0) {
            Log.e(this.getClass().getName(), "Please create a GL program before Link shaders!");
            return false;
        }
        GLES20.glLinkProgram(mProgramObject);
        int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(mProgramObject, GLES20.GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] != GLES20.GL_TRUE) {
            Log.e(this.getClass().getName(), "Could not link program: ");
            Log.e(this.getClass().getName(), "logs:" + GLES20.glGetProgramInfoLog(mProgramObject));
            GLES20.glDeleteProgram(mProgramObject);
            mProgramObject = 0;
            return false;
        }
        return true;
    }

    private boolean loadShaders(String vertexFilename, String fragmentFilename) {
        if (mProgramObject==0) {
            Log.e(this.getClass().getName(), "No GLSL Program created!");
            return false;
        }

        // load vertex and fragment shader
        mVertexShader = loadShader(vertexFilename, GLES20.GL_VERTEX_SHADER);
        mFragmentShader = loadShader(fragmentFilename, GLES20.GL_FRAGMENT_SHADER);

        // if one of shader cannot be read return false
        if (mVertexShader==0 || mFragmentShader==0) {
            Log.e(this.getClass().getName(), "Shader doesn' compile");
            return false;
        }

        GLES20.glAttachShader(mProgramObject, mVertexShader);
        GLES20.glAttachShader(mProgramObject, mFragmentShader);
        return true;
    }

    /* load a Vertex or Fragment shader */
    private int loadShader(String filename, int shaderType) {
        String source = Util.readStringAsset(mActivity, filename);
        int shader = GLES20.glCreateShader(shaderType);
        if (shader != 0) {
            GLES20.glShaderSource(shader, source);
            GLES20.glCompileShader(shader);
            int[] compiled = new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
            if (compiled[0] == 0) {
                Log.e(this.getClass().getName(), "Could not compile shader " + shaderType + ":");
                Log.e(this.getClass().getName(), GLES20.glGetShaderInfoLog(shader));
                GLES20.glDeleteShader(shader);
                shader = 0;
            }
        }
        Log.i(this.getClass().getName(), "shader compiled:" + filename);
        return shader;
    }

}
