package com.example.tanxiao.opengldemo.view;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.AttributeSet;

import com.example.tanxiao.opengldemo.utils.CameraUtil;
import com.example.tanxiao.opengldemo.utils.TextureHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by TX on 2018/2/8.
 * Class note:
 */

public class GlCameraView extends GLSurfaceView {

    public GlCameraView(Context context) {
        super(context);
        init();
    }

    public GlCameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setEGLContextClientVersion(2);
        setRenderer(new CameraRender(this));
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }

    private static class CameraRender implements Renderer {


        private SurfaceTexture surfaceTexture;

        private GlCameraView glCameraView;

        private SurfaceTexture.OnFrameAvailableListener onFrameAvailableListener = new SurfaceTexture.OnFrameAvailableListener() {
            @Override
            public void onFrameAvailable(SurfaceTexture surfaceTexture) {
                glCameraView.requestRender();
            }
        };
        private CameraDrawer mCameraDrawer;
        private float[] mtx = new float[16];

        public CameraRender(GlCameraView glCameraView) {
            this.glCameraView = glCameraView;
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            int textureId = TextureHelper.createOESTextureObject();
            surfaceTexture = new SurfaceTexture(textureId);
            surfaceTexture.setOnFrameAvailableListener(onFrameAvailableListener);
            mCameraDrawer = new CameraDrawer(glCameraView.getContext(),textureId);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES20.glViewport(0, 0, width, height);
            Matrix.setIdentityM(mtx, 0);
            CameraUtil.openCamera(surfaceTexture);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            if (surfaceTexture == null) {
                return;
            }
            surfaceTexture.updateTexImage();
            surfaceTexture.getTransformMatrix(mtx);
            mCameraDrawer.draw(mtx);
        }
    }
}
