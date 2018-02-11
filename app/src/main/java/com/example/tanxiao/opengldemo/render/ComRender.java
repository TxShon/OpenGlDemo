package com.example.tanxiao.opengldemo.render;

import android.opengl.GLSurfaceView;

import com.example.tanxiao.opengldemo.sample.IShape;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by TX on 2018/2/8.
 * Class note:
 */

public class ComRender implements GLSurfaceView.Renderer {

    private final IShape shape;

    public ComRender(IShape shape) {
        this.shape = shape;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        shape.onSurfaceCreated(gl, config);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        shape.onSurfaceChanged(gl, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        shape.onDrawFrame(gl);
    }
}
