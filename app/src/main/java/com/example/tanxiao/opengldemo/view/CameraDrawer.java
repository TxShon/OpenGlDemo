package com.example.tanxiao.opengldemo.view;

import android.content.Context;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.example.tanxiao.opengldemo.R;
import com.example.tanxiao.opengldemo.utils.GlCommonUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by TX on 2018/2/8.
 * Class note:
 */

public class CameraDrawer {

    private int textureId;

    private static final String VERTEX_SHADER = "" +
            //顶点坐标
            "attribute vec4 aPosition;\n" +
            // MVP 的变换矩阵（整体变形）
            "uniform mat4 uMVPMatrix;\n" +
            //纹理矩阵
            "uniform mat4 uTextureMatrix;\n" +
            //自己定义的纹理坐标
            "attribute vec4 aTextureCoordinate;\n" +
            //传给片段着色器的纹理坐标
            "varying vec2 vTextureCoord;\n" +
            "void main()\n" +
            "{\n" +
            //根据自己定义的纹理坐标和纹理矩阵求取传给片段着色器的纹理坐标
            "  vTextureCoord = (uTextureMatrix * aTextureCoordinate).xy;\n" +
            "  gl_Position = uMVPMatrix*aPosition;\n" +
            "}\n";

    private static final String FRAGMENT_SHADER = "" +
            //使用外部纹理必须支持此扩展
            "#extension GL_OES_EGL_image_external : require\n" +
            "precision mediump float;\n" +
            //外部纹理采样器
            "uniform samplerExternalOES uTextureSampler;\n" +
            "varying vec2 vTextureCoord;\n" +
            "void main() \n" +
            "{\n" +
            //获取此纹理（预览图像）对应坐标的颜色值
            "  gl_FragColor = texture2D(uTextureSampler, vTextureCoord);\n" +
            //"  vec4 vCameraColor = texture2D(uTextureSampler, vTextureCoord);\n" +
            //            求此颜色的灰度值
            //"  float fGrayColor = (0.3*vCameraColor.r + 0.59*vCameraColor.g + 0.11*vCameraColor.b);\n" +
            //将此灰度值作为输出颜色的RGB值，这样就会变成黑白滤镜
            //"  gl_FragColor = vec4(fGrayColor, fGrayColor, fGrayColor, 0.5);\n" +
            "}\n";

    private static final float position[] = {
            -1.0f, -1.0f,
            -1.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, -1.0f,};

    private static final float textureVertices[] = {
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f,};

    private final int BYTES_PER_FLOAT = 4;

    private float[] mMVPMatrix = new float[16];
    private FloatBuffer positionBuffer;
    private FloatBuffer textureVerticesBuffer;
    private int mPositionHandle;
    private int mTextureCoordHandle;
    private int mMVPMatrixHandle;
    private int mProgramHandle;
    private int mTextureHandle;
    private int mTextureMatrixHandle;
    private int vertexShaderResource;
    private int fragmentShaderResource;
    private Context context;


    public CameraDrawer(Context context, int textureId) {
        this(context, textureId, R.raw.preview_vertex_shader, R.raw.preview_fragment_shader);
    }

    public CameraDrawer(Context context, int textureId, int vertexShaderResource, int fragmentShaderResource) {
        this.context = context;
        this.textureId = textureId;
        this.vertexShaderResource = vertexShaderResource;
        this.fragmentShaderResource = fragmentShaderResource;
        initGl();
    }


    private void initGl() {

        positionBuffer = ByteBuffer.allocateDirect(position.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        positionBuffer.put(position).position(0);

        textureVerticesBuffer = ByteBuffer.allocateDirect(textureVertices.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        textureVerticesBuffer.put(textureVertices).position(0);

        // 初始化正交矩阵
        Matrix.setIdentityM(mMVPMatrix, 0);

        mProgramHandle = GlCommonUtil.createProgram(GlCommonUtil.readShaderFromSource(context, vertexShaderResource),
                GlCommonUtil.readShaderFromSource(context, fragmentShaderResource));

        if (mProgramHandle == 0) {
            return;
        }

        mPositionHandle = GLES20.glGetAttribLocation(mProgramHandle, "aPosition");
        mTextureCoordHandle = GLES20.glGetAttribLocation(mProgramHandle, "aTextureCoordinate");
        mTextureHandle = GLES20.glGetUniformLocation(mProgramHandle, "uTextureSampler");
        mTextureMatrixHandle = GLES20.glGetUniformLocation(mProgramHandle, "uTextureMatrix");
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgramHandle, "uMVPMatrix");

        GLES20.glUseProgram(mProgramHandle);
    }


    public void draw(float[] mtx) {

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, this.textureId);
        GLES20.glUniform1i(mTextureHandle, 0);

        // GLES20.glUniformMatrix4fv(mMVPMatrixHandle,1,false,mtx,0);

        GLES20.glUniformMatrix4fv(mTextureMatrixHandle, 1, false, mtx, 0);
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);

        GLES20.glVertexAttribPointer(mPositionHandle, 2, GLES20.GL_FLOAT, false,
                2 * BYTES_PER_FLOAT, positionBuffer);
        GLES20.glEnableVertexAttribArray(mPositionHandle);


        GLES20.glVertexAttribPointer(mTextureCoordHandle, 2, GLES20.GL_FLOAT, false,
                2 * BYTES_PER_FLOAT, textureVerticesBuffer);
        GLES20.glEnableVertexAttribArray(mTextureCoordHandle);

        GLES20.glClearColor(0f, 0f, 0f, 1f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4);

    }


}
