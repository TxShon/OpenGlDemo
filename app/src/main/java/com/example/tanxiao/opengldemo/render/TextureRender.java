package com.example.tanxiao.opengldemo.render;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.example.tanxiao.opengldemo.R;
import com.example.tanxiao.opengldemo.objects.Mallet;
import com.example.tanxiao.opengldemo.objects.Table;
import com.example.tanxiao.opengldemo.programs.ColorShaderProgram;
import com.example.tanxiao.opengldemo.programs.TextureShaderProgram;
import com.example.tanxiao.opengldemo.utils.TextureHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glViewport;

/**
 * Created by TX on 2018/2/24.
 * Class note:
 */

public class TextureRender implements GLSurfaceView.Renderer {

    private Context context;
    private Table table;
    private Mallet mallet;
    private TextureShaderProgram textureProgram;
    private ColorShaderProgram colorProgram;

    private int texture;

    //投影矩阵
    private float[] projectionMatrix = new float[16];

    //模型矩阵
    private float[] modelMatrix = new float[16];

    //总体矩阵（矩阵相乘需要一个临时矩阵保存结果）
    private float[] mMvpMatrix = new float[16];

    public TextureRender(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0, 0, 0, 0);

        table = new Table();
        mallet = new Mallet();

        textureProgram = new TextureShaderProgram(context);
        colorProgram = new ColorShaderProgram(context);

        texture = TextureHelper.loadTexture(context, R.drawable.air_hockey_surface);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);

        //赋值透视投影矩阵
        Matrix.perspectiveM(projectionMatrix,0,45,
                (float)width/(float)height,1f,10f);

        //正交初始化变换矩阵，用于平移，旋转等变换
        Matrix.setIdentityM(modelMatrix,0);
        //z轴移动-2
        Matrix.translateM(modelMatrix,0,0f,0f,-2f);
        //绕x轴旋转60度
        Matrix.rotateM(modelMatrix,0,-60f,1f,0f,0f);
        //缩放0.8f
        Matrix.scaleM(modelMatrix,0,0.8f,0.8f,0.8f);

        //矩阵相乘，并将结果存入mMvpMatrix
        Matrix.multiplyMM(mMvpMatrix,0,projectionMatrix,0,modelMatrix,0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);

        textureProgram.useProgram();
        textureProgram.setUniforms(mMvpMatrix,texture);
        table.bindData(textureProgram);
        table.draw();

        colorProgram.useProgram();
        colorProgram.setUniforms(mMvpMatrix);
        mallet.bindData(colorProgram);
        mallet.draw();
    }
}
