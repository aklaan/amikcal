package com.rdupuis.amikcal.opengl;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**  */
public class Vertices {

    // max size of Vertices or Indices usable
    public static final int MAX_CAPACITY = 500000;

    public static final int FLOAT_SIZE = 4;
    public static final int SHORT_SIZE = 2;

    //! Vertices
    private FloatBuffer mVertices;
    //! indices
    private ShortBuffer mIndices;
    //! qty of vertices used
    private int mVerticesUsed;
    //! qty of indices used
    private int mIndicesUsed;
    
    public Vertices() {
        mVertices = ByteBuffer.allocateDirect(MAX_CAPACITY * FLOAT_SIZE).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mIndices = ByteBuffer.allocateDirect(MAX_CAPACITY * SHORT_SIZE).order(ByteOrder.nativeOrder()).asShortBuffer();
    }


    public void clear() {
        mVerticesUsed = 0;
        mIndicesUsed = 0;
    }

    public void putVertice(int index, P3FT2FR4FVertex vertex) {
        mVertices.position(P3FT2FR4FVertex.P3FT2FR4FVertex_SIZE * index);
        mVertices.put(vertex.mX).put(vertex.mY).put(vertex.mZ);
        mVertices.put(vertex.mU).put(vertex.mV);
        mVertices.put(vertex.mR).put(vertex.mG).put(vertex.mB).put(vertex.mA);
        mVertices.position(0);
    }
    
    public void putIndice(int index, int indice) {
        mIndices.position(index);
        mIndices.put((short)indice);
        mIndices.position(0);
    }

    public FloatBuffer getVertices() {
        return mVertices;
    }

    public ShortBuffer getIndices() {
        return mIndices;
    }

}