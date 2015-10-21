package com.rdupuis.amikcal;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class HelloOpenGLES10SurfaceView extends GLSurfaceView {

	private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
	private float mPreviousX;
    private float mPreviousY;
	private HelloOpenGLES10Renderer mRenderer;
    public HelloOpenGLES10SurfaceView(Context context){
        super(context);
        mRenderer = new HelloOpenGLES10Renderer();
        
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(new HelloOpenGLES10Renderer());
       setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
     //  setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
}