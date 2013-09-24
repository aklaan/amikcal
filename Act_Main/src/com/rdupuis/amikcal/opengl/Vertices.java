package com.rdupuis.amikcal.opengl;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**  */
public class Vertices {

    // max size of Vertices or Indices usable
	// nombre de vertex maximal que l'on va générer
    public static final int MAX_CAPACITY = 500000;

    public static final int FLOAT_SIZE = 4; // on indique que le nombre de byte pour un float est de 4
                                            // un byte n'est pas obligatoirement égal à 8 bit 
    										// cela dépend du matériel. en général il est très souvant egal à 
    										// 8 bit ce qui fait qu'un byte est très souvent égal à un Octet
    										// mais comme ce n'est pas toujours le cas, on parle en byte et non en octet
    										// pour être précis.
    
    public static final int SHORT_SIZE = 2; //ici on indique qu'un short est codé sur 2 byte 
    										//soit généralement 2 octets
    										// soit : 00000000 00000000

    //! Vertices
    private FloatBuffer mVertices;			// définition d'un tableau de flotants
    //! indices
    private ShortBuffer mIndices;
    //! qty of vertices used
    private int mVerticesUsed;
    //! qty of indices used
    private int mIndicesUsed;
    
    
    
    // A propos du byteorder : 
    // les architectures CPU ne traitent pas tous les bit dans le même sens.
    // pour simplifier, certaines lisent de gauche à droite (SUM/IBM 370) et d'autre font l'inverse
    // l'instruction ByteOrder a pour but d'indiquer le sens d'insertion des bytes à respecter 
    // pour que les informations soient coreectement traitées par l'architecture.
    // nativeOrder indique qu'il faut utiliser l'ordre Natif de l'architecture qui va exécuter ce programme.
    
    
    // constructeur
    public Vertices() {
        mVertices = ByteBuffer.allocateDirect(MAX_CAPACITY * FLOAT_SIZE).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mIndices = ByteBuffer.allocateDirect(MAX_CAPACITY * SHORT_SIZE).order(ByteOrder.nativeOrder()).asShortBuffer();
    }


    public void clear() {
        mVerticesUsed = 0;
        mIndicesUsed = 0;
    }

    //setter vertices
    public void putVertice(int index, P3FT2FR4FVertex vertex) {
    	// la position physique en mémoire des bytes qui représentent le vertex 
    	// c'est la taille d'un vertex en bytes x l'index
    	
    	// ici on se positionne dans le buffer à l'endroit où l'on va ecrire le prochain vertex
        mVertices.position(P3FT2FR4FVertex.P3FT2FR4FVertex_SIZE * index);
        
        mVertices.put(vertex.mX).put(vertex.mY).put(vertex.mZ);
        mVertices.put(vertex.mU).put(vertex.mV);
        mVertices.put(vertex.mR).put(vertex.mG).put(vertex.mB).put(vertex.mA);
        
        // on se repositionne en 0 , prêt pour la relecture
       
        mVertices.position(0);
    }
    
    //setter indices
    public void putIndice(int index, int indice) {
        // on se positionne a l'index dans le buffer
    	// comme on a qu'un seul short a placer on ne fait pas comme dans putvertice
    	mIndices.position(index);
    	// on ecrit le short
        mIndices.put((short)indice);
        // on se repositionne en zéro
        mIndices.position(0);
    }

    //getter vertices
    public FloatBuffer getVertices() {
        return mVertices;
    }

    //getter indices
    public ShortBuffer getIndices() {
        return mIndices;
    }

}