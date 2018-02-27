package com.example.tanxiao.opengldemo.render;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.example.tanxiao.opengldemo.R;
import com.example.tanxiao.opengldemo.objects.NewMallet;
import com.example.tanxiao.opengldemo.objects.Puck;
import com.example.tanxiao.opengldemo.objects.Table;
import com.example.tanxiao.opengldemo.programs.ColorShaderProgram;
import com.example.tanxiao.opengldemo.programs.TextureShaderProgram;
import com.example.tanxiao.opengldemo.utils.TextureHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.setLookAtM;

/**
 * Created by TX on 2018/2/24.
 * Class note:
 */

public class TextureRenderWithMallets implements GLSurfaceView.Renderer {

    private Context context;
    private Table table;
    private NewMallet mallet;
    private TextureShaderProgram textureProgram;
    private ColorShaderProgram colorProgram;

    private int texture;

    private float[] viewMatrix = new float[16];

    //投影矩阵
    private float[] projectionMatrix = new float[16];

    //模型矩阵
    private float[] modelMatrix = new float[16];

    //总体矩阵（矩阵相乘需要一个临时矩阵保存结果）
    private float[] mMvpMatrix = new float[16];

    //临时矩阵
    private final float[] viewProjectionMatrix = new float[16];
    private Puck puck;

    public TextureRenderWithMallets(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0, 0, 0, 0);

        table = new Table();
        mallet = new NewMallet(0.08f, 0.15f, 32);
        puck = new Puck(0.06f, 0.02f, 32);

        textureProgram = new TextureShaderProgram(context);
        colorProgram = new ColorShaderProgram(context, R.raw.vertex_shader, R.raw.fragment_shader);

        texture = TextureHelper.loadTexture(context, R.drawable.air_hockey_surface);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);

        //赋值透视投影矩阵
        Matrix.perspectiveM(projectionMatrix, 0, 45,
                (float) width / (float) height, 1f, 10f);

        setLookAtM(viewMatrix, 0,
                0f, 1.2f, 2.2f,
                0f, 0f, 0f,
                0f, 1f, 0f);

        Matrix.multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);

        //draw table
        positionTableInScene();
        textureProgram.useProgram();
        textureProgram.setUniforms(mMvpMatrix, texture);
        table.bindData(textureProgram);
        table.draw();

        //draw mallet
        positionObjectInScene(0, mallet.height / 2f, -0.4f);
        colorProgram.useProgram();
        colorProgram.setUniforms(mMvpMatrix, 1, 0, 0);
        mallet.bindData(colorProgram);
        mallet.draw();

        positionObjectInScene(0, mallet.height / 2f, 0.4f);
        colorProgram.setUniforms(mMvpMatrix, 1, 0, 0);
        mallet.draw();

        //draw puck
        positionObjectInScene(0, puck.height / 2f, 0f);
        colorProgram.setUniforms(mMvpMatrix, 1, 0, 0);
        puck.bindData(colorProgram);
        puck.draw();
    }


    /**
     * 物体变换矩阵
     *
     * @param x
     * @param y
     * @param z
     */
    private void positionObjectInScene(float x, float y, float z) {
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.translateM(modelMatrix, 0, x, y, z);
        //Matrix.rotateM(modelMatrix,0,180f,0,0,1f);
        Matrix.multiplyMM(mMvpMatrix, 0, viewProjectionMatrix, 0, modelMatrix, 0);
    }

    /**
     * 桌子矩阵变换
     */
    private void positionTableInScene() {
        //正交初始化变换矩阵，用于平移，旋转等变换
        Matrix.setIdentityM(modelMatrix, 0);
        //z轴移动-2
        // Matrix.translateM(modelMatrix, 0, 0f, 0f, -2f);
        //绕x轴旋转60度
        Matrix.rotateM(modelMatrix, 0, -90f, 1f, 0f, 0f);
        //缩放0.8f
        // Matrix.scaleM(modelMatrix, 0, 0.8f, 0.8f, 0.8f);

        //矩阵相乘，并将结果存入mMvpMatrix
        Matrix.multiplyMM(mMvpMatrix, 0, viewProjectionMatrix, 0, modelMatrix, 0);
    }
}
