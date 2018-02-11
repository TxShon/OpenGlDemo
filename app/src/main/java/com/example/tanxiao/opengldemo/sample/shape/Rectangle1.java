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
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;

/**
 * Created by TX on 2018/2/9.
 * Class note:
 * 基础矩形：
 * 顶点颜色的使用
 */

public class Rectangle1 implements IShape {


    /**
     * 顶点坐标
     */
    private static final float[] tableVertices = {
            //vertex
            0.0f, 0.0f,
            //color
            1.0f, 1.0f, 1.0f,

            -0.5f, -0.5f,
            0.7f, 0.7f, 0.7f,

            0.5f, -0.5f,
            0.7f, 0.7f, 0.7f,

            0.5f, 0.5f,
            0.7f, 0.7f, 0.7f,

            -0.5f, 0.5f,
            0.7f, 0.7f, 0.7f,

            -0.5f, -0.5f,
            0.7f, 0.7f, 0.7f,

            //line
            -0.5f, 0f,
            1.0f, 0.0f, 0.0f,

            0.5f, 0f,
            0.0f, 0.0f, 1.0f,

            //mallets
            0f, 0.25f,
            1.0f, 0.0f, 0.0f,

            0f, -0.25f,
            0.0f, 0.0f, 1.0f,
    };

    private static final int POSITION_COMPONENT_COUNT = 2;

    private static final int COLOR_COMPONENT_COUNT = 3;

    private static final int BYTES_PER_FLOAT = 4;

    private static final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;

    private FloatBuffer vertexData;

    private Context context;

    private static final String A_COLOR = "a_Color";

    private static final String A_POSITION = "a_Position";

    private int aPositionLocation;

    protected int aColorLocation;

    public Rectangle1(Context context) {
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
                GlCommonUtil.readShaderFromSource(context, R.raw.simple_vertex_shader_vertex_color)
                , GlCommonUtil.readShaderFromSource(context, R.raw.simple_fragment_shader_vertex_color));

        glUseProgram(programHandle);

        aPositionLocation = glGetAttribLocation(programHandle, A_POSITION);
        aColorLocation = glGetAttribLocation(programHandle, A_COLOR);

        vertexData.position(0);
        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT,
                false, STRIDE, vertexData);
        glEnableVertexAttribArray(aPositionLocation);

        vertexData.position(POSITION_COMPONENT_COUNT);
        glVertexAttribPointer(aColorLocation, COLOR_COMPONENT_COUNT, GL_FLOAT,
                false, STRIDE, vertexData);
        glEnableVertexAttribArray(aColorLocation);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);

        //画矩形
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);

        //画线
        glDrawArrays(GL_LINES, 6, 2);

        //画第一个点
        glDrawArrays(GL_POINTS, 8, 1);

        //画第二个点
        glDrawArrays(GL_POINTS, 9, 1);

    }
}
