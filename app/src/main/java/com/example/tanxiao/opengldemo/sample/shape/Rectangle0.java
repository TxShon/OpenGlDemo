package com.example.tanxiao.opengldemo.sample.shape;

import android.content.Context;

import com.example.tanxiao.opengldemo.R;
import com.example.tanxiao.opengldemo.sample.IShape;
import com.example.tanxiao.opengldemo.utils.GlCommonUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;

/**
 * Created by TX on 2018/2/9.
 * Class note:
 * 基础矩形：
 * attribute,uniform 的使用
 */

public class Rectangle0 implements IShape {

    /**
     * 顶点坐标
     */
    private static final float[] tableVertices = {
            //vertex
            -0.5f, -0.5f,
            -0.5f, 0.5f,
            0.5f, 0.5f,
            0.5f, -0.5f,

            //line
            -0.5f, 0f,
            0.5f, 0f,

            //mallets
            0f, 0.25f,
            0f, -0.25f,
    };

    private static final int POSITION_COMPONENT_COUNT = 2;

    private static final int BYTES_PER_FLOAT = 4;

    private FloatBuffer vertexData;

    private Context context;

    private static final String U_COLOR = "u_color";

    private int uColorLocation;

    private static final String A_POSITION = "a_Position";

    private int aPositionLocation;


    public Rectangle0(Context context) {
        this.context = context;
        init();
    }

    @Override
    public void init() {
        vertexData = ByteBuffer.allocateDirect(tableVertices.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();

        vertexData.put(tableVertices);

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0.5f, 0.5f, 0.5f, 0.7f);
        int programHandle = GlCommonUtil.createProgram(
                GlCommonUtil.readShaderFromSource(context, R.raw.simple_vertex_shader)
                , GlCommonUtil.readShaderFromSource(context, R.raw.simple_fragment_shader));

        glUseProgram(programHandle);

        uColorLocation = glGetUniformLocation(programHandle, U_COLOR);
        aPositionLocation = glGetAttribLocation(programHandle, A_POSITION);

        vertexData.position(0);
        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT,
                false, 0, vertexData);
        glEnableVertexAttribArray(aPositionLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);

        //画矩形
        glUniform4f(uColorLocation, 0.7f, 0.7f, 0.7f, 0.7f);
        glDrawArrays(GL_TRIANGLE_FAN, 0, 4);

        //画线
        glUniform4f(uColorLocation, 0.7f, 0.0f, 0.0f, 0.7f);
        glDrawArrays(GL_LINES, 4, 2);

        //画第一个点
        glUniform4f(uColorLocation, 0.0f, 0.7f, 0.0f, 0.7f);
        glDrawArrays(GL_POINTS, 6, 1);

        //画第二个点
        glUniform4f(uColorLocation, 0.0f, 0.0f, 0.7f, 0.7f);
        glDrawArrays(GL_POINTS, 7, 1);

    }
}
