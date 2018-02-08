package com.example.tanxiao.opengldemo.view;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.example.tanxiao.opengldemo.utils.CameraUtil;

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

        public CameraRender(GlCameraView glCameraView) {
            this.glCameraView = glCameraView;
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            int textureId = createOESTextureObject();
            surfaceTexture = new SurfaceTexture(textureId);
            surfaceTexture.setOnFrameAvailableListener(onFrameAvailableListener);
            mCameraDrawer = new CameraDrawer(textureId);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES20.glViewport(0,0,width,height);
            CameraUtil.openCamera(surfaceTexture);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            if (surfaceTexture == null) {
                return;
            }
            surfaceTexture.updateTexImage();
            float[] mtx = new float[16];
            surfaceTexture.getTransformMatrix(mtx);
            mCameraDrawer.draw(mtx);
        }
    }

    public static int createOESTextureObject() {
        int[] tex = new int[1];
        //生成一个纹理
        GLES20.glGenTextures(1, tex, 0);
        //将此纹理绑定到外部纹理上
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, tex[0]);
        //设置纹理过滤参数
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
        //解除纹理绑定
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 0);
        return tex[0];
    }
}
